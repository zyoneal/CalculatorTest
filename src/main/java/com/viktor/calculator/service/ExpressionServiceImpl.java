package com.viktor.calculator.service;

import com.viktor.calculator.dto.ExpressionDto;
import com.viktor.calculator.entity.Expression;
import com.viktor.calculator.repository.ExpressionRepository;
import com.viktor.calculator.service.impl.CalculateServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpressionServiceImpl implements ExpressionService {

    private final ExpressionRepository expressionRepository;

    @Override
    public Expression saveExpression(ExpressionDto expression) {
        CalculateService calculateService = new CalculateServiceImpl();
        Double result = calculateService.calculate(expression.getExpression());
        Expression e = new Expression();
        e.setExpression(expression.getExpression());
        e.setResult(result);
        return expressionRepository.save(e);
    }

    @Override
    public List<Expression> findAllExpressions() {
        return expressionRepository.findAll();
    }

    @Override
    public List<ExpressionDto> findExpressionsWhereResultEquals(Double value) {
        return expressionRepository.findExpressionsWhereResultEquals(value);
    }

    @Override
    public List<ExpressionDto> findExpressionsWhereResultIsGreaterThan(Double value) {
        return expressionRepository.findExpressionsWhereResultIsGreaterThan(value);
    }

    @Override
    public List<ExpressionDto> findExpressionsWhereResultIsLessThan(Double value) {
        return expressionRepository.findExpressionsWhereResultIsLessThan(value);
    }

    @Override
    public Expression findExpressionById(Long id) {
        return expressionRepository.findExpressionById(id);
    }

    @Override
    public void deleteById(Long id) {
        try {
            expressionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.info(exception.getMessage(), exception);
        }
    }

    @Override
    public Optional<Expression> updateExpression(Long id, ExpressionDto expression) {
        return Optional.of(expressionRepository.findExpressionById(id))
                .map(e -> {
                    Expression exp = new Expression();
                    exp.setId(id);
                    exp.setExpression(expression.getExpression());
                    CalculateService calculateService = new CalculateServiceImpl();
                    Double result = calculateService.calculate(expression.getExpression());
                    exp.setResult(result);
                    return expressionRepository.save(exp);
                });
    }

}
