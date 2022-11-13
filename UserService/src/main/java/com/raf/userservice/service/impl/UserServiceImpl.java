package com.raf.userservice.service.impl;

import com.raf.userservice.domain.Rank;
import com.raf.userservice.domain.User;
import com.raf.userservice.dto.*;
import com.raf.userservice.listener.MessageHelper;
import com.raf.userservice.mapper.exception.BadRequestException;
import com.raf.userservice.mapper.exception.ForbiddenException;
import com.raf.userservice.mapper.exception.NotFoundException;
import com.raf.userservice.mapper.UserMapper;
import com.raf.userservice.repository.UserRepository;
import com.raf.userservice.security.service.TokenService;
import com.raf.userservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private TokenService tokenService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private RankImpl rankImpl;
    private JmsTemplate jmsTemplate;
    private String registerUserDestination;
    private String updatePasswordDestination;
    private MessageHelper messageHelper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService, RankImpl rankImpl,
                           JmsTemplate jmsTemplate,@Value("${destination.registerUser}") String registerUserDestination,
                          @Value("${destination.updatePassword}") String updatePasswordDestination, MessageHelper messageHelper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.rankImpl = rankImpl;
        this.jmsTemplate = jmsTemplate;
        this.registerUserDestination = registerUserDestination;
        this.messageHelper = messageHelper;
        this.updatePasswordDestination = updatePasswordDestination;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto addClient(UserCreateDto userCreateDto) {
        Optional<User> checkUser = userRepository.findUserByEmail(userCreateDto.getEmail());
        if(checkUser.isPresent()) {
            throw new BadRequestException("User with given email already exists!");
        }
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        user.setRole("client");
        user.setReservationNum(0);
        userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(user);
        jmsTemplate.convertAndSend(registerUserDestination, messageHelper.createTextMessage(userDto));

        return userDto;
    }

    @Override
    public UserDto addManager(UserCreateDto userCreateDto) {
        Optional<User> checkUser = userRepository.findUserByEmail(userCreateDto.getEmail());
        if(checkUser.isPresent()) {
            throw new BadRequestException("User with given email already exists!");
        }
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        user.setRole("manager");
        user.setReservationNum(0);
        userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(user);
        jmsTemplate.convertAndSend(registerUserDestination, messageHelper.createTextMessage(userDto));

        return userDto;
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException("User with given id not found!"));
        userRepository.deleteUserById(id);
    }

    @Override
    public UserDto update(UserUpdateDto userUpdateDto, Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException("User with given id not found!"));

        if(userUpdateDto.getPassword() != null && !(userUpdateDto.getPassword().equals(user.getPassword()))){
            user = userMapper.updateUser(user, userUpdateDto);
            jmsTemplate.convertAndSend(updatePasswordDestination, messageHelper.createTextMessage(userMapper.userToUserDto(user)));
        }else
            user = userMapper.updateUser(user, userUpdateDto);

        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public void updateActive(UserCreateActiveDto userCreateActiveDto, Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException("User with given id not found!"));
        user.setActive(userCreateActiveDto.getActive());
        userRepository.save(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository
                .findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with given username and password doesn't exist.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));
        if(user.getActive().equals("no")){ // TODO ako admin zabrani nekome pristup
            throw new ForbiddenException("Your registration is not verified or your account is currently blocked.");
        }
        Claims claims = Jwts.claims();
        claims.put("role", user.getRole());
        claims.put("id", user.getId());
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public DiscountDto getDiscount(Long id) {
        List<Rank> ranks = rankImpl.getAllRanks();
        Optional<User> user = userRepository.findUserById(id);
        if(!user.isPresent()){
            throw new NotFoundException("User with given id doesn't exist!");
        }
        User u = user.get();
        for(Rank r: ranks){
            if(u.getReservationNum() >= r.getBotLimit() && u.getReservationNum() <= r.getTopLimit()){
                DiscountDto discountDto = new DiscountDto();
                discountDto.setDiscount(r.getDiscount());
                discountDto.setName(r.getName());
                return discountDto;
            }
        }
        return new DiscountDto(null, (double)0);
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> checkUser = userRepository.findUserById(id);
        if(!checkUser.isPresent()) {
            throw new NotFoundException("User with given id doesn't exist!");
        }
        return userMapper.userToUserDto(checkUser.get());
    }

    @Override
    public void incrementReservationForUser(IncrementReservationCountDto incrementReservationCountDto) {
        userRepository.findById(incrementReservationCountDto.getUserId()).ifPresent(user -> {
            user.setReservationNum(user.getReservationNum()+1);
            userRepository.save(user);
        });
    }

    @Override
    public void decrementReservationForUser(DecrementReservationCountDto decrementReservationCountDto) {
        userRepository.findById(decrementReservationCountDto.getUserId()).ifPresent(user -> {
            user.setReservationNum(user.getReservationNum()-1);
            userRepository.save(user);
        });
    }

    @Override
    public void confirmRegistration(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(!user.isPresent()){
            throw new NotFoundException("User with given email doesn't exist.");
        }
        user.get().setActive("yes");
        userRepository.save(user.get());
        return;
    }
}
