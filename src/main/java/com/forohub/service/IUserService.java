package com.forohub.service;

import com.forohub.dto.UserDto;
import com.forohub.entity.UserAuthor;
import com.forohub.exceptions.BadRequestException;
import com.forohub.exceptions.ResourceNotFoundException;

import java.util.List;
public interface IUserService {

    List<UserDto> getUsers();

    UserDto getUserById(Long id) throws ResourceNotFoundException;

    UserDto postUser(UserAuthor userAuthor) throws BadRequestException;

    UserDto updateUser(UserAuthor userAuthor);

    String deleteUser(Long id) throws ResourceNotFoundException;
}
