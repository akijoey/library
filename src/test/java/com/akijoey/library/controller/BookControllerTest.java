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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BookControllerTest {

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
        mockMvc.perform(get("/api/book/total/{cid}", 5)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("book/total", preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    @WithMockUser(username = "user")
    void getList() throws Exception {
        String token = tokenUtil.generateToken("user");
        mockMvc.perform(get("/api/book/list/{page}/{size}/{cid}", 1, 10, 5)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("book/list", preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    @WithMockUser(username = "user")
    void getTable() throws Exception {
        String token = tokenUtil.generateToken("user");
        mockMvc.perform(get("/api/book/table/{page}/{size}/{cid}", 1, 10, 5)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token))
                .andDo(document("book/table", preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    @WithMockUser(username = "user")
    void getDetail() throws Exception {
        String token = tokenUtil.generateToken("user");
        Map<String, Object> data = Map.of("isbn", 9787020028115L);
        mockMvc.perform(post("/api/book/detail")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(data)))
                .andDo(document("book/detail", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
        tokenUtil.removeToken("user");
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}