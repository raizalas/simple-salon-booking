package com.booking.service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ValidationService {
    public static java.util.Scanner scanner = new java.util.Scanner(System.in);

    public static String validateStringInput(String question, String errorMessage) {
        String result;
        String regex = "^[A-Za-z\\s]{1,20}$";
        boolean isLooping = true;
        do {
            System.out.print(question);
            result = scanner.nextLine();

            //validasi menggunakan matches
            if (result.matches(regex)) {
                isLooping = false;
            }else {
                System.out.println(errorMessage);
            }
        } while (isLooping);

        return result;
    }


    public static int validateNumberWithRange(String question, String errorMessage, String regex, int max, int min) {
        int result;
        boolean isLooping = true;

        do {
            result = Integer.parseInt(validateInput(question, errorMessage, regex));
            if (result >= min && result <= max) {
                isLooping = false;
            }else {
                System.out.println(errorMessage);
            }
        } while (isLooping);

        return result;
    }

    public static String validateInput(String question, String errorMessage, String regex) {
        String result;
        boolean isLooping = true;
        do {
            System.out.print(question);
            result = scanner.nextLine();

            //validasi menggunakan matches
            if (result.matches(regex)) {
                isLooping = false;
            }else {
                System.out.println(errorMessage);
            }
        } while (isLooping);

        return result;
    }

    public static boolean yesOrNoPrompt (String question) {
        System.out.println(question + " [Y/N] :");
        String input = scanner.nextLine().trim().toUpperCase();
        while(!input.equals("Y") && !input.equals("N")) {
            System.out.println("Input invalid ulangi kembali!");
            input = scanner.nextLine().trim().toUpperCase();
        }
        return input.equals("Y");
    }

    public static <T> T pencarianList(List<T> itemList, Matcher<T> matcher, String question, String errorMessage) {
        Optional<T> itemOptional;
        do {
            System.out.print(question + "(masukkan exit untuk keluar) : ");
            String searchValue = scanner.nextLine();
            if (searchValue.equalsIgnoreCase("exit")) {
                return null;
            }
            itemOptional = itemList.stream()
                    .filter(item -> matcher.matches(item, searchValue))
                    .findFirst();
            if (itemOptional.isPresent()) {
                return itemOptional.get();
            } else {
                System.out.println(errorMessage);
            }

        } while (!itemOptional.isPresent());

        return null;

    }
}
