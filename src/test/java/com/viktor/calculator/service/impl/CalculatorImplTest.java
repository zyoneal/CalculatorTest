package com.viktor.calculator.service.impl;

import com.viktor.calculator.exceptions.OperationException;
import com.viktor.calculator.service.CalculateService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorImplTest {

    CalculateService calculateService;

    CalculatorImplTest() {
        this.calculateService = new CalculatorImpl();
    }

    @Test
    void calculatePlusMinusOk() {
        String expression = "5 + 1 - 5 + 12 + 12.7";
        double result = calculateService.calculate(expression);
        assertEquals(25.7, result);
    }

    @Test
    void calculateMultiplyDivideOk() {
        String expression = "5 * 1 - 12 / 2 + 12.7";
        double result = calculateService.calculate(expression);
        assertEquals(11.7, result);
    }

    @Test
    void calculateWithBracketsOk() {
        String expression = "5 * ( 1 - 12 ) / 2 + ( 12.7 + 5 )";
        double result = calculateService.calculate(expression);
        assertEquals(-9.8, result);
    }

    @Test
    void calculateWithNegativeNumberOk() {
        String expression = "5 * -5";
        double result = calculateService.calculate(expression);
        assertEquals(-25, result);
    }

    @Test
    void failIf2OperationInRow() {
        String expression = "5 * + 5";
        assertThrows(OperationException.class, () -> calculateService.calculate(expression));
    }

}
