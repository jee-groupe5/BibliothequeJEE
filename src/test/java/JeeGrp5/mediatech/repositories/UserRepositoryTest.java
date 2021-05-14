package JeeGrp5.mediatech.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        log.trace("START TEST setUp ...");
        assertNotNull(repository,"UserRepository is NULL");
    }

    @Test
    void findAllUser(){
        log.trace("START findAllUser");
        var lst = repository.findAll();
        lst.forEach(c->log.trace("user : {}",c));
        log.trace("Number of users :{}",lst.size());
        log.trace("END findAllUser");
    }

    @Test
    void findByFirstname(){
        var lst = repository.findByFirstname("Maxence");
        log.trace(lst.toString());
    }
}