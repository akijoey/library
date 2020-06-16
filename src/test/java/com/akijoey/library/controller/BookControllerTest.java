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
    @Transactional
    void insert() {
        String url = PREFIX + "/insert";
        Map<String, Object> data = Map.of("isbn", 9787020055272L, "cover", "https://img1.doubanio.com/view/subject/s/public/s3333559.jpg", "title", "朝花夕拾", "author", "鲁迅", "press", "人民文学出版社", "date", "2006-12", "page", 116, "summary", "中西的思想确乎有一点不同。听说中国的孝子们，一到将要“罪孽深重祸延父母”的时候，就买几斤人参，煎汤灌下去，希望父母多喘几天气，即使半天也好。我的一位教医学的先生却教给我医生的职务道：可医的应该给他医治，不可医的应该给他死得没有痛苦。——但这先生自然是西医。", "cid", 1);
        mockUtil.authenticatePost(mockMvc, url, data);
    }

    @Test
    @Transactional
    void update() {
        String url = PREFIX + "/update";
        Map<String, Object> data = Map.of("isbn", 9787020028115L, "cover", "https://img3.doubanio.com/view/subject/s/public/s1146040.jpg", "title", "骆驼祥子", "author", "老舍", "press", "人民文学出版社", "date", "2000-3-1", "page", 225, "summary", "《骆驼祥子》是老舍用同情的笔触描绘的一幕悲剧：二十年代的北京，一个勤劳、壮实的底层社会小人物怀着发家、奋斗的美好梦想，却最终为黑暗的暴风雨所吞噬。它揭示了当时“小人物”的奴隶心理和希望的最终破灭。随着祥子心爱的女人小福子的自杀，祥子熄灭了个人奋斗的最后一朵火花。这是旧中国老北京贫苦市民的典型命运。", "cid", 1);
        mockUtil.authenticatePost(mockMvc, url, data);
    }

    @Test
    @Transactional
    void delete() {
        String url = PREFIX + "/delete";
        Map<String, Object> data = Map.of("isbn", 9787020028115L);
        mockUtil.authenticatePost(mockMvc, url, data);
    }

}