package com.raf.notificationservice.mapper;

import com.raf.notificationservice.domain.Notification;
import com.raf.notificationservice.dto.NotificationUpdateDto;
import com.raf.notificationservice.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification notificationUpdateDtoToNotification(Notification notification, NotificationUpdateDto notificationUpdateDto){
        if(notificationUpdateDto.getEmailTo() != null){
            notification.setEmailTo(notificationUpdateDto.getEmailTo());
        }
        if(notificationUpdateDto.getText() != null){
            notification.setText(notificationUpdateDto.getText());
        }
        if(notificationUpdateDto.getType() != null){
            notification.setType(notificationUpdateDto.getType());
        }
        if(notificationUpdateDto.getDateCreated() != null){
            if(!notificationUpdateDto.getParseDate(notificationUpdateDto.getDateCreated())){
                throw new BadRequestException("Invalid date format!");
            }
            notification.setDateCreated(notificationUpdateDto.getDateCreated());
        }

        return notification;
    }
}
