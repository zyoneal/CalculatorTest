package com.viktor.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.viktor.calculator.dto.ExpressionDto;
import com.viktor.calculator.service.ExpressionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExpressionControllerIT {

    @InjectMocks
    private ExpressionController expressionController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ExpressionService expressionService;

    private final String URI = "/expressions/add";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(expressionController)
                .build();
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(this.jdbcTemplate, "expression");
    }

    @Test
    void getAddExpressionPage_isOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(this.URI))
                .andExpect(status().isOk())
                .andExpect(view().name("add-expression"))
                .andDo(print());
    }

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void addExpressionPage_isOk() throws Exception {
        ExpressionDto expressionDto = new ExpressionDto();
        expressionDto.setExpression("5+5");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson= objectWriter.writeValueAsString(expressionDto);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI)
                .contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:list"))
                .andDo(print());
    }



}
