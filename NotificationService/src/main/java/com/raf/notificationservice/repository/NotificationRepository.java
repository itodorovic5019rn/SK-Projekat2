package com.raf.notificationservice.repository;

import com.raf.notificationservice.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByEmailTo(String emailTo);
}
