package com.techmix.fisher.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fisher
 * @since 4/8/16
 */
@Controller
public class AngularJSForwardController {
    private final Logger log = LoggerFactory.getLogger(AngularJSForwardController.class);

    @RequestMapping(value = {
            "/*"
    }, method = RequestMethod.GET)
    public void pageForward(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        forward(httpRequest, httpResponse);
    }

    private void forward(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/built/index.html");
        try {
            dispatcher.forward(httpRequest, httpResponse);
        } catch (Exception e) {
            log.error("Error forwarding request", e);
        }
    }
}
