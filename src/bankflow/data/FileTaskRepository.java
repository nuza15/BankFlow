package bankflow.data;

import java.io.*;
import java.nio.file.*;

/** Loads and saves the Database object using Java serialization. */
public class FileTaskRepository implements TaskRepository {
    private final Path file;
    private Database database;

    public FileTaskRepository(Path file) {
        this.file = file;
        this.database = load();
    }

    public static Path defaultFile() {
        return Paths.get(System.getProperty("user.home"), ".bankflow", "bankflow.dat");
    }

    @Override public Database getDatabase() { return database; }

    private Database load() {
        if (Files.exists(file)) {
            try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file))) {
                Object value = in.readObject();
                if (value instanceof Database db) return db;
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Could not load saved data: " + ex.getMessage());
            }
        }
        Database fresh = new Database();
        fresh.initializeDefaults();
        return fresh;
    }

    @Override public synchronized void flush() {
        try {
            Files.createDirectories(file.getParent());
            Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(temporary))) {
                out.writeObject(database);
            }
            Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not save database", ex);
        }
    }
}
