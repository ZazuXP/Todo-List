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
            } else if (input.startsWith("/list")) {
                taskManager.getTask();
            }
        }
    }
}