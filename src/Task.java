import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Task {
    private int id;
    private String description;
    private boolean isDone = false;
    private LocalDate deadline;

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

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getDeadlineStatus() {
        if (deadline == null) {
            return "(нет дедлайна)";
        }
        if (isDone) {
            return "(задача выполнена)";
        }

        LocalDate today = LocalDate.now();

        if (today.isBefore(deadline)) {
            long daysLeft = ChronoUnit.DAYS.between(today, deadline);
            return ConsoleColors.GREEN + "(осталось " + daysLeft + " дней)" + ConsoleColors.RESET;
        } else if(today.isEqual(deadline)) {
            return ConsoleColors.YELLOW + "(дедлайн сегодня!)" + ConsoleColors.RESET;
        } else {
            long daysOverdue = ChronoUnit.DAYS.between(deadline, today);
            return ConsoleColors.RED + "(просрочена на " + daysOverdue + " дней)" + ConsoleColors.RESET;
        }
    }

    @Override
    public String toString(){
        String status;
        if (isDone) {
            status = ConsoleColors.GREEN + "[X]" + ConsoleColors.RESET;
        } else {
            status = ConsoleColors.YELLOW + "[ ]" + ConsoleColors.RESET;
        }
        return id + ". " + status + description + " " + getDeadlineStatus();
    }
}
