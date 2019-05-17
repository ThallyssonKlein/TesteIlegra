package br.com.thallyssonklein.controller;

import br.com.thallyssonklein.controller.CustomResponse;
import br.com.thallyssonklein.controller.IngestController;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IngestControllerTest {

    @InjectMocks
    private IngestController ingestController;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPersistWhenAllParametersAreOk() throws IOException, ServletException {
        //given
        when(request.getParameter("url")).thenReturn("/mockitoTest");
        when(request.getParameter("times")).thenReturn("2019-05-15 10:44:50.873934");
        when(request.getParameter("useruid")).thenReturn("mockitouid");
        when(request.getParameter("region")).thenReturn("1");
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        //when
        ingestController.doPost(request, response);
        //then
        Mockito.verify(response.getWriter()).print(new Gson().toJson(new CustomResponse(200)));
    }

    @Test
    public void shouldNotPersistWhenSomethingIsWrong() throws ServletException, IOException {
        //given
        when(request.getParameter("times")).thenReturn("abcdefg");
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        //when
        ingestController.doPost(request, response);

        //then
        verify(response.getWriter()).print(new Gson().toJson(new CustomResponse(500)));
    }

//    @After
//    public void validate() {
//        validateMockitoUsage();
//    }
}
