package com.forohub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forohub.dto.UserDto;
import com.forohub.entity.Role;
import com.forohub.entity.UserAuthor;
import com.forohub.exceptions.BadRequestException;
import com.forohub.exceptions.ResourceNotFoundException;
import com.forohub.repository.UserRepository;
import com.forohub.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
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
        List<UserAuthor> userAuthors = userRepository.findAll();
        List<UserDto> usersDto = userAuthors.stream().map(user -> objectMapper.convertValue(user, UserDto.class)).collect(Collectors.toList());

        if ( usersDto.size() > 0 ) {
            log.info("List of users found: " + usersDto);
        } else {
            log.warn("List of users are empty");
        }

        return usersDto;
    }

    @Override
    public UserDto getUserById(Long id) throws ResourceNotFoundException {
        Optional<UserAuthor> user = userRepository.findById(id);
        UserDto userDto;

        if ( user.isPresent() ) {
            userDto = objectMapper.convertValue(user, UserDto.class);
            log.info("User with id {} was found -> {} ", id, userDto);
            return userDto;
        }

        log.error("The user with id {} was not found", id);
        throw new ResourceNotFoundException("Not found the user with id " + id);
    }

    @Override
    public UserDto postUser(UserAuthor userAuthor) throws BadRequestException {
        UserAuthor userAuthorSave = userRepository.save(userAuthor);
        UserDto userDto = objectMapper.convertValue(userAuthorSave, UserDto.class);
        log.info("User saved successfully: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserAuthor userAuthor) {
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
