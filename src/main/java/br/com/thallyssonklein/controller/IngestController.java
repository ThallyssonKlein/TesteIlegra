package br.com.thallyssonklein.controller;

import br.com.thallyssonklein.entity.Log;
import br.com.thallyssonklein.jpa.EntityManagerUtil;
import com.google.gson.Gson;
import lombok.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Path("/ingestlog")
public class IngestController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            DateFormat format = new SimpleDateFormat("MMddyyHHmmss");
            Log l = new Log(request.getParameter("url"),
                    format.parse(request.getParameter("times")),
                    request.getParameter("uid"),
                    Integer.parseInt(request.getParameter("region")));
            EntityManager entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(l);
            entityManager.getTransaction().commit();
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(new CustomResponse(200)));
            out.flush();
        }catch(Exception e){
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(new CustomResponse(500)));
            out.flush();
        }
    }
}