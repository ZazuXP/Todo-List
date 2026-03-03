import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public void adTask(String description) {
        Task task = new Task(nextId, description);
        tasks.add(task);
        nextId++;
    }

    public void getAllTask() {
        tasks.forEach(System.out::println);
    }

    public void markTaskAsDone(int id) {
        for (Task elem: tasks) {
            if (elem.getId() == id) {
                elem.markAsDone();
            }
        }
    }
}
