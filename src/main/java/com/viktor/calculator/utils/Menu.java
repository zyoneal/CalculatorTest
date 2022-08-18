package com.viktor.calculator.utils;

import java.util.Scanner;

public class Menu {

    public int menuChooseMode() {
        System.out.println("Main menu\n" +
                "1. Посчитать выражение\n" +
                "2. Получить все выражения\n" +
                "3. Найти выражения значения которых равно числу\n" +
                "4. Найти выражения значения которых больше\n" +
                "5. Найти выражения значения которых меньше\n" +
                "6. Обновить выражение\n" +
                "0. Exit");
        System.out.println();
        return new Scanner(System.in).nextInt();
    }

}
