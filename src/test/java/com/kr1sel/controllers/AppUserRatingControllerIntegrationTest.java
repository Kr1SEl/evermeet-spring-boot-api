package com.kr1sel.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"/sql/appUserControllerIT/addUsers.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class AppUserRatingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserDetailsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Kyrylo"))
                .andExpect(MockMvcResultMatchers.jsonPath("age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("location").value("Wroclaw"))
                .andExpect(MockMvcResultMatchers.jsonPath("interests", containsInAnyOrder("GAMING", "IT", "BEER")));
    }

    @Test
    void getUserDetailsFailsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/user/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}