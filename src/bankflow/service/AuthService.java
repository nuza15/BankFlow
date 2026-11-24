package bankflow.service;

import bankflow.data.Database;
import bankflow.data.TaskRepository;
import bankflow.model.TeamMember;
import bankflow.model.User;

public class AuthService {
    private final TaskRepository repository;
    private final Database db;

    public AuthService(TaskRepository repository) {
        this.repository = repository;
        this.db = repository.getDatabase();
    }

    /** Returns the user if the credentials are valid and the account is active, otherwise null. */
    public User login(String username, String password) {
        User u = db.findUser(username == null ? "" : username.trim());
        return u != null && u.authenticate(password == null ? "" : password) ? u : null;
    }

    /** Creates an inactive account that an administrator must approve. */
    public TeamMember register(String fullName, String username, String email, String password) {
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Enter your full name.");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Choose a username.");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("The password needs at least 6 characters.");
        if (db.findUser(username.trim()) != null) throw new IllegalArgumentException("That username is already taken.");
        TeamMember m = new TeamMember(db.nextId(), fullName.trim(), username.trim(), email == null ? "" : email.trim(), password, false);
        db.getUsers().add(m);
        repository.flush();
        return m;
    }
}
