package com.lewis.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zmh46712
 * @version Id: HelloController, v 0.1 2017/4/28 16:45 zmh46712 Exp $
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/say")
    @ResponseBody
    public String say(){
        return "hello ! it is really ?";
    }

}
