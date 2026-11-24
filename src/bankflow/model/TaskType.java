package bankflow.model;

import java.io.Serializable;

public class TaskType implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;

    public TaskType(int id, String name) { this.id = id; this.name = name; }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override public boolean equals(Object o) { return o instanceof TaskType t && t.id == id; }
    @Override public int hashCode() { return id; }
    @Override public String toString() { return name; }
}
