package bankflow;

import bankflow.data.TaskRepository;
import bankflow.data.Database;

/** Shares the repository and database with the rest of the application. */
public class AppContext {
    private final TaskRepository repository;

    public AppContext(TaskRepository repository) {
        this.repository = repository;
    }

    public Database database() { return repository.getDatabase(); }
    public void save() { repository.flush(); }
}
