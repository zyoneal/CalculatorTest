package com.viktor.calculator.domain;

public class Expression {

    private String expression;

    private Double result;

    public Expression(String expression) {
        this.expression = expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return expression;
    }

}
