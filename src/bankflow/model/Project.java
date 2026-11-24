package bankflow.model;

import java.io.Serializable;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private String description;
    private boolean archived;

    public Project(int id, String name, String description) {
        this.id = id; this.name = name; this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    @Override public boolean equals(Object o) { return o instanceof Project p && p.id == id; }
    @Override public int hashCode() { return id; }
    @Override public String toString() { return name; }
}
