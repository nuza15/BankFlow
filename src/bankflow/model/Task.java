package bankflow.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public abstract class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String title = "";
    private String description = "";
    private int estimatedMinutes = 30;
    private Priority priority = Priority.MEDIUM;
    private TaskType type;
    private Status status;
    private Project project;
    private List<TeamMember> assignees = new ArrayList<>();
    private List<Subtask> subtasks = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private List<LocalDate> dates = new ArrayList<>();
    private final TeamMember createdBy;

    protected Task(int id, TeamMember createdBy) { this.id = id; this.createdBy = createdBy; }

    public abstract boolean isRecurring();
    /** Is the task scheduled on this day? */
    public abstract boolean occursOn(LocalDate day);
    /** Status as shown on a given day (recurring tasks have one status per occurrence). */
    public abstract Status getStatusOn(LocalDate day, Status fallback);
    public abstract void setStatusOn(LocalDate day, Status status);
    /** Moves the occurrence on {@code from} to {@code to}. */
    public abstract void reschedule(LocalDate from, LocalDate to);
    /** Date shown in lists: next scheduled date from {@code from}, else the last one. */
    public abstract LocalDate getNextDate(LocalDate from);

    public void changeStatus(Status s) { this.status = s; }
    public void escalatePriority() { this.priority = priority.next(); }
    public void addSubtask(Subtask s) { subtasks.add(s); }
    public void addNote(Note n) { notes.add(n); }

    /** Moves the earliest date to {@code newDate}. */
    public void reschedule(LocalDate newDate) {
        if (!dates.isEmpty()) reschedule(dates.get(0), newDate);
    }

    public String subtaskProgress() {
        if (subtasks.isEmpty()) return "";
        long done = subtasks.stream().filter(Subtask::isCompleted).count();
        return done + "/" + subtasks.size();
    }

    public boolean hasOpenSubtasks() { return subtasks.stream().anyMatch(s -> !s.isCompleted()); }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(int m) { this.estimatedMinutes = m; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public TaskType getType() { return type; }
    public void setType(TaskType type) { this.type = type; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public List<TeamMember> getAssignees() { return Collections.unmodifiableList(assignees); }
    public void setAssignees(Collection<TeamMember> a) { this.assignees = new ArrayList<>(a); }
    public List<Subtask> getSubtasks() { return subtasks; }
    public void setSubtasks(List<Subtask> s) { this.subtasks = new ArrayList<>(s); }
    public List<Note> getNotes() { return Collections.unmodifiableList(notes); }
    public List<LocalDate> getDates() { return Collections.unmodifiableList(dates); }
    public void setDates(Collection<LocalDate> d) { this.dates = new ArrayList<>(new TreeSet<>(d)); }
    /** Restores dates in the stored order (the first date of a recurring task is the start of its series). */
    public void restoreDates(List<LocalDate> ordered) { this.dates = new ArrayList<>(ordered); }
    protected List<LocalDate> rawDates() { return dates; }
    public TeamMember getCreatedBy() { return createdBy; }

    @Override public boolean equals(Object o) { return o instanceof Task t && t.id == id; }
    @Override public int hashCode() { return id; }
    @Override public String toString() { return title; }
}
