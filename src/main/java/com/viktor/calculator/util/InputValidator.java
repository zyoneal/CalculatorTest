package com.viktor.calculator.util;

public class InputValidator {

    public static String validInputExpression(String input) {
        String validSymbols = "0123456789-+*/(). ";
        return validator(input, validSymbols);
    }

    public static String validInputValue(String input) {
        String validSymbols = "0123456789.-";
        return validator(input, validSymbols);
    }

    private static String validator(String input, String validSymbols) {
        StringBuilder errorMessage = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String currSymbol = String.valueOf(input.charAt(i));
            if (!validSymbols.contains(currSymbol)) errorMessage.append("Exception: ").append(currSymbol).append(" is not valid; ");
        }
        return errorMessage.toString();
    }

}
