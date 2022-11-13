package com.raf.reservationservice.messagebroker;

import org.apache.activemq.broker.BrokerService;

public class MessageBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
