package com.viktor.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.viktor.calculator.dto.ExpressionDto;
import com.viktor.calculator.entity.Expression;
import com.viktor.calculator.service.ExpressionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@WebMvcTest(controllers = ExpressionController.class)
@Import(ExpressionController.class)
public class ExpressionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpressionService expressionService;

    private final String URI = "/expressions";

    @Test
    void saveExpressionOk() throws Exception {
        String expression = "5+5";
        ExpressionDto expressionDto = new ExpressionDto();
        expressionDto.setExpression(expression);

        Expression expressionObj = new Expression();
        expressionObj.setExpression(expression);
        expressionObj.setResult(10.0);
        when(expressionService.saveExpression(any(ExpressionDto.class))).thenReturn(expressionObj);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(expressionDto);

        mockMvc.perform(post(URI + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is(302))
                .andDo(MockMvcResultHandlers.print());

        verify(expressionService, times(1)).saveExpression(any(ExpressionDto.class));
    }

    @Test
    void findAllExpressionOk() throws Exception {
        Expression e1 = Expression.builder()
                .id(1L)
                .expression("20+5")
                .result(25.)
                .build();

        Expression e2 = Expression.builder()
                .id(2L)
                .expression("50+50")
                .result(100.0)
                .build();

        List<Expression> list = List.of(e1, e2);
        when(expressionService.findAllExpressions()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(MockMvcResultHandlers.print());

        verify(expressionService, times(1)).findAllExpressions();
    }

    @Test
    void DeleteById_ShouldDeleteExpression() throws Exception {
        Expression e1 = Expression.builder()
                .id(1L)
                .expression("20+5")
                .result(25.0)
                .build();

        doNothing().when(expressionService).deleteById(e1.getId());

        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/delete/" + e1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(expressionService, times(1)).deleteById(anyLong());
    }

    @Test
    void updateById_ShouldUpdateExpression() throws Exception {
        Expression e1 = Expression.builder()
                .id(1L)
                .expression("20+5")
                .result(25.)
                .build();

        doNothing().when(expressionService).deleteById(e1.getId());

        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/delete/" + e1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(expressionService, times(1)).deleteById(anyLong());
    }

    @Test
    void updateExpressionOk() throws Exception {
        String expression = "5+5";
        String newExpression = "100+100";
        ExpressionDto expressionDto = new ExpressionDto();
        expressionDto.setExpression(newExpression);

        Expression oldExpr = new Expression();
        oldExpr.setId(1L);
        oldExpr.setExpression(expression);
        oldExpr.setResult(10.0);

        Expression newExpr = new Expression();
        newExpr.setId(1L);
        newExpr.setExpression(newExpression);
        newExpr.setResult(100.0);

        when(expressionService.saveExpression(any(ExpressionDto.class))).thenReturn(oldExpr);


        when(expressionService.updateExpression(oldExpr.getId(), expressionDto)).thenReturn(Optional.of(newExpr));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(expressionDto);

        mockMvc.perform(post(URI + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is(200))
                .andExpect(view().name("index"))
                .andDo(MockMvcResultHandlers.print());

        verify(expressionService, times(1)).updateExpression(any(), any(ExpressionDto.class));
    }


}
