package com.viktor.calculator.service;

import com.viktor.calculator.dto.ExpressionDto;
import com.viktor.calculator.entity.Expression;

import java.util.List;
import java.util.Optional;

public interface ExpressionService {

    Expression saveExpression(ExpressionDto expressionDto);

    List<Expression> findAllExpressions();

    List<ExpressionDto> findExpressionsWhereResultEquals(Double value);

    List<ExpressionDto> findExpressionsWhereResultIsGreaterThan(Double value);

    List<ExpressionDto> findExpressionsWhereResultIsLessThan(Double value);

    Expression findExpressionById(Long id);

    void deleteById(Long id);

    Optional<Expression> updateExpression(Long id, ExpressionDto expression);

}
