package com.lewis.jdk8.optional;

import com.lewis.jdk8.vo.Car;
import com.lewis.jdk8.vo.Insurance;
import com.lewis.jdk8.vo.Person;

import java.util.Optional;

/**
 * Created by zhangminghua on 2016/11/26.
 */
public class OptionalTest {
    public static void main(String[] args) {
        Person person = new Person("lewis", Optional.of(new Car("bmw",Optional.of(new Insurance("保险")))));
        Optional<Person> op = Optional.of(person);
        Optional<Car> car = op.flatMap(Person::getCar);
        Optional<Insurance> insurance = car.flatMap(Car::getInsurance);
        Optional<String> s = insurance.map(Insurance::getName);
        System.out.println(s.get());
        System.out.println(person);

    }

    public String getInsuranceName(Optional<Person> optional){
        return optional.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("unKnown");
    }
}
