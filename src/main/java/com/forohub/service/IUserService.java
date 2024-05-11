package com.forohub.service;

import com.forohub.dto.UserDto;
import com.forohub.entity.User;
import com.forohub.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUserService {

    List<UserDto> getUsers();

    UserDto getUserById(Long id) throws ResourceNotFoundException;

    UserDto postUser(User user);

    UserDto updateUser(User user);

    String deleteUser(Long id) throws ResourceNotFoundException;
}
