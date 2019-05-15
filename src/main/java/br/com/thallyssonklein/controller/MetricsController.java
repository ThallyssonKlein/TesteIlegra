package br.com.thallyssonklein.controller;

import br.com.thallyssonklein.Util;
import br.com.thallyssonklein.entity.Log;
import br.com.thallyssonklein.entity.Metric;
import br.com.thallyssonklein.entity.TopByRegion;
import br.com.thallyssonklein.jpa.EntityManagerUtil;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.util.*;

public class MetricsController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response2) throws ServletException, IOException {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO LOGS (URL, TIMES, USERUID, REGION) VALUES\n" +
                "('top1OfTheWorld', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheWorld', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                "('top1OfTheWorld', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 3),\n" +
                "('top2OfTheWorld', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfTheWorld', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                "('top3OfTheWorld', '2019-05-15 09:07:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1);\n").executeUpdate();
        entityManager.getTransaction().commit();
        Metric response = new Metric();
        //METRIC 1
        List<String> urls = entityManager.createQuery("SELECT url FROM LOGS").getResultList();
        Map<String, Integer> sortedByCount = Util.sortMap(Util.countFrequencies(urls));
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
        //METRIC 2
        List<TopByRegion> topByRegions = new ArrayList<TopByRegion>();
        List<Integer> regions = entityManager.createQuery("SELECT region FROM LOGS").getResultList();
        for(Integer region : regions){
            List<String> urls2 = (List<String>) entityManager.createQuery("SELECT url FROM LOGS WHERE region=" + region.toString()).getResultList();
            Map<String, Integer> sortedByCount2 = Util.sortMap(Util.countFrequencies(urls2));
            int i2 = 0;
            String[] top3MostRegion = new String[3];
            for(Map.Entry<String, Integer> entry : sortedByCount2.entrySet()){
                top3MostWorld[i2] = entry.getKey();
                if(i2==2){
                    break;
                }
                ++i2;
            }
            topByRegions.add(new TopByRegion(region, top3MostRegion));
        }
        response.setTopByRegions(topByRegions);
        //METRIC 3
        Iterator<Map.Entry<String, Integer>> entries = sortedByCount.entrySet().iterator();
        Map.Entry<String, Integer> entry = null;
        while (entries.hasNext()){
            entry = entries.next();
        }
        response.setLessAccessedOfTheWorld(entry.getKey());
        //METRIC 4
        List<Log> logs = entityManager.createQuery("SELECT e FROM LOGS e").getResultList();
        List<String> filtredLogs = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
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
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(l.getTimes());
                    if(cal2.get(Calendar.WEEK_OF_MONTH) == cal.get(Calendar.WEEK_OF_MONTH)){
                        filtredLogs.add(l.getUrl());
                    }
                }
                break;
            case "month":
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
        for(int i3 = 0; i3<=2; ++i3){
            top3ByTime[i3] = filtredLogs.get(i3);
        }
        response.setTop3ByTime(top3ByTime);
        //METRIC 5
        response.setMinuteWithMoreAcess(Util.countFrequenciesByMinute(logs));
        response2.setContentType("application/json");
        PrintWriter out = response2.getWriter();
        out.print(new Gson().toJson(response));
        out.flush();
    }
}
