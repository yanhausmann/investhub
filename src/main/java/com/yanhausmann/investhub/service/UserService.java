package com.yanhausmann.investhub.service;

import com.yanhausmann.investhub.dto.CreateUserDTO;
import com.yanhausmann.investhub.dto.UpdateUserDTO;
import com.yanhausmann.investhub.entity.User;
import com.yanhausmann.investhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //Conversão da classe DTO para Entity
    public UUID createUser(CreateUserDTO createUserDTO) {
        var entity = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){

        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {

        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDTO updateUserDTO) {

        var id = UUID.fromString(userId);
        var userEntity =  userRepository.findById(id);

        if (userEntity.isPresent()){
            var user = userEntity.get();

            if (updateUserDTO.username() != null) {
                user.setUsername(updateUserDTO.username());
            }

            if (updateUserDTO.password() != null) {
                user.setPassword(updateUserDTO.username());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId){
        var id = UUID.fromString(userId);
        var userExist = userRepository.existsById(id);

        if (userExist) {
            userRepository.deleteById(id);
        }
    }
}
