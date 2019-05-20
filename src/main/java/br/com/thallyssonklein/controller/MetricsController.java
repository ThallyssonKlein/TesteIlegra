package br.com.thallyssonklein.controller;

import br.com.thallyssonklein.Util;
import br.com.thallyssonklein.entity.Log;
import br.com.thallyssonklein.entity.Metric;
import br.com.thallyssonklein.entity.TopByRegion;
import br.com.thallyssonklein.jpa.EntityManagerUtil;
import com.google.gson.Gson;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MetricsController extends HttpServlet {

    private Map<String, Integer> sortedByCount = new HashMap<>();
    public List<Log> logs = new ArrayList<>();

    public void doGet(HttpServletRequest request, HttpServletResponse response2) throws ServletException, IOException {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        Metric response = new Metric();
        //METRIC 1
        metric1(entityManager, response);
        //METRIC 2
        metric2(entityManager, response);
        //METRIC 3
        metric3(entityManager, response);
        //METRIC 4
        metric4(entityManager, response, request);
        //METRIC 5
        metric5(entityManager, response);
        response2.setContentType("application/json");
        PrintWriter out = response2.getWriter();
        out.print(new Gson().toJson(response));
        out.flush();
    }

    public void metric1(EntityManager entityManager, Metric response){
        List<String> urls = entityManager.createQuery("SELECT url FROM LOGS").getResultList();
        sortedByCount = Util.sortMap(Util.countFrequencies(urls));
        int i = 0;
        String[] top3MostWorld = new String[3];
        for(Map.Entry<String, Integer> entry : sortedByCount.entrySet()){
            top3MostWorld[i] = entry.getKey();
            if(i==2){
                break;
            }
            ++i;
        }
        response.setTop3MostAccess(top3MostWorld);
    }

    public void metric2(EntityManager entityManager, Metric response){
        List<TopByRegion> topByRegions = new ArrayList<TopByRegion>();
        List<Integer> regions = entityManager.createQuery("SELECT DISTINCT region FROM LOGS").getResultList();
        for(Integer region : regions){
            List<String> urls2 = (List<String>) entityManager.createNativeQuery("SELECT url FROM LOGS WHERE region=" + region.toString()).getResultList();
            Map<String, Integer> sortedByCount2 = Util.sortMap(Util.countFrequencies(urls2));
            int i2 = 0;
            String[] top3MostRegion = new String[3];
            for(Map.Entry<String, Integer> entry : sortedByCount2.entrySet()){
                top3MostRegion[i2] = entry.getKey();
                if(i2==2){
                    break;
                }
                ++i2;
            }
            topByRegions.add(new TopByRegion(region, top3MostRegion));
        }
        response.setTopByRegions(topByRegions);
    }

    public void metric3(EntityManager entityManager, Metric response){
        Iterator<Map.Entry<String, Integer>> entries = sortedByCount.entrySet().iterator();
        Map.Entry<String, Integer> entry = null;
        while (entries.hasNext()){
            entry = entries.next();
        }
        response.setLessAccessedOfTheWorld(entry.getKey());
    }

    public void metric4(EntityManager entityManager, Metric response, HttpServletRequest request){
        logs = entityManager.createQuery("SELECT e FROM LOGS e").getResultList();
        List<String> filtredLogs = new ArrayList<>();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
        switch (request.getParameter("dwm")){
            case "day":
                for(Log l : logs){
                    int day = l.getTimes().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth();
                    if(day == cal.get(Calendar.DAY_OF_MONTH)){
                        filtredLogs.add(l.getUrl());
                    }
                }
                break;
            case "week":
                for(Log l : logs){
                    Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
                    cal2.setTime(l.getTimes());
                    if(cal2.get(Calendar.WEEK_OF_MONTH) == cal.get(Calendar.WEEK_OF_MONTH)){
                        filtredLogs.add(l.getUrl());
                    }
                }
                break;
            case "year":
                for(Log l : logs){
                    int year = l.getTimes().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
                    if(year == cal.get(Calendar.YEAR)){
                        filtredLogs.add(l.getUrl());
                    }
                }
                break;
        }
        String[] top3ByTime = new String[3];
        Map<String, Integer> filtredUrls = Util.sortMap(Util.countFrequencies(filtredLogs));
        int i = 0;
        for(Map.Entry<String, Integer> entry : filtredUrls.entrySet()){
            top3ByTime[i] = entry.getKey();
            if(i==2){
                break;
            }else{
                ++i;
            }
        }
        response.setTop3ByTime(top3ByTime);
    }

    public void metric5(EntityManager entityManager, Metric response){
        response.setMinuteWithMoreAcess(Util.countFrequenciesByMinute(logs));
    }
}
