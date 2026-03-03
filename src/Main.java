import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");
            String input = scanner.nextLine();
            if (input.startsWith("/add")) {
                taskManager.adTask(input.substring(5));
                System.out.println("Задача добавлена!");
            } else if (input.startsWith("/list")) {
                taskManager.getAllTask();
            } else if (input.startsWith("/done")) {
                taskManager.markTaskAsDone(Integer.parseInt(input.substring(6)));
                System.out.println("Задача помечена как выполненная!");
            } else if (input.startsWith("/exit")) {
                System.out.println("Вы вышли из программы");
                break;
            }
        }
    }
}