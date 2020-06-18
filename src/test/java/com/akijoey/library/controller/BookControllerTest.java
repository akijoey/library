package com.akijoey.library.controller;

import com.akijoey.library.util.MockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;
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
        mockMvc = mockUtil.setUp(webApplicationContext, restDocumentation);
    }

    @Test
    void getTotal() throws Exception {
        String url = PREFIX + "/total/{cid}";
        mockUtil.mockGet(mockMvc, "user", url, 5);
    }

    @Test
    void getList() throws Exception {
        String url = PREFIX + "/list/{page}/{size}/{cid}";
        mockUtil.mockGet(mockMvc, "user", url, 1, 10, 5);
    }

    @Test
    void getTable() throws Exception {
        String url = PREFIX + "/table/{page}/{size}/{cid}";
        mockUtil.mockGet(mockMvc, "user", url, 1, 10, 5);
    }

    @Test
    void getDetail() throws Exception {
        String url = PREFIX + "/detail";
        Map<String, Object> data = Map.of("isbn", 9787020028115L);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

    @Test
    @Transactional
    void insert() throws Exception {
        String url = PREFIX + "/insert";
        Map<String, Object> data = Map.of("isbn", 9787020055272L, "cover", "https://img1.doubanio.com/view/subject/s/public/s3333559.jpg", "title", "朝花夕拾", "author", "鲁迅", "press", "人民文学出版社", "date", "2006-12", "page", 116, "summary", "中西的思想确乎有一点不同。听说中国的孝子们，一到将要“罪孽深重祸延父母”的时候，就买几斤人参，煎汤灌下去，希望父母多喘几天气，即使半天也好。我的一位教医学的先生却教给我医生的职务道：可医的应该给他医治，不可医的应该给他死得没有痛苦。——但这先生自然是西医。", "cid", 1);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

    @Test
    @Transactional
    void count() throws Exception {
        String url = PREFIX + "/count";
        Map<String, Object> data = Map.of("isbn", 9787020028115L, "count", 10);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

    @Test
    void upload() throws Exception {
        String url = PREFIX + "/upload";
        String cover = "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";
        byte[] content = Base64.getDecoder().decode(cover);
        MockMultipartFile file = new MockMultipartFile("cover", "cover.jpg", "multipart/form-data", content);
        mockUtil.mockFileUpload(mockMvc, "user", url, file);
    }

    @Test
    @Transactional
    void update() throws Exception {
        String url = PREFIX + "/update";
        Map<String, Object> data = Map.of("isbn", 9787020028115L, "cover", "https://img3.doubanio.com/view/subject/s/public/s1146040.jpg", "title", "骆驼祥子", "author", "老舍", "press", "人民文学出版社", "date", "2000-3-1", "page", 225, "summary", "《骆驼祥子》是老舍用同情的笔触描绘的一幕悲剧：二十年代的北京，一个勤劳、壮实的底层社会小人物怀着发家、奋斗的美好梦想，却最终为黑暗的暴风雨所吞噬。它揭示了当时“小人物”的奴隶心理和希望的最终破灭。随着祥子心爱的女人小福子的自杀，祥子熄灭了个人奋斗的最后一朵火花。这是旧中国老北京贫苦市民的典型命运。", "cid", 1);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

    @Test
    @Transactional
    void delete() throws Exception {
        String url = PREFIX + "/delete";
        Map<String, Object> data = Map.of("isbn", 9787020028115L);
        mockUtil.mockPost(mockMvc, "user", url, data);
    }

}