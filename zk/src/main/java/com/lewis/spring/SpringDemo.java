package com.lewis.spring;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by zhangminghua on 2017/1/3.
 */
public class SpringDemo {
    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource("beans.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);

        Person person = factory.getBean("person", Person.class);
        person.sayHello("lewis");
    }
}
