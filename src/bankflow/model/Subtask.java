package bankflow.model;

import java.io.Serializable;

public class Subtask implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private boolean completed;

    public Subtask(int id, String title) { this.id = id; this.title = title; }

    public int getId() { return id; }
    public void assignId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void markCompleted() { this.completed = true; }

    @Override public String toString() { return title; }
}
