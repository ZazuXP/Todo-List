import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private ArrayList<Task> deletedTasks = new ArrayList<>();
    private ArrayList<Task> doneTasks = new ArrayList<>();
    public int nextId = 0;

    public void adTask(String description) {
        nextId++;
        Task task = new Task(nextId, description);
        tasks.add(task);
    }

    public void getAllTask() {
        tasks.forEach(System.out::println);
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

    public void saveToFile(String filename) {
        for (Task elem: tasks) {
            String task = elem.getId() + ";" + elem.getDescription() + ";" + elem.getIsDone();
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
                writer.println(task);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8)) {
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                for (Task elem: tasks) {
                    nextId++;
                    elem.setId(scanner.nextInt());
                    elem.setDescription(scanner.next());
                    elem.setIsDone(scanner.nextBoolean());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
