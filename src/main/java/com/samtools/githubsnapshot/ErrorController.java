package com.samtools.githubsnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @Autowired
    private IndexController index;

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = "This should be application specific";

        System.out.println("Catching Exception - IllegalArgumentException, IllegalStateException");
        System.out.printf("Exception detail: %s. Trace [%s]\n", ex.getMessage(), ex.getStackTrace().toString());

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {HttpSessionRequiredException.class})
    protected ModelAndView handleSessionTimeout(RuntimeException ex, WebRequest request) {

        System.out.println("Catching Exception - HttpSessionRequiredException");
        System.out.printf("Exception detail: %s. Trace [%s]\n", ex.getMessage(), ex.getStackTrace().toString());
        System.out.printf("HTTP request detail: %s.\n", request.getDescription(true));

        String error_text = "Session close by timeout ("+ex.getMessage()+")";

        ModelMap map = new ModelMap();
        map.addAttribute("error_text", error_text);

        return new ModelAndView("error",map);

    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    protected ModelAndView handleServerTimeout(RuntimeException ex, WebRequest request) {

        System.out.println("Catching Exception - HttpClientErrorException. GraphQL server error connection");
        System.out.printf("Exception detail: %s. Trace [%s]\n", ex.getMessage(), ex.getStackTrace().toString());

        String error_text = "Can't connect to server: "+ex.getMessage();

        ModelMap map = new ModelMap();
        map.addAttribute("error_text", error_text);

        return new ModelAndView("error",map);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ModelAndView commonError(RuntimeException ex, WebRequest request) {

        System.out.printf("Catching Exception: %s. Trace [%s]\n", ex.getMessage(), ex.getStackTrace().toString());

        String error_text = "Common error: "+ex.getMessage();

        ModelMap map = new ModelMap();
        map.addAttribute("error_text", error_text);

        return new ModelAndView("error",map);
    }

}