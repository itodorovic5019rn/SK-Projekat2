package com.raf.notificationservice.service;

import com.raf.notificationservice.domain.Notification;
import com.raf.notificationservice.dto.NotificationUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    void sendSimpleMessage(String to, String subject, String text);

    void createNotification(String email, String type, String text);

    Page<Notification> findAllNotifications(Pageable pageable);

    Page<Notification> findAllNotificationsForManager(Pageable pageable, Long managerId);

    void deleteNotification(Long id);

    Notification updateNotification(Long id, NotificationUpdateDto notificationUpdateDto);



}
