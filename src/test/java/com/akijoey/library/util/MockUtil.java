package com.akijoey.library.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.regex.Pattern;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Component
public class MockUtil {

    @Autowired
    TokenUtil tokenUtil;

    public MockMvc builder(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())
                .build();
    }

    public void permitGet(MockMvc mockMvc, String url, String params, Object... variables) {
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        try {
            mockMvc.perform(get(url, variables)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andDo(document(path, preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void permitPost(MockMvc mockMvc, String url) {
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        try {
            mockMvc.perform(post(url)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andDo(document(path, preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void permitPost(MockMvc mockMvc, String url, Map<String, Object> data) {
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        try {
            mockMvc.perform(post(url)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(new ObjectMapper().writeValueAsString(data)))
                    .andDo(document(path, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void permitFileUpload(MockMvc mockMvc, String url, MockMultipartFile file) {
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        String content = "< " + file.getOriginalFilename() + " >";
        try {
            mockMvc.perform(fileUpload(url).file(file)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andDo(document(path, preprocessRequest(replacePattern(Pattern.compile("^.*$"), content)), preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void authenticateGet(MockMvc mockMvc, String url, Object... variables) {
        String token = tokenUtil.generateToken("user");
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        try {
            mockMvc.perform(get(url, variables)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .header("Authorization", "Bearer " + token))
                    .andDo(document(path, preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tokenUtil.removeToken("user");
    }

    public void authenticatePost(MockMvc mockMvc, String url) {
        String token = tokenUtil.generateToken("user");
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        try {
            mockMvc.perform(post(url)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .header("Authorization", "Bearer " + token))
                    .andDo(document(path, preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tokenUtil.removeToken("user");
    }
    public void authenticatePost(MockMvc mockMvc, String url, Map<String, Object> data) {
        String token = tokenUtil.generateToken("user");
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        try {
            mockMvc.perform(post(url)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(new ObjectMapper().writeValueAsString(data)))
                    .andDo(document(path, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tokenUtil.removeToken("user");
    }

    public void authenticateFileUpload(MockMvc mockMvc, String url, MockMultipartFile file) {
        String token = tokenUtil.generateToken("user");
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        String content = "< " + file.getOriginalFilename() + " >";
        try {
            mockMvc.perform(fileUpload(url).file(file)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .header("Authorization", "Bearer " + token).contentType(MediaType.MULTIPART_FORM_DATA))
                    .andDo(document(path, preprocessRequest(replacePattern(Pattern.compile("^.*$"), content)), preprocessResponse(prettyPrint())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tokenUtil.removeToken("user");
    }
}
