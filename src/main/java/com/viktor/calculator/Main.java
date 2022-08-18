package com.viktor.calculator;

import com.viktor.calculator.dao.ConnectionBuilder;
import com.viktor.calculator.dao.ExpressionDao;
import com.viktor.calculator.dao.ExpressionDaoImpl;
import com.viktor.calculator.domain.Expression;
import com.viktor.calculator.service.CalculateService;
import com.viktor.calculator.service.impl.CalculatorImpl;
import com.viktor.calculator.utils.Menu;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import static com.viktor.calculator.utils.Constants.*;

public class Main {

    CalculateService calculateService;

    ExpressionDao expressionDao = new ExpressionDaoImpl();

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        startUp();
        Menu menu = new Menu();
        Scanner in = new Scanner(System.in);
        while (true) {
            switch (menu.menuChooseMode()) {
                case EXIT:
                    return;
                case CALCULATE:
                    this.calculateService = new CalculatorImpl();
                    System.out.println("Please, enter your expression:");
                    String expression = in.nextLine();
                    Double result = calculateService.calculate(expression);
                    Expression e = new Expression(expression);
                    e.setResult(result);
                    expressionDao.saveExpression(e);
                    System.out.println(countNumberOfNumbersInString(expression));
                    break;
                case FIND_ALL_EXPRESSIONS:
                    System.out.println("All expressions in database:");
                    List<Expression> expressionList = expressionDao.findAllExpressions();
                    if (expressionList.isEmpty()) {
                        System.out.println("No expressions at all.");
                    } else {
                        for (Expression ex : expressionList) {
                            System.out.println(ex);
                        }
                    }
                    break;
                case FIND_EXPRESSIONS_RESULT_EQUALS:
                    System.out.println("Please, input value");
                    Double val = in.nextDouble();
                    List<Expression> expressionsWhereResultEquals = expressionDao.findExpressionsWhereResultEquals(val);
                    if (expressionsWhereResultEquals.isEmpty()) {
                        System.out.println("No expressions for : " + val);
                    } else {
                        for (Expression ex : expressionsWhereResultEquals) {
                            System.out.println(ex);
                        }
                    }
                    break;
                case FIND_EXPRESSIONS_RESULT_GREATER_THAN:
                    System.out.println("Please, input value");
                    Double value = in.nextDouble();
                    List<Expression> expressionsWhereResultIsGreaterThan = expressionDao.findExpressionsWhereResultIsGreaterThan(value);
                    if (expressionsWhereResultIsGreaterThan.isEmpty()) {
                        System.out.println("No expressions where result greater than : " + value);
                    } else {
                        for (Expression ex : expressionsWhereResultIsGreaterThan) {
                            System.out.println(ex);
                        }
                    }
                    break;
                case FIND_EXPRESSIONS_RESULT_LESS_THAN:
                    System.out.println("Please, input value");
                    Double v = in.nextDouble();
                    List<Expression> expressionsWhereResultIsLessThan = expressionDao.findExpressionsWhereResultIsLessThan(v);
                    if (expressionsWhereResultIsLessThan.isEmpty()) {
                        System.out.println("No expressions where result less than : " + v);
                    } else {
                        for (Expression ex : expressionsWhereResultIsLessThan) {
                            System.out.println(ex);
                        }
                    }
                    break;
                case UPDATE_EXPRESSION:
                    this.calculateService = new CalculatorImpl();
                    System.out.println("Please, write new expression");
                    String newExpression = in.nextLine();
                    System.out.println("Please, input id:");
                    Long l = in.nextLong();
                    long l1 = expressionDao.updateExpression(l, newExpression);
                    Expression expressionById = expressionDao.findExpressionById(l);
                    String updatedExpression = expressionById.getExpression();
                    Double calculate = calculateService.calculate(updatedExpression);
                    expressionDao.updateResultOfExpression(l, calculate);
                    System.out.println("Updated " + l1 + "expression: " + expressionById);
                    break;
                default:
                    System.out.println("Illegal option. Please try again");
            }
        }
    }

    private static void startUp() throws Exception {
        URL url1 = Main.class.getClassLoader().getResource("create_model.sql");
        List<String> strings1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = String.join("", strings1);
        try (Connection connection = ConnectionBuilder.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql1);
        }
    }

    public static int countNumberOfNumbersInString(String input) {
        int count = 0;
        boolean isPreviousDigit = false;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '.') {
                isPreviousDigit = true;
                continue;
            }
            if (Character.isDigit(input.charAt(i))) {
                if (!isPreviousDigit) {
                    count++;
                    isPreviousDigit = true;
                }
            } else {
                isPreviousDigit = false;
            }
        }
        return count;
    }

}
