package com.raf.notificationservice.listener;


import com.raf.notificationservice.dto.ReservationCompleteDto;
import com.raf.notificationservice.dto.UserDto;
import com.raf.notificationservice.listener.helper.MessageHelper;
import com.raf.notificationservice.service.NotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private NotificationService notificationService;

    public EmailListener(MessageHelper messageHelper, NotificationService notificationService) {
        this.messageHelper = messageHelper;
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "${destination.registerUser}", concurrency = "5-10")
    public void registerMail(Message message) throws JMSException {
        UserDto userDto = messageHelper.getMessage(message, UserDto.class);
        String link = "http://localhost:3000/verify";
        String text = String.format("Pozdrav %s %s, da bi ste se verifikovali kliknite na link %s", userDto.getFirstName(), userDto.getLastName(), link);
        notificationService.sendSimpleMessage(userDto.getEmail(), "Registration", text);
        notificationService.createNotification(userDto.getEmail(), "emailRegistration", text);

    }
    @JmsListener(destination = "${destination.updatePassword}", concurrency = "5-10")
    public void updatePassword(Message message) throws  JMSException {
        UserDto userDto = messageHelper.getMessage(message, UserDto.class);
        String text = String.format("Pozdrav %s %s, uspesno ste promenili sifru.", userDto.getFirstName(), userDto.getLastName());
        notificationService.sendSimpleMessage(userDto.getEmail(), "Password change", text);
        notificationService.createNotification(userDto.getEmail(), "passwordChange", text);
    }

    @JmsListener(destination = "${destination.reservationComplete}", concurrency = "5-10")
    public void reservationSend(Message message) throws  JMSException {
        ReservationCompleteDto reservationCompleteDto = messageHelper.getMessage(message, ReservationCompleteDto.class);
        String clientText =  String.format("Pozdrav %s %s, uspesno ste rezervisali smestaj.", reservationCompleteDto.getClient().getFirstName(), reservationCompleteDto.getClient().getLastName());
        String managerText = String.format("Pozdrav %s %s, klijent %s %s je rezervisao smestaj.", reservationCompleteDto.getManager().getFirstName(), reservationCompleteDto.getManager().getLastName(),
                                            reservationCompleteDto.getClient().getFirstName(), reservationCompleteDto.getClient().getLastName());
        notificationService.sendSimpleMessage(reservationCompleteDto.getClient().getEmail(), "Reservation complete", clientText);
        notificationService.sendSimpleMessage(reservationCompleteDto.getManager().getEmail(), "Client reservation complete", managerText);
        notificationService.createNotification(reservationCompleteDto.getClient().getEmail(), "reservationComplete", clientText);
        notificationService.createNotification(reservationCompleteDto.getManager().getEmail(), "reservationComplete", managerText);

    }

    @JmsListener(destination = "${destination.reservationCancel}", concurrency = "5-10")
    public void reservationCancel(Message message) throws  JMSException {
        ReservationCompleteDto reservationCompleteDto = messageHelper.getMessage(message, ReservationCompleteDto.class);
        String clientText =  String.format("Pozdrav %s %s, vasa rezervacija je otkazana.", reservationCompleteDto.getClient().getFirstName(), reservationCompleteDto.getClient().getLastName());
        String managerText = String.format("Pozdrav %s %s, klijentu %s %s je rezervacija otkazana.", reservationCompleteDto.getManager().getFirstName(), reservationCompleteDto.getManager().getLastName(),
                reservationCompleteDto.getClient().getFirstName(), reservationCompleteDto.getClient().getLastName());
        notificationService.sendSimpleMessage(reservationCompleteDto.getClient().getEmail(), "Reservation cancel", clientText);
        notificationService.sendSimpleMessage(reservationCompleteDto.getManager().getEmail(), "Client reservation cancel", managerText);
        notificationService.createNotification(reservationCompleteDto.getClient().getEmail(), "reservationCancel", clientText);
        notificationService.createNotification(reservationCompleteDto.getManager().getEmail(), "reservationCancel", managerText);
    }
}
