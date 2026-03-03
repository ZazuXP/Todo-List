public class Task {
    private int id;
    private String description;
    private boolean isDone = false;

    public Task(int  id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void markAsDone(){
        isDone = true;
    }

    public String toString(){
        return id + ". " + "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
