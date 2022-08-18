package com.viktor.calculator.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Collections.unmodifiableSet;

public final class Constants {
    //--Operations
    public static final String PLUS = "+";

    public static final String MINUS = "-";

    public static final String MULTIPLY = "*";

    public static final String DIVIDE = "/";

    //--Menu
    public static final int EXIT = 0;

    public static final int CALCULATE = 1;

    public static final int FIND_ALL_EXPRESSIONS = 2;

    public static final int FIND_EXPRESSIONS_RESULT_EQUALS = 3;

    public static final int FIND_EXPRESSIONS_RESULT_GREATER_THAN = 4;

    public static final int FIND_EXPRESSIONS_RESULT_LESS_THAN = 5;

    public static final int UPDATE_EXPRESSION = 6;

    //--ExpressionDao
    public static final String GET_ALL_EXPRESSIONS = "SELECT title FROM expression";

    public static final String GET_EXPRESSIONS_WHERE_RESULT_EQUALS = "SELECT title FROM expression WHERE result = ?";

    public static final String GET_EXPRESSIONS_WHERE_RESULT_GREATER = "SELECT title " +
     "FROM expression e " +
     "WHERE result > ?";

    public static final String GET_EXPRESSIONS_WHERE_RESULT_LESS = "SELECT title " +
     "FROM expression e " +
     "WHERE result < ?";

    public static final String INSERT_EXPRESSION = "INSERT INTO expression (title, result) VALUES (?, ?);";

    public static final String GET_EXPRESSION_BY_ID = "SELECT title, result FROM expression WHERE id = ?";

    public static final String UPDATE_EXPRESSION_BY_ID = "UPDATE expression SET title = ? WHERE id = ?";

    public static final String UPDATE_RESULT_BY_UPDATED_EXPRESSION = "UPDATE expression SET result = ? WHERE id = ?";

    public static final Set<Character> PLUS_MINUS = Set.of('+', '-');

    public static final Set<Character> MULTIPLY_DIVIDE = Set.of('*', '/');

    public static final Set<Character> OPERATORS = unmodifiableSet(new HashSet<>() {{
        addAll(PLUS_MINUS);
        addAll(MULTIPLY_DIVIDE);
    }});

    public static final Pattern EXPRESSION_PART_PATTERN = Pattern.compile("((\\d*\\.\\d+)|(\\d+)|([\\+\\-\\*/\\(\\)]))");

    private Constants() {
        throw new UnsupportedOperationException();
    }

}
