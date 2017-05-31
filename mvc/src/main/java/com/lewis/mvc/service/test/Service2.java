package com.lewis.mvc.service.test;

import org.springframework.stereotype.Service;

/**
 * @author zmh46712
 * @version Id: Service2, v 0.1 2017/5/31 17:45 zmh46712 Exp $
 */
@Service
public class Service2 extends AbsService {
    @Override
    public void say() {
        System.out.println("Service2 say");
    }
}
