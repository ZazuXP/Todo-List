import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        String filename = "resources/tasks.txt";

        System.out.println("Вас приветсвует менеджер задач!");

        taskManager.loadFromFile(filename);
        taskManager.clearFile(filename);
        taskManager.saveToFile(filename);

        System.out.println("Вы можете посмотреть инструкцию по командам, введя в консоль /printCom");

        while (true) {
            System.out.print("Введите команду: ");
            String input = scanner.nextLine();
            try {
                if (input.startsWith("/printCom")) {
                    taskManager.printAllCommands();
                }
                else if (input.startsWith("/add")) {
                    taskManager.adTask(input.substring(5));
                    System.out.print("Хотите установить крайний срок выполнения задачи? (напишите \"да\", если хотите): ");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("да")) {
                        System.out.print("Введите дату(день-месяц-полный год, всё в числовом формате, пример: 14-11-2008): ");
                        input = scanner.nextLine();
                        taskManager.setDate(input, (taskManager.nextId-1));
                    }
                    System.out.println("Задача добавлена!");
                    taskManager.clearFile(filename);
                    taskManager.saveToFile(filename);
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
                            taskManager.clearFile(filename);
                            taskManager.saveToFile(filename);
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
                            taskManager.clearFile(filename);
                            taskManager.saveToFile(filename);
                        }
                    } catch(NumberFormatException e) {
                        System.out.println("Ошибка: введите числовое представление id");
                    }
                } else if (input.startsWith("/removeAll")) {
                    taskManager.deleteAllTask();
                    taskManager.clearFile(filename);
                    System.out.println("Все задачи успешно удалены!");
                }
                else if (input.startsWith("/exit")) {
                    taskManager.clearFile(filename);
                    taskManager.saveToFile(filename);
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