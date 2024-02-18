package lesson8;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class SapperField {
    private int[][] fieldWithMines; // оставляем данное поле пустым,
    // так как мы не знаем какой именно уровень выберет пользователь (сколько будет строк и колонок в массиве)
    private int amountOfMines = 0;// оставляем данное поле пустым,
    // так как мы не знаем сколько будет мин

    private boolean[][] fieldToOpen;
    private int countClosedCellsToWin = 0;

    public SapperField(String level) {
        if (level.equals("1") || level.equalsIgnoreCase("beginner")) {
            fieldWithMines = new int[9 + 2][9 + 2];
            amountOfMines = 10;
            fieldToOpen = new boolean[9 + 2][9 + 2];
            countClosedCellsToWin = amountOfMines + ((9 + 9) * 2) + 4;
        }
        if (level.equals("2") || level.equalsIgnoreCase("amateur")) {
            fieldWithMines = new int[16 + 2][16 + 2];
            amountOfMines = 40;
            fieldToOpen = new boolean[16 + 2][16 + 2];
            countClosedCellsToWin = amountOfMines + ((16 + 16) * 2) + 4;
        }
        if (level.equals("3") || level.equalsIgnoreCase("professional")) {
            fieldWithMines = new int[16 + 2][32 + 2];
            amountOfMines = 99;
            fieldToOpen = new boolean[16 + 2][32 + 2];
            countClosedCellsToWin = amountOfMines + ((16 + 32) * 2) + 4;
        }
        if (level.equals("4") || level.equalsIgnoreCase("special")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of rows: ");
            int rows = scanner.nextInt();

            System.out.println("Enter the number of columns: ");
            int columns = scanner.nextInt();

            System.out.println("Enter the number of columns: ");
            amountOfMines = scanner.nextInt();
            fieldWithMines = new int[rows + 2][columns + 2];
            countClosedCellsToWin = amountOfMines + ((columns + rows) * 2) + 4;

        }
    }

    public void addRandomMines() {
        for (int i = 0; i < amountOfMines; i++) {
            int row = ThreadLocalRandom.current().nextInt(1, fieldWithMines.length - 2);
            int column = ThreadLocalRandom.current().nextInt(1, fieldWithMines[0].length - 2);
            if (fieldWithMines[row][column] != -1) {
                fieldWithMines[row][column] = -1;
            } else {
                i--;
            }
        }

    }

    public void addAndCountNums() {
        for (int i = 0; i < fieldWithMines.length; i++) {
            for (int j = 0; j < fieldWithMines[0].length; j++) {
                // заполняем всех соседей + 1 к числу, если в клеточке мина
                if (fieldWithMines[i][j] == -1) {
                    if (fieldWithMines[i - 1][j - 1] != -1) {
                        fieldWithMines[i - 1][j - 1] = fieldWithMines[i - 1][j - 1] + 1;
                    }
                    if (fieldWithMines[i - 1][j] != -1) {
                        fieldWithMines[i - 1][j] = fieldWithMines[i - 1][j] + 1;
                    }
                    if (fieldWithMines[i - 1][j + 1] != -1) {
                        fieldWithMines[i - 1][j + 1] = fieldWithMines[i - 1][j + 1] + 1;
                    }
                    if (fieldWithMines[i + 1][j + 1] != -1) {
                        fieldWithMines[i + 1][j + 1] = fieldWithMines[i + 1][j + 1] + 1;
                    }
                    if (fieldWithMines[i][j - 1] != -1) {
                        fieldWithMines[i][j - 1] = fieldWithMines[i][j - 1] + 1;
                    }
                    if (fieldWithMines[i + 1][j - 1] != -1) {
                        fieldWithMines[i + 1][j - 1] = fieldWithMines[i + 1][j - 1] + 1;
                    }
                    if (fieldWithMines[i + 1][j] != -1) {
                        fieldWithMines[i + 1][j] = fieldWithMines[i + 1][j] + 1;
                    }
                    if (fieldWithMines[i][j + 1] != -1) {
                        fieldWithMines[i][j + 1] = fieldWithMines[i][j + 1] + 1;
                    }
                }

            }
        }
    }

    public void printField() {
        System.out.println("----Your field: ----");
        for (int i = 1; i < fieldWithMines.length - 1; i++) { // обходим первую и последнюю строку
            for (int j = 1; j < fieldWithMines[0].length - 1; j++) { // обходим первую и последнюю колонку
                if (fieldToOpen[i][j] == true) { // если клеточка открыта
                    System.out.print(fieldWithMines[i][j] + " ");

                } else { // если клеточка закрыта
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public void printEndGameField() {
        System.out.println("----Your field: ----");
        for (int i = 1; i < fieldWithMines.length - 1; i++) { // обходим первую и последнюю строку
            for (int j = 1; j < fieldWithMines[0].length - 1; j++) { // обходим первую и последнюю колонку
                if (fieldToOpen[i][j] == true || fieldWithMines[i][j] == -1) { // если клеточка открыта или там мина
                    System.out.print(fieldWithMines[i][j] + " ");

                } else { // если клеточка закрыта
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }


    public boolean doAStep() {
        if(countClosedCellsToWin == this.countClosedCells()){
            System.out.println("---CONGRATS! YOU WON THE GAME!!!---");
            this.printEndGameField();
            return false;
        }


        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a row: ");
        int row = scanner.nextInt();

        System.out.println("Choose a column: ");
        int column = scanner.nextInt();

        if (row <= 0 || row > fieldWithMines.length - 1 || column <= 0 || column > fieldWithMines[0].length - 1) { // если индексы не корректны,-1 потому что идем не до края)
            System.out.println(" ERROR! ENTER THE INDEXES CORRECTLY!");
            this.doAStep(); // рекурсивный вызов метода
        }

        if(fieldToOpen[row][column] == true){// проверка задублированного кода
            System.out.println(" You've already opened the sell, ENTER AGAIN!");
            this.doAStep();
        }
        // 100% знаем, что индексы нам подходят
        // экспресс проверка на окончание игры, если игрок наступил на мину
        if (fieldWithMines[row][column] == -1) {
            System.out.println("YOU LOST THE GAME!");
            this.printEndGameField();
            return false; // даем ответ, что еще один шаг не нужен
        } else {// Если в ячейке не мина и нужно продолжить игру дальше
            fieldToOpen[row][column] = true;
            return true; // даем ответ, что нужен СЛЕДУЮЩИЙ ШАГ
        }

    }

    private int countClosedCells() {
        int count = 0;
        for (int i = 0; i < fieldToOpen.length; i++) {
            for (int j = 0; j < fieldToOpen.length; j++) {
                if(fieldToOpen[i][j] == false){
                    count++;
                }

            }

        }
        return count;
    }
}
