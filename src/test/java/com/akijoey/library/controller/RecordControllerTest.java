package com.akijoey.library.controller;

import com.akijoey.library.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class RecordControllerTest {

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
    void getTotal() throws Exception {
        String token = tokenUtil.generateToken("user");
        mockMvc.perform(get("/api/record/total")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("record/total", preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    void getTable() throws Exception {
        String token = tokenUtil.generateToken("user");
        mockMvc.perform(get("/api/record/table/{page}/{size}", 1, 10)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("record/table", preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    @Transactional
    void borrowing() throws Exception {
        String token = tokenUtil.generateToken("user");
        Map<String, Object> data = Map.of("isbn", 9787020028115L);
        mockMvc.perform(post("/api/record/borrow")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andDo(document("record/borrow", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    @Transactional
    void returning() throws Exception {
        String token = tokenUtil.generateToken("user");
        Map<String, Object> data = Map.of("id", 1);
        mockMvc.perform(post("/api/record/return")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andDo(document("record/return", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    @Transactional
    void renewing() throws Exception {
        String token = tokenUtil.generateToken("user");
        Map<String, Object> data = Map.of("id", 1, "timestamp", 1596931200000L);
        mockMvc.perform(post("/api/record/renew")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andDo(document("record/renew", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

}