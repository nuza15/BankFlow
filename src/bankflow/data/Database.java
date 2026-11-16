package bankflow.data;

import bankflow.model.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** In-memory data container. The repository persists this object to disk. */
public class Database implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<User> users = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private final List<TaskType> taskTypes = new ArrayList<>();
    private final List<Status> statuses = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final List<HistoryEntry> history = new ArrayList<>();
    private int nextId = 1;

    public int nextId() { return nextId++; }

    public List<User> getUsers() { return users; }
    public List<Project> getProjects() { return projects; }
    public List<TaskType> getTaskTypes() { return taskTypes; }
    public List<Status> getStatuses() { return statuses; }
    public List<Task> getTasks() { return tasks; }
    public List<HistoryEntry> getHistory() { return history; }

    /** Adds the initial status labels when the database is first created. */
    public void initializeDefaults() {
        if (statuses.isEmpty()) {
            statuses.add(new Status()); // TODO: Replace with a fully initialized To Do status.
        }
    }
}
