package com.xyzlast.web.controller;

import com.google.common.base.Strings;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String message = "이름이 입력되지 않았습니다.";
        if (!Strings.isNullOrEmpty(name)) {
            message = "Hello World!! " + name;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        modelAndView.setViewName("hello");
        return modelAndView;
    }
}
