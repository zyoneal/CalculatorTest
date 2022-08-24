package com.viktor.calculator.repository;

import com.viktor.calculator.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {

    @Query("SELECT e FROM Expression e WHERE e.result = :value")
    List<Expression> findExpressionsWhereResultEquals(@Param("value") Double value);

    @Query("SELECT e FROM Expression e WHERE e.result > :value")
    List<Expression> findExpressionsWhereResultIsGreaterThan(@Param("value") Double value);

    @Query("SELECT e FROM Expression e WHERE e.result < :value")
    List<Expression> findExpressionsWhereResultIsLessThan(@Param("value") Double value);

    @Query("SELECT e FROM Expression e WHERE e.id = :id")
    Expression findExpressionById(@Param("id") Long id);

}
