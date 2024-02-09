package com.yanhausmann.investhub.service;

import com.yanhausmann.investhub.dto.CreateUserDTO;
import com.yanhausmann.investhub.dto.UpdateUserDTO;
import com.yanhausmann.investhub.entity.User;
import com.yanhausmann.investhub.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//Arrange
//Act
//Assert

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123456",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDTO(
                    "Test_Username",
                    "test_email@test.com",
                    "123456"
            );

            var output = userService.createUser(input);

            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOcurrs() {

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123456",
                    Instant.now(),
                    null
            );

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO(
                    "Test_Username",
                    "test_email@test.com",
                    "123456"
            );

            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123456",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(user.getUserId().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }


        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {

            var userId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(userId.toString());

            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123456",
                    Instant.now(),
                    null
            );

            var userList = List.of(user);
            doReturn(List.of(user))
                    .when(userRepository)
                    .findAll();

            //Act
            var output = userService.listUsers();

            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteById{

        @Test
        @DisplayName("Should delete user with success when user exist")
        void shouldDeleteUserWithSuccessWhenUserExist() {

            //Arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            //act
            userService.deleteById(userId.toString());

            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Should not delete a user if it does not exist")
        void shouldNotDeleteUserWhenUserNotExist() {

            //Arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            //act
            userService.deleteById(userId.toString());

            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(0)).deleteById(any());

        }
    }

    @Nested
    class updateUserById{

        @Test
        @DisplayName("Should update user by id with success when user exist and username/password if filled")
        void shouldUpdateUserByIdWithSuccessWhenOptionalIsPresent() {

            var updateUserDTO = new UpdateUserDTO(
                    "newPassword",
                    "newPassword"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123456",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            //Act
            userService.updateUserById(user.getUserId().toString(), updateUserDTO);

            //Assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDTO.username(), userCaptured.getUsername());
            assertEquals(updateUserDTO.password(), userCaptured.getPassword());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Should not update user when user not exist")
        void shouldNotUpdateUserWhenNotExist() {

            var updateUserDTO = new UpdateUserDTO(
                    "newPassword",
                    "newPassword"
            );

            var userId = UUID.randomUUID();

            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            userService.updateUserById(userId.toString(), updateUserDTO);

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).save(any());
        }

    }

}