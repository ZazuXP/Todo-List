import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public void adTask(String description) {
        Task task = new Task(nextId, description);
        tasks.add(task);
    }

    public void getTask() {
        tasks.forEach(System.out::println);
    }
}
