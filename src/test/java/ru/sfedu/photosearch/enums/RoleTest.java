package ru.sfedu.photosearch.enums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.Main;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    private static Logger log = LogManager.getLogger(RoleTest.class);

    @Test
    void values() {
        log.info(Role.valueOf("CUSTOMER"));
    }

    @Test
    void valueOf() {

    }
}