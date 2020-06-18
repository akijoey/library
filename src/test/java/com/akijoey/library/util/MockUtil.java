package com.akijoey.library.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.OperationRequest;
import org.springframework.restdocs.operation.OperationRequestFactory;
import org.springframework.restdocs.operation.OperationResponse;
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.regex.Pattern;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Component
public class MockUtil {

    @Autowired
    TokenUtil tokenUtil;

    public MockMvc setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())
                .build();
    }

    public void perform(MockMvc mockMvc, MockHttpServletRequestBuilder request, OperationPreprocessor... preprocessors) throws Exception {
        ResultActions resultActions = mockMvc.perform(request);
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        String url = resultActions.andReturn().getRequest().getRequestURI();
        String path = url.split("/")[2] + "/" + url.split("/")[3];
        resultActions.andDo(document(path, preprocessRequest(preprocessors), preprocessResponse(prettyPrint())));
    }

    public MockHttpServletRequestBuilder get(String url, Object... variables) {
        return RestDocumentationRequestBuilders.get(url, variables);
    }
    public MockHttpServletRequestBuilder get(String token, String url, Object... variables) {
        return RestDocumentationRequestBuilders.get(url, variables).header("Authorization", "Bearer " + token);
    }

    public MockHttpServletRequestBuilder post(String url) {
        return RestDocumentationRequestBuilders.post(url);
    }
    public MockHttpServletRequestBuilder post(String token, String url) {
        return post(url).header("Authorization", "Bearer " + token);
    }
    public MockHttpServletRequestBuilder post(String url, Map<String, Object> data) throws JsonProcessingException {
        return post(url).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(new ObjectMapper().writeValueAsString(data));
    }
    public MockHttpServletRequestBuilder post(String token, String url, Map<String, Object> data) throws JsonProcessingException {
        return post(url, data).header("Authorization", "Bearer " + token);
    }

    public MockHttpServletRequestBuilder fileUpload(String url, MockMultipartFile file) {
        return RestDocumentationRequestBuilders.fileUpload(url).file(file);
    }
    public MockHttpServletRequestBuilder fileUpload(String token, String url, MockMultipartFile file) {
        return fileUpload(url, file).header("Authorization", "Bearer " + token);
    }


    public void mockGet(MockMvc mockMvc, String url, Object... variables) throws Exception {
        perform(mockMvc, get(url, variables));
    }
    public void mockGet(MockMvc mockMvc, String username, String url, Object... variables) throws Exception {
        String token = tokenUtil.generateToken(username);
        perform(mockMvc, get(token, url, variables), hiddenToken(username));
        tokenUtil.removeToken(username);
    }

    public void mockPost(MockMvc mockMvc, String url) throws Exception {
        perform(mockMvc, post(url));
    }
    public void mockPost(MockMvc mockMvc, String username, String url) throws Exception {
        String token = tokenUtil.generateToken(username);
        perform(mockMvc, post(token, url), hiddenToken(username));
        tokenUtil.removeToken(username);
    }
    public void mockPost(MockMvc mockMvc, String url, Map<String, Object> data) throws Exception {
        perform(mockMvc, post(url, data), prettyPrint());
    }
    public void mockPost(MockMvc mockMvc, String username, String url, Map<String, Object> data) throws Exception {
        String token = tokenUtil.generateToken(username);
        perform(mockMvc, post(token, url, data), prettyPrint(), hiddenToken(username));
        tokenUtil.removeToken(username);
    }

    public void mockFileUpload(MockMvc mockMvc, String url, MockMultipartFile file) throws Exception {
        String content = "< " + file.getOriginalFilename() + " >";
        perform(mockMvc, fileUpload(url, file), replacePattern(Pattern.compile("^.*$"), content));
    }
    public void mockFileUpload(MockMvc mockMvc, String username, String url, MockMultipartFile file) throws Exception {
        String token = tokenUtil.generateToken(username);
        String content = "< " + file.getOriginalFilename() + " >";
        perform(mockMvc, fileUpload(token, url, file), replacePattern(Pattern.compile("^.*$"), content), hiddenToken(username));
        tokenUtil.removeToken(username);
    }

    private OperationPreprocessor hiddenToken(String username) {
        return new OperationPreprocessor() {
            @Override
            public OperationRequest preprocess(OperationRequest request) {
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(request.getHeaders());
                headers.setBearerAuth("< " + username + " token >");
                return new OperationRequestFactory()
                        .create(request.getUri(), request.getMethod(), request.getContent(), headers, request.getParameters(), request.getParts());
            }
            @Override
            public OperationResponse preprocess(OperationResponse response) {
                return response;
            }
        };
    }
}
