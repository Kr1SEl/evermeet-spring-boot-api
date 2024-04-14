package com.kr1sel.controllers;

import com.kr1sel.repositories.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"/sql/appUserControllerIT/addUsers.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/sql/appUserControllerIT/dropRelations.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AppUserControllerIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @WithUserDetails("kris")
    void getUserDetailsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("username").value("kris"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Kyrylo"))
                .andExpect(MockMvcResultMatchers.jsonPath("age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("location").value("Wroclaw"))
                .andExpect(MockMvcResultMatchers.jsonPath("interests", containsInAnyOrder("GAMING", "IT", "BEER")));
    }

    @Test
    @WithUserDetails("kris")
    void getUserDetailsNotFoundTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/user/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithUserDetails("kris")
    void sendFriendshipRequestTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/send-friend-request/ksenia"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithUserDetails("kris")
    void sendFriendshipRequestUserNotExistsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/send-friend-request/johndoe"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithUserDetails("kris")
    void sendFriendshipRequestUserIsSenderUserTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/send-friend-request/kris"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithUserDetails("kris")
    void removeFriendshipRequestUserNotExistsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/remove-friend-request/johndoe"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithUserDetails("kris")
    void removeFriendshipRequestUserIsSenderUserTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/remove-friend-request/kris"))
                .andExpect(MockMvcResultMatchers.status().isNotModified());
    }

    @Test
    @WithUserDetails("kris")
    void removeFriendshipRequestNoRequestToRemoveTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/remove-friend-request/ksenia"))
                .andExpect(MockMvcResultMatchers.status().isNotModified());
    }

    @Test
    @WithUserDetails("kris")
    void removeFriendshipRequestTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/send-friend-request/ksenia"));
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/remove-friend-request/ksenia"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    @Sql(scripts = {"/sql/appUserControllerIT/addFriendshipRequests.sql"})
    @WithUserDetails("kris")
    void acceptFriendshipRequestTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/accept-friend-request/ksenia"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithUserDetails("kris")
    void acceptFriendshipRequestNoRequestTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/accept-friend-request/ksenia"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithUserDetails("kris")
    void acceptFriendshipRequestUserNotExistsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/accept-friend-request/johndoe"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithUserDetails("kris")
    void removeFriendUserNotExistsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/remove-friend/johndoe"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithUserDetails("kris")
    void removeFriendUserNotFriendsTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/remove-friend/ksenia"))
                .andExpect(MockMvcResultMatchers.status().isNotModified());
    }

    @Test
    @Sql(scripts = {"/sql/appUserControllerIT/addConnections.sql"})
    @WithUserDetails("kris")
    void removeFriendTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/user/remove-friend/ksenia"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}