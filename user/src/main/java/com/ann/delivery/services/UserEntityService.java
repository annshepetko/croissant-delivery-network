package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityService {


    private final UserRepository userRepository;

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with this emil does`nt exist"));

    }

    public boolean isUserAlreadyRegistered(String email){
        return userRepository.findByEmail(email).isPresent();
    }


}
