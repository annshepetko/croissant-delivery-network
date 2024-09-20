package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.entity.User;
import com.ann.delivery.enums.Roles;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityService {


    private final UserRepository userRepository;

    public boolean isUserAdmin(String username){
        User user = getUserByEmail(username);

        return user.getRole().equals(Roles.ADMIN);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with this emil does`nt exist"));

    }

    public boolean isUserAlreadyRegistered(String email){
        return userRepository.findByEmail(email).isPresent();
    }


    public void saveUser(User user){
        userRepository.save(user);
    }
}
