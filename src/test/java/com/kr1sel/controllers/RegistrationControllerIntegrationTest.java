package com.kr1sel.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"/sql/registrationControllerIT/removeUsers.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RegistrationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ksu\",\n" +
                                "        \"username\": \"ksenia\",\n" +
                                "        \"password\": \"ketksu1101\",\n" +
                                "        \"age\" : 21,\n" +
                                "        \"location\" : \"Poland\",\n" +
                                "        \"interests\" : [\"MUSIC\", \"PETS\", \"SPORT\"]\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Sql(scripts = {"/sql/registrationControllerIT/addUser.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void registerUsernameIsTakenTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ksu\",\n" +
                                "        \"username\": \"ksenia\",\n" +
                                "        \"password\": \"ketksu1101\",\n" +
                                "        \"age\" : 21,\n" +
                                "        \"location\" : \"Poland\",\n" +
                                "        \"interests\" : [\"MUSIC\", \"PETS\", \"SPORT\"]\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void registerUsernameIsOutOfBounds() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ksu\",\n" +
                                "        \"username\": \"ks\",\n" +
                                "        \"password\": \"ketksu1101\",\n" +
                                "        \"age\" : 21,\n" +
                                "        \"location\" : \"Poland\",\n" +
                                "        \"interests\" : [\"MUSIC\", \"PETS\", \"SPORT\"]\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("username").value(containsString("Username should be between")));
    }

    @Test
    void registerNameIsOutOfBounds() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ks\",\n" +
                                "        \"username\": \"ksenia\",\n" +
                                "        \"password\": \"ketksu1101\",\n" +
                                "        \"age\" : 21,\n" +
                                "        \"location\" : \"Poland\",\n" +
                                "        \"interests\" : [\"MUSIC\", \"PETS\", \"SPORT\"]\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(containsString("Name should be between")));
    }

    @Test
    void registerPasswordIsOutOfBounds() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ksu\",\n" +
                                "        \"username\": \"ksenia\",\n" +
                                "        \"password\": \"ket\",\n" +
                                "        \"age\" : 21,\n" +
                                "        \"location\" : \"Poland\",\n" +
                                "        \"interests\" : [\"MUSIC\", \"PETS\", \"SPORT\"]\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("password").value(containsString("Password should be between")));
    }

    @Test
    void registerAgeIsOutOfBounds() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ksu\",\n" +
                                "        \"username\": \"ksenia\",\n" +
                                "        \"password\": \"ketksu1101\",\n" +
                                "        \"age\" : 14,\n" +
                                "        \"location\" : \"Poland\",\n" +
                                "        \"interests\" : [\"MUSIC\", \"PETS\", \"SPORT\"]\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("age").value(containsString("Your age should be over")));
    }

    @Test
    void registerLocationIsOutOfBounds() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ksu\",\n" +
                                "        \"username\": \"ksenia\",\n" +
                                "        \"password\": \"ketksu1101\",\n" +
                                "        \"age\" : 21,\n" +
                                "        \"location\" : \"P\",\n" +
                                "        \"interests\" : [\"MUSIC\", \"PETS\", \"SPORT\"]\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("location").value(containsString("Location name should be between")));
    }

    @Test
    void registerInterestsIsOutOfBounds() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"name\": \"Ksu\",\n" +
                                "        \"username\": \"ksenia\",\n" +
                                "        \"password\": \"ketksu1101\",\n" +
                                "        \"age\" : 21,\n" +
                                "        \"location\" : \"Poland\",\n" +
                                "        \"interests\" : []\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("interests").value(containsString("You should choose at least one interest")));
    }


}