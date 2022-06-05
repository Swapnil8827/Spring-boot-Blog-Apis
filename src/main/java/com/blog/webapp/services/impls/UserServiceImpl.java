package com.blog.webapp.services.impls;

import com.blog.webapp.daos.UserDao;
import com.blog.webapp.exceptions.ResourceNotFoundException;
import com.blog.webapp.models.User;
import com.blog.webapp.payloads.UserDto;
import com.blog.webapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println(" User Name "+user.getName());

        User savedUser = userDao.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userDao
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setName (userDto.getName ());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout ());
        User updatedUser = this.userDao.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userDao
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> allUsers = userDao.findAll();

       return allUsers.stream()
               .map(this::userToDto)
               .collect(Collectors.toList());

    }

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userDao
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userDao.delete(user);
    }

    public User dtoToUser(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }
    public UserDto userToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

}
