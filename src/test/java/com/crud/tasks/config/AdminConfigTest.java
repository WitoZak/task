package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AdminConfigTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private AdminConfig adminConfig;

    @Test
    void getAdminMailTest() {
        // given
        String expectedAdminMail = "admin@example.com";
        Mockito.when(environment.getProperty("admin.mail")).thenReturn(expectedAdminMail);

        // when
        String actualAdminMail = adminConfig.getAdminMail();

        // then
        assertEquals(expectedAdminMail, actualAdminMail);
    }
}
