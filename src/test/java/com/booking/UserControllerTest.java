package com.booking;

import com.booking.controller.UserController;
import com.booking.entity.User;
import com.booking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserControllerTest  {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setEmail("testMail");
        user1.setFirstName("Mayur");
        user1.setLastName("Sharma");
        user1.setId(1L);
        List<User> allusers = new ArrayList<>();
        allusers.add(user1);
        Mockito.when(userRepository.findAll()).thenReturn(allusers);
        List<User> allUsers = userController.getAllUsers();
        assertEquals(allUsers.get(0).getFirstName(),"Mayur");
    }

    @Test
    void getUserById() {
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}