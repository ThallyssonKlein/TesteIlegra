package br.com.thallyssonklein.controller;

import br.com.thallyssonklein.entity.Metric;
import br.com.thallyssonklein.jpa.EntityManagerUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetricsControllerTest {

    @Mock
    private HttpServletRequest request;

    private boolean wasCalled = false;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    public void insertWorld(EntityManager entityManager){
        if(!wasCalled){
            entityManager.createNativeQuery("INSERT INTO LOGS (URL, TIMES, USERUID, REGION) VALUES\n" +
                    "('top1OfTheWorld', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top1OfTheWorld', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top1OfTheWorld', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top1OfTheWorld', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top2OfTheWorld', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top2OfTheWorld', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top2OfTheWorld', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top3OfTheWorld', '2019-05-15 09:07:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top3OfTheWorld', '2019-05-15 09:07:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top4OfTheWorld', '2019-05-15 09:07:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1);\n").executeUpdate();
            wasCalled = true;
        }
    }
    @Test
    public void shouldMetric1Ok() throws ServletException, IOException {
        //given
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        insertWorld(entityManager);
        entityManager.getTransaction().commit();
        //when
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric1(entityManager, metric);
        //then
        assertTrue(metric.getTop3MostAccess()[0].equals("top1OfTheWorld") &&
                    metric.getTop3MostAccess()[1].equals("top2OfTheWorld") &&
                    metric.getTop3MostAccess()[2].equals("top3OfTheWorld"));
    }

    @Test
    public void shouldMetric2Ok(){
        //given
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO LOGS (URL, TIMES, USERUID, REGION) VALUES\n" +
                "('top1OfTheRegion1', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheRegion1', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheRegion1', '2019-05-15 09:06:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfTheRegion1', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfTheRegion1', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top3OfTheRegion1', '2019-05-15 09:07:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheRegion2', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                "('top1OfTheRegion2', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                "('top1OfTheRegion2', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                "('top2OfTheRegion2', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                "('top2OfTheRegion2', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                "('top3OfTheRegion2', '2019-05-15 09:05:58.090257', '5b019db5-b3d0-46d2-9963-437860af707f', 2);\n").executeUpdate();
        entityManager.getTransaction().commit();
        //when
        Metric metric = new Metric();
        metric.setTop3MostAccess(new String[]{"top1OfTheWorld", "top2OfTheWorld", "top3OfTheWorld"});
        MetricsController metricsController = new MetricsController();
        metricsController.metric2(entityManager, metric);
        //then
        String[] region1 = metric.getTopByRegions().get(0).getTop3MostAccess();
        String[] region2 = metric.getTopByRegions().get(1).getTop3MostAccess();
assertTrue(    region1[0].equals("top1OfTheRegion1") &&
                        region1[1].equals("top2OfTheRegion1") &&
                        region1[2].equals("top3OfTheRegion1") &&
                        region2[0].equals("top1OfTheRegion2") &&
                        region2[1].equals("top2OfTheRegion2") &&
                        region2[2].equals("top3OfTheRegion2")
        );
    }

    @Test
    public void shouldMetric3Ok(){
        //given
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        insertWorld(entityManager);
        entityManager.getTransaction().commit();
        //when
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric1(entityManager, metric);
        metricsController.metric3(entityManager, metric);
        //then
        assertTrue(metric.getLessAccessedOfTheWorld().equals("top4OfTheWorld"));
    }

    @Test
    public void shouldMetricDay4Ok(){
        //given
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO LOGS (URL, TIMES, USERUID, REGION) VALUES\n" +
                "('top1OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top3OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top3OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top4OfTheDay15', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfTheDay16', DATEADD('DAY',-1, CURRENT_DATE), '5b019db5-b3d0-46d2-9963-437860af707f', 1);\n").executeUpdate();
        entityManager.getTransaction().commit();
        //when
        when(request.getParameter("dwm")).thenReturn("day");
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric4(entityManager, metric, request);
        //then
        assertTrue(metric.getTop3ByTime()[0].equals("top1OfTheDay15") && metric.getTop3ByTime()[1].equals("top2OfTheDay15") &&
                            metric.getTop3ByTime()[2].equals("top3OfTheDay15"));
    }

    @Test
    public void shouldMetricWeek4Ok(){
        //given
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO LOGS (URL, TIMES, USERUID, REGION) VALUES\n" +
                "('top1OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top3OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top3OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top4OfCurrentWeek', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfLastWeek', DATEADD('DAY',-8, CURRENT_DATE), '5b019db5-b3d0-46d2-9963-437860af707f', 1);\n").executeUpdate();
        entityManager.getTransaction().commit();
        //when
        when(request.getParameter("dwm")).thenReturn("week");
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric4(entityManager, metric, request);
        //then
        assertTrue(metric.getTop3ByTime()[0].equals("top1OfCurrentWeek") && metric.getTop3ByTime()[1].equals("top2OfCurrentWeek") &&
                metric.getTop3ByTime()[2].equals("top3OfCurrentWeek"));
    }

    @Test
    public void shouldMetricMonth4Ok(){
        //given
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO LOGS (URL, TIMES, USERUID, REGION) VALUES\n" +
                "('top1OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top2OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top3OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top3OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top4OfCurrentMonth', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                "('top1OfLastMonth', DATEADD('DAY',-31, CURRENT_DATE), '5b019db5-b3d0-46d2-9963-437860af707f', 1);\n").executeUpdate();
        entityManager.getTransaction().commit();
        //when
        when(request.getParameter("dwm")).thenReturn("month");
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric4(entityManager, metric, request);
        //then
        assertTrue(metric.getTop3ByTime()[0].equals("top1OfCurrentMonth") && metric.getTop3ByTime()[1].equals("top2OfCurrentMonth") &&
                metric.getTop3ByTime()[2].equals("top3OfCurrentMonth"));
    }

    @Test
    public void shouldMetric5Ok(){
        //given
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();
        insertWorld(entityManager);
        entityManager.getTransaction().commit();
        //when
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.logs = entityManager.createQuery("SELECT e FROM LOGS e").getResultList();
        metricsController.metric5(entityManager, metric);
        //then
        assertTrue(metric.getMinuteWithMoreAcess() == 6);
    }
}
