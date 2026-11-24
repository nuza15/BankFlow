package bankflow.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/** One line of the audit trail. {@code user == null} means the System Scheduler. */
public class HistoryEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final LocalDateTime timestamp;
    private final String action;
    private final String oldValue;
    private final String newValue;
    private final User user;
    private final Task task;

    public HistoryEntry(int id, Task task, User user, String action, String oldValue, String newValue) {
        this(id, task, user, action, oldValue, newValue, LocalDateTime.now().withNano(0));
    }

    public HistoryEntry(int id, Task task, User user, String action, String oldValue, String newValue, LocalDateTime timestamp) {
        this.id = id; this.task = task; this.user = user;
        this.action = action; this.oldValue = oldValue; this.newValue = newValue; this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getAction() { return action; }
    public String getOldValue() { return oldValue; }
    public String getNewValue() { return newValue; }
    public User getUser() { return user; }
    public Task getTask() { return task; }
    public String getUserName() { return user == null ? "System Scheduler" : user.getFullName(); }
}
