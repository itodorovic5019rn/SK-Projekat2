package com.raf.notificationservice.service.impl;

import com.raf.notificationservice.domain.Notification;
import com.raf.notificationservice.dto.NotificationUpdateDto;
import com.raf.notificationservice.dto.UserDto;
import com.raf.notificationservice.exception.NotFoundException;
import com.raf.notificationservice.exception.ServerErrorException;
import com.raf.notificationservice.mapper.NotificationMapper;
import com.raf.notificationservice.repository.NotificationRepository;
import com.raf.notificationservice.service.NotificationService;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private JavaMailSender mailSender;
    private NotificationRepository notificationRepository;
    private NotificationMapper notificationMapper;
    private RestTemplate userServiceApiClient;
    private Retry userServiceRetry;


    public NotificationServiceImpl(JavaMailSender mailSender, NotificationRepository notificationRepository,
                                   NotificationMapper notificationMapper,  @Qualifier("userServiceApiClient") RestTemplate userServiceApiClient, Retry userServiceRetry) {
        this.mailSender = mailSender;
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userServiceApiClient = userServiceApiClient;
        this.userServiceRetry = userServiceRetry;
    }
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void createNotification(String email, String type, String text) {
        Notification notification = new Notification(text, type, email,getTodayParseDate(new Date()));
        notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> findAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public Page<Notification> findAllNotificationsForManager(Pageable pageable, Long managerId) {
        UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto( managerId)).get();

        List<Notification> notificationList = notificationRepository.findAllByEmailTo(userDto.getEmail());
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), notificationList.size());
        final Page<Notification> page = new PageImpl<>(notificationList.subList(start, end), pageable, notificationList.size());
        return page;



    }

    @Override
    public void deleteNotification(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if(!notification.isPresent())
            throw new NotFoundException("Notification with given id doesn't exist.");
        notificationRepository.delete(notification.get());
    }

    @Override
    public Notification updateNotification(Long id, NotificationUpdateDto notificationUpdateDto) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if(!notification.isPresent())
            throw new NotFoundException("Notification with given id doesn't exist.");

        Notification updateNotification = notificationMapper.notificationUpdateDtoToNotification(notification.get(),notificationUpdateDto);
        notificationRepository.save(updateNotification);
        return updateNotification;
    }

    private String getTodayParseDate(Date dateNow) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(dateNow);

        return format;
    }
    private UserDto getUserDto(Long id){
        System.out.println("Getting user...");
        try {
            ResponseEntity<UserDto> userDto = userServiceApiClient.exchange("/" + id, HttpMethod.GET, null, UserDto.class);
            return userDto.getBody();
        }catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 404)
                throw new NotFoundException("User with given id doesn't exist");
            throw  new RuntimeException("Internal server error!");
        }catch (Exception e){
            throw  new RuntimeException("Internal server error!");
        }

    }
}
