package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class CoreConfigurationTest {

    @Test
    void restTemplate() {
        // given
        CoreConfiguration coreConfiguration = new CoreConfiguration();

        // when
        RestTemplate restTemplate = coreConfiguration.restTemplate();

        // then
        assertNotNull(restTemplate);
    }
}
