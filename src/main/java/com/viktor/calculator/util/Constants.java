package com.viktor.calculator.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Collections.unmodifiableSet;

public class Constants {

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
