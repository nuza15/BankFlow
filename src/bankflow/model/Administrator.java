package bankflow.model;

/** Specialisation of {@link TeamMember}: manages everything across the team. */
public class Administrator extends TeamMember {
    private static final long serialVersionUID = 1L;

    public Administrator(int id, String fullName, String username, String email, String password, boolean active) {
        super(id, fullName, username, email, password, active);
    }

    public Administrator(int id, String fullName, String username, String email, byte[] salt, String passwordHash, boolean active) {
        super(id, fullName, username, email, salt, passwordHash, active);
    }

    @Override public String getRole() { return "Administrator"; }
    @Override public boolean isAdmin() { return true; }
    @Override public boolean canManage(Task task) { return true; }
}
