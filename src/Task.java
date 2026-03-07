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

    public String getDescription() {
        return description;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void markAsDone(){
        isDone = true;
    }

    public String toString(){
        return id + ". " + "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
