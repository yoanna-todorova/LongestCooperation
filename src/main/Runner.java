package main;

import java.util.Scanner;

import static main.Utility.printResultCooperationData;
public class Runner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.next();
        printResultCooperationData(fileName);
    }
}
