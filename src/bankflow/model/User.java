package bankflow.model;

import bankflow.data.PasswordUtil;
import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String fullName;
    private String username;
    private String email;
    private byte[] salt;
    private String passwordHash;
    private boolean active;

    protected User(int id, String fullName, String username, String email, String password, boolean active) {
        this.id = id; this.fullName = fullName; this.username = username; this.email = email;
        this.active = active;
        setPassword(password);
    }

    /** Used when loading from the database: the password is already hashed. */
    protected User(int id, String fullName, String username, String email, byte[] salt, String passwordHash, boolean active) {
        this.id = id; this.fullName = fullName; this.username = username; this.email = email;
        this.active = active; this.salt = salt; this.passwordHash = passwordHash;
    }

    public byte[] getSalt() { return salt; }
    public String getPasswordHash() { return passwordHash; }

    /** Only active users can log in. */
    public boolean authenticate(String password) {
        return active && PasswordUtil.verify(password, salt, passwordHash);
    }

    /** Checks the password regardless of approval state (to explain a pending account at login). */
    public boolean authenticateIgnoringState(String password) {
        return PasswordUtil.verify(password, salt, passwordHash);
    }

    public void setPassword(String password) {
        this.salt = PasswordUtil.newSalt();
        this.passwordHash = PasswordUtil.hash(password, salt);
    }

    public abstract String getRole();
    public boolean isAdmin() { return false; }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getInitials() {
        StringBuilder sb = new StringBuilder();
        for (String part : fullName.trim().split("\\s+")) if (!part.isEmpty() && sb.length() < 2) sb.append(Character.toUpperCase(part.charAt(0)));
        return sb.toString();
    }

    @Override public boolean equals(Object o) { return o instanceof User u && u.id == id; }
    @Override public int hashCode() { return id; }
    @Override public String toString() { return fullName; }
}
