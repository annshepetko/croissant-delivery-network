package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.entity.User;
import com.ann.delivery.enums.Roles;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private static final Logger logger = LoggerFactory.getLogger(UserEntityService.class);

    private final UserRepository userRepository;

    public boolean isUserAdmin(String username){

        logger.info("CHECKING IS USER ADMIN");

        User user = getUserByEmail(username);

        return user.getRole().equals(Roles.ADMIN);
    }

    public User getUserByEmail(String email){
        logger.info("SEARCHING USER BY EMAIL");

        return userRepository.findByEmail(email)
                .orElseThrow(() ->{
                    logger.error("USER NOT FOUND BY THIS EMAIL");
                    return new EntityNotFoundException("User with this emil does`nt exist");
                });

    }

    public boolean isUserAlreadyRegistered(String email){

        logger.info("CHECKING IS USER ALREADY EXISTS");
        return userRepository.findByEmail(email).isPresent();
    }


    public void saveUser(User user){

        logger.info("SAVING USER");

        userRepository.save(user);
    }
}
