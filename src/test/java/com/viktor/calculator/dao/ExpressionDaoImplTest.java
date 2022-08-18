package com.viktor.calculator.dao;

import com.viktor.calculator.domain.Expression;
import com.viktor.calculator.exceptions.DaoException;
import com.viktor.calculator.service.CalculateService;
import com.viktor.calculator.service.impl.CalculatorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;


public class ExpressionDaoImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ExpressionDaoImplTest.class);

    CalculateService calculateService;

    ExpressionDao expressionDao;

    public ExpressionDaoImplTest() {
        this.calculateService = new CalculatorImpl();
        this.expressionDao = new ExpressionDaoImpl();
    }

    @BeforeClass
    public static void startUp() throws Exception {
        DBInit.startUp();
    }

    @Before
    public void startTest() {
        System.out.println("Start test");
    }

    @Test
    public void testFindExpressionGreaterThanOk() throws DaoException {
        List<Expression> d = new ExpressionDaoImpl().findExpressionsWhereResultIsGreaterThan(15.0);
        Assert.assertTrue(d.size() == 3);
    }

    @Test
    public void testFindExpressionLessThanOk() throws DaoException {
        List<Expression> d = new ExpressionDaoImpl().findExpressionsWhereResultIsLessThan(15.0);
        Assert.assertTrue(d.size() == 1);
    }

    @Test
    public void saveExpressionOk() throws DaoException {
        String expression = "15 + 15";
        Expression result = buildExpression(expression);
        expressionDao.saveExpression(result);
    }

    public Expression buildExpression(String expression) {
        Double calculate = calculateService.calculate(expression);
        Expression e = new Expression(expression);
        e.setResult(calculate);
        return e;
    }

}