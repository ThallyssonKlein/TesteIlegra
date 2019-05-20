package br.com.thallyssonklein.controller;

import br.com.thallyssonklein.entity.Metric;
import br.com.thallyssonklein.jpa.EntityManagerUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(JUnit4.class)
public class MetricsControllerTest {

    @Mock
    private HttpServletRequest request;

    private static EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static boolean wasCalled = false;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setUp2(){
        System.out.println("Chamou o setup2");
        entityManager.getTransaction().begin();
        if(!wasCalled){
            wasCalled = true;
            System.out.println("Vai executar a query");
            entityManager.createNativeQuery("INSERT INTO LOGS (URL, TIMES, USERUID, REGION) VALUES\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheWorld', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top1OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top1OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top1OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top2OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top2OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top2OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top2OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top3OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top3OfTheRegion1', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 1),\n" +
                    "('top1OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top1OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top1OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top1OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top2OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top2OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top2OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top3OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top3OfTheRegion2', DATEADD('YEAR',-1, DATEADD('DAY',-1, DATEADD('WEEK',-1, CURRENT_DATE))), '5b019db5-b3d0-46d2-9963-437860af707f', 2),\n" +
                    "('top1OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top1OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top2OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('top3OfTheDWY', NOW(), '5b019db5-b3d0-46d2-9963-437860af707f', 4),\n" +
                    "('lessAccessedOfTheWorld', DATEADD('DAY',-1, CURRENT_DATE), '5b019db5-b3d0-46d2-9963-437860af707f', 4)," +
                    "('minuteWithLessAccess', DATEADD('MINUTE',-10, CURRENT_DATE), '5b019db5-b3d0-46d2-9963-437860af707f', 4);").executeUpdate();
        }
        entityManager.getTransaction().commit();

    }

    @Test
    public void shouldMetric1Ok() throws ServletException, IOException {
        //given
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
        //when
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric1(entityManager, metric);
        metricsController.metric3(entityManager, metric);
        //then
        assertTrue(metric.getLessAccessedOfTheWorld().equals("lessAccessedOfTheWorld"));
    }

    @Test
    public void shouldMetricDay4Ok(){
        //given
        //when
        when(request.getParameter("dwm")).thenReturn("day");
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric4(entityManager, metric, request);
        //then
        assertTrue(metric.getTop3ByTime()[0].equals("top1OfTheDWY") && metric.getTop3ByTime()[1].equals("top2OfTheDWY") &&
                            metric.getTop3ByTime()[2].equals("top3OfTheDWY"));
    }

    @Test
    public void shouldMetricWeek4Ok(){
        //given
        //when
        when(request.getParameter("dwm")).thenReturn("week");
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric4(entityManager, metric, request);
        //then
        assertTrue(metric.getTop3ByTime()[0].equals("top1OfTheDWY") && metric.getTop3ByTime()[1].equals("top2OfTheDWY") &&
                metric.getTop3ByTime()[2].equals("top3OfTheDWY"));
    }

    @Test
    public void shouldMetricYear4Ok(){
        //given
        //when
        when(request.getParameter("dwm")).thenReturn("year");
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.metric4(entityManager, metric, request);
        //then
        assertTrue(metric.getTop3ByTime()[0].equals("top1OfTheDWY") && metric.getTop3ByTime()[1].equals("top2OfTheDWY") &&
                metric.getTop3ByTime()[2].equals("top3OfTheDWY"));
    }

    @Test
    public void shouldMetric5Ok(){
        //given
        //when
        Metric metric = new Metric();
        MetricsController metricsController = new MetricsController();
        metricsController.logs = entityManager.createQuery("SELECT e FROM LOGS e").getResultList();
        metricsController.metric5(entityManager, metric);
        //then
        assertTrue(metric.getMinuteWithMoreAcess() == 0);
    }
}
