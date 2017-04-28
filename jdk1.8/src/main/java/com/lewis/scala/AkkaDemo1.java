package com.lewis.scala;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author zmh46712
 * @version Id: AkkaDemo1, v 0.1 2017/4/28 10:54 zmh46712 Exp $
 */
public class AkkaDemo1 {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("akkaRoot");
        //actorSystem.actorOf(Props.)
    }
}
