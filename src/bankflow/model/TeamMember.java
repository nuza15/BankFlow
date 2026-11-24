package bankflow.model;

public class TeamMember extends User {
    private static final long serialVersionUID = 1L;

    public TeamMember(int id, String fullName, String username, String email, String password, boolean active) {
        super(id, fullName, username, email, password, active);
    }

    public TeamMember(int id, String fullName, String username, String email, byte[] salt, String passwordHash, boolean active) {
        super(id, fullName, username, email, salt, passwordHash, active);
    }

    @Override public String getRole() { return "Team Member"; }

    /** A member manages the tasks they created or are assigned to. */
    public boolean canManage(Task task) {
        return task.getCreatedBy().equals(this) || task.getAssignees().contains(this);
    }
}
