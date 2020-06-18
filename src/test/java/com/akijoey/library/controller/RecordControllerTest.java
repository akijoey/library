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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class RecordControllerTest {

    private static final String PREFIX = "/api/record";

    @Autowired
    MockUtil mockUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = mockUtil.setUp(webApplicationContext, restDocumentation);
    }

    @Test
    void getTotal() throws Exception {
        String url = PREFIX + "/total";
        mockUtil.mockGet(mockMvc, "user", url);
    }

    @Test
    void getTable() throws Exception {
        String url = PREFIX + "/table/{page}/{size}";
        mockUtil.mockGet(mockMvc, "user", url, 1, 10);
    }

    @Test
    @Transactional
    void borrowing() throws Exception {
        String url = PREFIX + "/borrow";
        Map<String, Object> data = Map.of("isbn", 9787020028115L);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

    @Test
    @Transactional
    void returning() throws Exception {
        String url = PREFIX + "/return";
        Map<String, Object> data = Map.of("id", 1);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

    @Test
    @Transactional
    void renewing() throws Exception {
        String url = PREFIX + "/renew";
        Map<String, Object> data = Map.of("id", 1, "timestamp", 1596931200000L);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

    @Test
    @Transactional
    void delete() throws Exception {
        String url = PREFIX + "/delete";
        Map<String, Object> data = Map.of("id", 1);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

}