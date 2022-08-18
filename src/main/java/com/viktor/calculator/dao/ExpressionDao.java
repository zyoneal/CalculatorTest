package com.viktor.calculator.dao;

import com.viktor.calculator.domain.Expression;
import com.viktor.calculator.exceptions.DaoException;

import java.util.List;

public interface ExpressionDao {

    Long saveExpression(Expression expression) throws DaoException;

    List<Expression> findAllExpressions() throws DaoException;

    List<Expression> findExpressionsWhereResultEquals(Double value) throws DaoException;

    List<Expression> findExpressionsWhereResultIsGreaterThan(Double value) throws DaoException;

    List<Expression> findExpressionsWhereResultIsLessThan(Double value) throws DaoException;

    Expression findExpressionById(Long id) throws DaoException;

    long updateExpression(Long id, String expression) throws DaoException;

    long updateResultOfExpression(Long id, Double resultOfExpression) throws DaoException;

}
