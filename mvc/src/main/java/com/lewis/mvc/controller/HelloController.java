package com.lewis.mvc.controller;

import com.lewis.mvc.service.test.AbsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zmh46712
 * @version Id: HelloController, v 0.1 2017/4/28 16:45 zmh46712 Exp $
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @Resource
    private Map<String, AbsService> map;

    @Resource
    private List<AbsService>        list;

    @RequestMapping("/say")
    @ResponseBody
    public String say() {
        System.out.println(map);
        System.out.println(list);
        return "hello ! it is really ?";
    }

}
