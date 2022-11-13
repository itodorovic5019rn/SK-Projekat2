package com.raf.userservice.mapper;

import com.raf.userservice.domain.User;
import com.raf.userservice.dto.UserCreateDto;
import com.raf.userservice.dto.UserDto;
import com.raf.userservice.dto.UserUpdateDto;
import com.raf.userservice.mapper.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setPhone(user.getPhone());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setRole(user.getRole());
        userDto.setActive(user.getActive());
        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setPhone(userCreateDto.getPhone());
        if(!userCreateDto.getParseDate(userCreateDto.getBirthDate())){
            throw new BadRequestException("Invalid birth date format");
        }
        if((userCreateDto.getHireDate() != null) && !(userCreateDto.getParseDate(userCreateDto.getHireDate()))){
            throw new BadRequestException("Invalid hire date format");
        }
        user.setBirthDate(userCreateDto.getBirthDate());
        user.setHireDate(userCreateDto.getHireDate());
        user.setPassportNum(userCreateDto.getPassportNum());
        user.setActive("no");
        return user;
    }

    public User updateUser(User user, UserUpdateDto userUpdateDto){
        if(userUpdateDto.getEmail() != null)
            user.setEmail(userUpdateDto.getEmail());
        if(userUpdateDto.getFirstName() != null)
            user.setFirstName(userUpdateDto.getFirstName());
        if(userUpdateDto.getLastName() != null)
            user.setLastName(userUpdateDto.getLastName());
        if(userUpdateDto.getUsername() != null)
            user.setUsername(userUpdateDto.getUsername());
        if(userUpdateDto.getPassword() != null)
            user.setPassword(userUpdateDto.getPassword());
        if(userUpdateDto.getPhone() != null)
            user.setPhone(userUpdateDto.getPhone());
        if(userUpdateDto.getBirthDate() != null)
            user.setBirthDate(userUpdateDto.getBirthDate());
        if(userUpdateDto.getHireDate() != null)
            user.setHireDate(userUpdateDto.getHireDate());
        if(userUpdateDto.getPassportNum() != null)
            user.setPassportNum(userUpdateDto.getPassportNum());
        return user;
    }

}
