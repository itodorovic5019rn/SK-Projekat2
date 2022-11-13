package com.raf.userservice.service;

import com.raf.userservice.domain.User;
import com.raf.userservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto addClient(UserCreateDto userCreateDto);

    UserDto addManager(UserCreateDto userCreateDto);

    void deleteUserById(Long id);

    UserDto update(UserUpdateDto userUpdateDto, Long id);

    void updateActive(UserCreateActiveDto userCreateActiveDto, Long id);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    DiscountDto getDiscount(Long id);

    UserDto findUserById(Long id);

    void incrementReservationForUser(IncrementReservationCountDto incrementReservationCountDto);

    void decrementReservationForUser(DecrementReservationCountDto decrementReservationCountDto);

    void confirmRegistration(String email);

}
