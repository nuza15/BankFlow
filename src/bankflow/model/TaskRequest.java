package bankflow.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Data collected by the task form (create and edit). */
public class TaskRequest {
    private String title = "";
    private String description = "";
    private TaskType type;
    private Priority priority = Priority.MEDIUM;
    private int estimatedMinutes = 30;
    private List<LocalDate> dates = new ArrayList<>();
    private Project project;
    private List<TeamMember> assignees = new ArrayList<>();
    private RecurrenceRule rule;
    private Status status;
    private List<Subtask> subtasks = new ArrayList<>();
    private List<String> noteTexts = new ArrayList<>();

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskType getType() { return type; }
    public void setType(TaskType type) { this.type = type; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public int getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(int m) { this.estimatedMinutes = m; }
    public List<LocalDate> getDates() { return dates; }
    public void setDates(List<LocalDate> dates) { this.dates = dates; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public List<TeamMember> getAssignees() { return assignees; }
    public void setAssignees(List<TeamMember> assignees) { this.assignees = assignees; }
    public RecurrenceRule getRule() { return rule; }
    public void setRule(RecurrenceRule rule) { this.rule = rule; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public List<Subtask> getSubtasks() { return subtasks; }
    public void setSubtasks(List<Subtask> subtasks) { this.subtasks = subtasks; }
    public List<String> getNoteTexts() { return noteTexts; }
}
