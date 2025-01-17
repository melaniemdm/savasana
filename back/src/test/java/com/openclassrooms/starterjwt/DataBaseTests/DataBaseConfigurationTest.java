package com.openclassrooms.starterjwt.DataBaseTests;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@ActiveProfiles("test")
public class DataBaseConfigurationTest {
    @Autowired
    private org.springframework.core.env.Environment environment;

    @Test
       public void testDatabaseUrl() {
        String dbUrl = environment.getProperty("spring.datasource.url");
        assertEquals("jdbc:h2:mem:testdb", dbUrl, "La base de données utilisée doit être H2 en mémoire");
    }
}
