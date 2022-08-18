package com.viktor.calculator.repository;

import com.viktor.calculator.dto.ExpressionDto;
import com.viktor.calculator.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {

    @Query("SELECT new com.viktor.calculator.dto.ExpressionDto(e.expression) FROM Expression e WHERE e.result = :value")
    List<ExpressionDto> findExpressionsWhereResultEquals(@Param("value") Double value);

    @Query("SELECT new com.viktor.calculator.dto.ExpressionDto(e.expression) FROM Expression e WHERE e.result > :value")
    List<ExpressionDto> findExpressionsWhereResultIsGreaterThan(@Param("value") Double value);

    @Query("SELECT new com.viktor.calculator.dto.ExpressionDto(e.expression) FROM Expression e WHERE e.result < :value")
    List<ExpressionDto> findExpressionsWhereResultIsLessThan(@Param("value") Double value);

    @Query("SELECT e FROM Expression e WHERE e.id = :id")
    Expression findExpressionById(@Param("id") Long id);

}
