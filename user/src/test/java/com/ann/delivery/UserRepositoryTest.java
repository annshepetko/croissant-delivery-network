package com.ann.delivery;

import com.ann.delivery.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    private void beforeEach(){
        this.user = User.builder()
                .email("ann.ssh@example.com")
                .build();
    }

    @Test
    void findByEmail() {

        User savedUser = userRepository.save(user);

        User foundUserByEmail = userRepository.findByEmail(savedUser.getEmail()).get();

        Assertions.assertNotNull(foundUserByEmail);
        Assertions.assertEquals(savedUser.getId(), foundUserByEmail.getId());
        Assertions.assertEquals(savedUser.getEmail(), foundUserByEmail.getEmail());

    }

    @Test
    void findByEmailWhichDoesntExist(){

        String unExistedEmail = "aann.ssh@gmail.com";

        Optional<User> user = userRepository.findByEmail(unExistedEmail);

        Assertions.assertTrue(user.isEmpty());

    }
}