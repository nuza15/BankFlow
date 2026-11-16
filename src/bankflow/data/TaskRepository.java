package bankflow.data;

import bankflow.data.Database;

/** Basic storage contract for the application. */
public interface TaskRepository {
    Database getDatabase();
    void flush();
}
