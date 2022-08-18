package com.viktor.calculator.service.impl;

import com.viktor.calculator.exceptions.OperationException;
import com.viktor.calculator.service.CalculateService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;

import static com.viktor.calculator.utils.Constants.*;

public class CalculatorImpl implements CalculateService {

    public Double calculate(String s) {
        s = s.replaceAll(" ", "");

        List<String> parsed = parseExpression(s);
        List<String> exp = transformToPostfixNotation(parsed);

        Deque<Double> result = new ArrayDeque<>();

        for (String e : exp) {
            if (isOperator(e)) {
                switch (e) {
                    case "/": {
                        double p2 = result.pop();
                        double p1 = result.pop();

                        if (p2 == 0) {
                            throw new OperationException("Division by zero");
                        }
                        result.push(p1 / p2);
                    }
                    break;

                    case "*": {
                        double p1 = result.pop();
                        double p2 = result.pop();

                        result.push(p1 * p2);
                    }
                    break;

                    case "+": {
                        if (result.size() != 1) {
                            double p1 = result.pop();
                            double p2 = result.pop();

                            result.push(p1 + p2);
                        }
                    }
                    break;

                    case "-": {
                        if (result.size() != 1) {
                            double p1 = result.pop();
                            double p2 = result.pop();

                            result.push(p2 - p1);
                        } else {
                            double p1 = result.pop();
                            result.push(-p1);
                        }
                    }
                    break;
                }
            } else {
                result.push(Double.parseDouble(e));
            }
        }
        return result.pop();
    }

    private static List<String> transformToPostfixNotation(List<String> parsed) {
        List<String> exp = new ArrayList<>();
        Deque<String> operationStack = new ArrayDeque<>();
        for (String currElem : parsed) {
            if (isOperator(currElem)) {
                while (!operationStack.isEmpty() &&
                        !operationStack.peek().equals("(") &&
                        (compareOperationPriority(operationStack.peek(), currElem) >= 0)) {

                    exp.add(operationStack.pop());
                }
                operationStack.push(currElem);
            } else if (currElem.equals("(")) {
                operationStack.push(currElem);
            } else if (")".equals(currElem)) {
                while (!"(".equals(operationStack.peek())) {
                    exp.add(operationStack.pop());
                }
                operationStack.pop();
            } else {
                exp.add(currElem);
            }
        }
        while (!operationStack.isEmpty()) {
            exp.add(operationStack.pop());
        }
        return exp;
    }

    private static List<String> parseExpression(String s) {

        Matcher m = EXPRESSION_PART_PATTERN.matcher(s);

        List<String> parsed = new ArrayList<>();

        while (m.find()) {

            String elem = m.group();

            if (!isOperator(elem)) {

                if ((parsed.size() == 1) && isOperator(parsed.get(0)) && !"(".equals(elem)) {
                    char prev = parsed.get(0).charAt(0);
                    if (PLUS_MINUS.contains(prev)) {
                        parsed.remove(0);
                        elem = prev + elem;
                    }
                } else if ((parsed.size() >= 2) && !elem.equals("(")) {
                    String prev = parsed.get(parsed.size() - 1);
                    String prevPrev = parsed.get(parsed.size() - 2);
                    if (isOperator(prev) && isOperator(prevPrev)) {
                        parsed.remove(parsed.size() - 1);
                        elem = prev + elem;
                    }
                }
            }
            parsed.add(elem);
        }
        return parsed;
    }

    private static int compareOperationPriority(String firstOperation, String secondOperation) {
        Integer w1 = MULTIPLY_DIVIDE.contains(firstOperation.charAt(0)) ? 1 : 0;
        Integer w2 = MULTIPLY_DIVIDE.contains(secondOperation.charAt(0)) ? 1 : 0;
        return w1.compareTo(w2);
    }

    private static boolean isOperator(String o) {
        if (o.length() != 1) return false;
        char t = o.charAt(0);
        return OPERATORS.contains(t);
    }

}
