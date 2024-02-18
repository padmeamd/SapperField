package lesson8;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Choose a level: ");
        System.out.println("1 - beginner");
        System.out.println("2 - amateur");
        System.out.println("3 - professional");
        System.out.println("4 - special");

        Scanner scanner = new Scanner(System.in);
        String level = scanner.nextLine(); // текст "1" или "beginner"

        SapperField sapperField = new SapperField(level);
        // ожидание - в конструкторе создастся массив размером по заданному пользователем уровню

        sapperField.addRandomMines();
        sapperField.addAndCountNums();
        sapperField.printField();

        while (sapperField.doAStep()){ // или следующий шаг, или выход из игры
            sapperField.printField();
            // печатаем текущее поле,
        }



    }
}
