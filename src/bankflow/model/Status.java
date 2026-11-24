package bankflow.model;

import java.io.Serializable;

/** A task status. The three default statuses (To Do, In Progress, Completed) cannot be removed. */
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private final boolean defaultStatus;

    public Status(int id, String name, boolean defaultStatus) {
        this.id = id; this.name = name; this.defaultStatus = defaultStatus;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isDefaultStatus() { return defaultStatus; }

    @Override public boolean equals(Object o) { return o instanceof Status s && s.id == id; }
    @Override public int hashCode() { return id; }
    @Override public String toString() { return name; }
}
