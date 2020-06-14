package com.akijoey.library.controller;

import com.akijoey.library.util.MockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BookControllerTest {

    private static final String PREFIX = "/api/book";

    @Autowired
    MockUtil mockUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = mockUtil.builder(webApplicationContext, restDocumentation);
    }

    @Test
    void getTotal() {
        String url = PREFIX + "/total/{cid}";
        mockUtil.authenticateGet(mockMvc, url, 5);
    }

    @Test
    void getList() {
        String url = PREFIX + "/list/{page}/{size}/{cid}";
        mockUtil.authenticateGet(mockMvc, url, 1, 10, 5);
    }

    @Test
    void getTable() {
        String url = PREFIX + "/table/{page}/{size}/{cid}";
        mockUtil.authenticateGet(mockMvc, url, 1, 10, 5);
    }

    @Test
    void getDetail() {
        String url = PREFIX + "/detail";
        Map<String, Object> data = Map.of("isbn", 9787020028115L);
        mockUtil.authenticatePost(mockMvc, url, data);
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