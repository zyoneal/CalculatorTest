package com.viktor.calculator.utils;

import java.util.Scanner;

public class Menu {

    public int menuChooseMode() {
        System.out.println("Main menu\n" +
                "1. Calculate expression\n" +
                "2. Find all expressions in database\n" +
                "3. Find expressions where result is equals to a number\n" +
                "4. Find expressions where result is greater than\n" +
                "5. Find expressions where result is less than\n" +
                "6. Update expression\n" +
                "0. Exit");
        System.out.println();
        return new Scanner(System.in).nextInt();
    }

}
