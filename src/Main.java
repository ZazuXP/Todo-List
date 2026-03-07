import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        taskManager.loadFromFile("resources/tasks.txt");
        taskManager.clearFile("resources/tasks.txt");

        while (true) {
            System.out.print("Введите команду: ");
            String input = scanner.nextLine();
            try {
                if (input.startsWith("/add")) {
                    taskManager.adTask(input.substring(5));
                    System.out.println("Задача добавлена!");
                } else if (input.startsWith("/list")) {
                    System.out.println("Список задач:");
                    taskManager.getAllTask();
                } else if (input.startsWith("/done")) {
                    try {
                        int id = Integer.parseInt(input.substring(6));
                        if (id > taskManager.nextId) {
                            System.out.println("Ошибка: введённый id превышает существующие в списке");
                        } else if (taskManager.checkDoneTasks(id)) {
                            System.out.println("Ошибка: эта задача уже помечена как выполненная");
                        } else {
                            taskManager.markTaskAsDone(id);
                            System.out.println("Задача помечена как выполненная!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: введите числовое представление id");
                    }
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
                    taskManager.saveToFile("resources/tasks.txt");
                    System.out.println("Вы вышли из программы. Задачи успешно сохранены!");
                    break;
                } else {
                    System.out.println("Ошибка: введена неизвестная команда");
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Ошибка: после ввода команды введите описание или id задачи");
            }
        }
    }
}