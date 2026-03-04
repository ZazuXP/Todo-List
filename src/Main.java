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
                System.out.println("Список задач:");
                taskManager.getAllTask();
            } else if (input.startsWith("/done")) {
                taskManager.markTaskAsDone(Integer.parseInt(input.substring(6)));
                System.out.println("Задача помечена как выполненная!");
            }  else if(input.startsWith("/delete")) {
                try {
                    int id = Integer.parseInt(input.substring(8));
                    if (id > taskManager.nextId){
                        System.out.println("Ошибка: введённый id превышает существующие в списке");
                    } else if (taskManager.checkDeletedTasks(id)) {
                        System.out.println("Ошибка: эта задача уже была удалена");
                    } else {
                        taskManager.deleteTask(id);
                        System.out.println("Задача удалена!");
                    }
                } catch(NumberFormatException e) {
                    System.out.println("Ошибка: введите числовое представление id");
                }
            } else if (input.startsWith("/exit")) {
                System.out.println("Вы вышли из программы");
                break;
            }
        }
    }
}