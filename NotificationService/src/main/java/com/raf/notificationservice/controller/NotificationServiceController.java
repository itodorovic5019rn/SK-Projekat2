package com.raf.notificationservice.controller;

import com.raf.notificationservice.domain.Notification;
import com.raf.notificationservice.dto.NotificationUpdateDto;
import com.raf.notificationservice.security.CheckSecurity;
import com.raf.notificationservice.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification")
public class NotificationServiceController {

    private NotificationService notificationService;

    public NotificationServiceController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<Page<Notification>> getAllNotifications(@RequestHeader("Authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(notificationService.findAllNotifications(pageable), HttpStatus.OK);
    }


    @GetMapping(value = "/{managerId}")
    @CheckSecurity(roles = {"manager"})
    public ResponseEntity<Page<Notification>> getNotificationsByManager(@RequestHeader("Authorization") String authorization,@PathVariable Long managerId, Pageable pageable){
        return new ResponseEntity<>(notificationService.findAllNotificationsForManager(pageable, managerId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity deleteNotificaiton(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<Notification> updateNotification(@RequestBody @Valid NotificationUpdateDto notificationUpdateDto, @RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        return new ResponseEntity<>(notificationService.updateNotification(id, notificationUpdateDto), HttpStatus.OK);
    }
}
