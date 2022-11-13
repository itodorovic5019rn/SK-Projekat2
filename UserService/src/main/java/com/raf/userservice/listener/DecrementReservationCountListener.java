package com.raf.userservice.listener;

import com.raf.userservice.dto.DecrementReservationCountDto;
import com.raf.userservice.dto.IncrementReservationCountDto;
import com.raf.userservice.service.impl.UserServiceImpl;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.IOException;

@Component
public class DecrementReservationCountListener {
    private MessageHelper messageHelper;
    private UserServiceImpl userService;

    public DecrementReservationCountListener(MessageHelper messageHelper, UserServiceImpl userService) {
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    @JmsListener(destination = "${destination.decrement.reservation.count}", concurrency = "5-10")
    public void incrementReservationCount(Message message) throws JMSException, IOException {
        DecrementReservationCountDto decrementReservationCountDto = messageHelper.getMessage(message, DecrementReservationCountDto.class);
        userService.decrementReservationForUser(decrementReservationCountDto);
    }
}
