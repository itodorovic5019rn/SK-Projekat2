package com.raf.userservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raf.userservice.dto.IncrementReservationCountDto;
import com.raf.userservice.service.impl.UserServiceImpl;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

@Component
public class IncrementReservationCountListener {

    private MessageHelper messageHelper;
    private UserServiceImpl userService;

    public IncrementReservationCountListener(MessageHelper messageHelper, UserServiceImpl userService) {
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    @JmsListener(destination = "${destination.increment.reservation.count}", concurrency = "5-10")
    public void incrementReservationCount(Message message) throws JMSException, IOException {
        IncrementReservationCountDto incrementReservationCountDto = messageHelper.getMessage(message, IncrementReservationCountDto.class);
        userService.incrementReservationForUser(incrementReservationCountDto);
    }

}
