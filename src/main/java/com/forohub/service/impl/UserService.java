package com.forohub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forohub.dto.UserDto;
import com.forohub.entity.User;
import com.forohub.exceptions.ResourceNotFoundException;
import com.forohub.repository.UserRepository;
import com.forohub.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = users.stream().map(user -> objectMapper.convertValue(user, UserDto.class)).collect(Collectors.toList());

        if ( usersDto.size() > 0 ) {
            log.info("List of users found: " + usersDto);
        }

        log.warn("List of users are empty");

        return usersDto;
    }

    @Override
    public UserDto getUserById(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto;

        if ( user != null ) {
            userDto = objectMapper.convertValue(user, UserDto.class);
            log.info("User found: " + userDto);
            return userDto;
        }

        log.error("The user with {} was not found", id);
        throw new ResourceNotFoundException("Not found the user with id " + id);
    }

    @Override
    public UserDto postUser(User user) {
        User userSave = userRepository.save(user);
        UserDto userDto = objectMapper.convertValue(userSave, UserDto.class);
        log.info("User save successfully: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(User user) {
        return null;
    }

    @Override
    public String deleteUser(Long id) throws ResourceNotFoundException {
        if ( getUserById(id) != null ) {
            userRepository.deleteById(id);
            log.warn("User with id {} was delete successfully", id);
        }

        return "User was delete successfully";
    }
}
