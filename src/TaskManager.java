import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private ArrayList<Task> deletedTasks = new ArrayList<>();
    private ArrayList<Task> doneTasks = new ArrayList<>();
    public int nextId = 1;

    public void printHeader(String header) {
        System.out.println("═══════════════════════════════════");
        System.out.println("            " + header);
        System.out.println("═══════════════════════════════════");
    }

    public void printAllCommands() {
        System.out.println("/add \"описание задачи\" - добавить задачу в список \t/list - вывести все задачи на экран");
        System.out.println("/deadline \"id задачи\" - установить крайний срок выполнения задачи по id \t/checkDL \"id задачи\" - проверить активность задачи по id");
        System.out.println("/delete \"id задачи\" - удалить задачу по id \t/removeAll - удалить все задачи из списка");
        System.out.println("/done \"id задачи\" - отметить задачу выполненной по id \t/exit - выйти из программы и сохранить текущие задачи");
    }

    public void adTask(String description) {
        Task task = new Task(nextId, description);
        tasks.add(task);
        nextId++;
    }

    public void getAllTask() {
        tasks.forEach(System.out::println);
    }

    public void deleteAllTask() {
        deletedTasks.addAll(tasks);
        tasks.clear();
    }

    public void markTaskAsDone(int id) {
        for (Task elem: tasks) {
            if (elem.getId() == id) {
                doneTasks.add(elem);
                elem.markAsDone();
            }
        }
    }

    public void deleteTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id){
                deletedTasks.add(tasks.get(i));
                tasks.remove(i);
                return;
            }
        }
    }

    public boolean checkDeletedTasks(int id) {
        for (Task elem: deletedTasks) {
            if (elem.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDoneTasks(int id) {
        for (Task elem: doneTasks) {
            if (elem.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void setDate(String deadline, int id) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(deadline, formatter);
            for (Task elem: tasks) {
                if (elem.getId() == id) {
                    elem.setDeadline(date);
                    System.out.println("Крайний срок установлен!");
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: невозможно преобразовать введённые данные в дату, введите числовое представление");
        }
    }

    public void checkDeadline(int id) {
        for (Task elem: tasks) {
            if(elem.getId() == id) {
                try {
                    LocalDate deadline = elem.getDeadline();
                    if(elem.getIsDone()){
                        System.out.println(ConsoleColors.GREEN + "Задача выполнена!" + ConsoleColors.RESET);
                    }
                    else if (deadline.isBefore(LocalDate.now())) {
                        System.out.println(ConsoleColors.RED + "Задача просрочена на " + ChronoUnit.DAYS.between(deadline, LocalDate.now()) + " дней" + ConsoleColors.RESET);
                    } else if(deadline.equals(LocalDate.now())) {
                        System.out.println(ConsoleColors.YELLOW + "Сегодня крайний срок завершения задачи!" + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.GREEN + "Задача всё ещё активна! До крайнего срока осталось " + ChronoUnit.DAYS.between(LocalDate.now(), deadline) + " дней" + ConsoleColors.RESET);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Ошибка: срок выполнения задачи не найден");
                }
            }
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            for (Task elem: tasks) {
                String task = elem.getId() + ";" + elem.getDescription() + ";" + elem.getIsDone();

                LocalDate deadline = elem.getDeadline();
                if (deadline != null) {
                    task += ";" + deadline;
                } else {
                    task += ";null";
                }
                writer.println(task);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8)) {
            tasks.clear();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0]);
                    String description = parts[1];
                    boolean isDone = Boolean.parseBoolean(parts[2]);

                    Task task = new Task(id, description);
                    task.setIsDone(isDone);

                    if (parts.length == 4 && !parts[3].isEmpty() && !parts[3].equals("null")) {
                        try {
                            LocalDate deadline = LocalDate.parse(parts[3]);
                            task.setDeadline(deadline);
                        } catch (DateTimeException e) {
                            System.out.println("Ошибка: неверный формат даты для задачи " + id + ": " + parts[3]);
                        }
                    }

                    tasks.add(task);

                    if (id >= nextId) {
                        nextId = id+1;
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Ошибка: файл с задачами не найден, будет создан новый при сохранении");
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }

    public void clearFile(String filename) {
        try(PrintWriter writer = new PrintWriter(filename)) {
            writer.write("");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
