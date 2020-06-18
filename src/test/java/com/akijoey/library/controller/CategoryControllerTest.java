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

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class CategoryControllerTest {

    private static final String PREFIX = "/api/category";

    @Autowired
    MockUtil mockUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = mockUtil.setUp(webApplicationContext, restDocumentation);
    }

    @Test
    void getSide() throws Exception {
        String url = PREFIX + "/side";
        mockUtil.mockGet(mockMvc, "user", url);
    }

    @Test
    void getList() throws Exception {
        String url = PREFIX + "/list";
        mockUtil.mockGet(mockMvc, "user", url);
    }

}