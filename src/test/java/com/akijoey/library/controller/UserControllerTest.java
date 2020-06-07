package com.akijoey.library.controller;

import com.akijoey.library.util.TokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class UserControllerTest {

    @Autowired
    TokenUtil tokenUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    void register() throws Exception {
        Map<String, Object> data = Map.of("username", "user", "password", 123456);
        mockMvc.perform(post("/api/user/register")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andDo(document("user/register", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void login() throws Exception {
        Map<String, Object> data = Map.of("username", "user", "password", 123456);
        mockMvc.perform(post("/api/user/login")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andDo(document("user/login", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    void getInfo() throws Exception {
        String token = tokenUtil.generateToken("user");
        mockMvc.perform(get("/api/user/info")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("user/info", preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    void getDetail() throws Exception {
        String token = tokenUtil.generateToken("user");
        mockMvc.perform(get("/api/user/detail")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("user/detail", preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    void upload() {
    }

    @Test
    void update() {
    }

    @Test
    void passwd() {
    }

    @Test
    void logout() throws Exception {
        String token = tokenUtil.generateToken("user");
        mockMvc.perform(post("/api/user/logout")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("user/logout", preprocessResponse(prettyPrint())));
    }

}