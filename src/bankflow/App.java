package bankflow;

import bankflow.data.FileTaskRepository;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        Path dataFile = FileTaskRepository.defaultFile();
        FileTaskRepository repository = new FileTaskRepository(dataFile);
        AppContext context = new AppContext(repository);

        System.out.println("BankFlow starter application");
        System.out.println("Database loaded successfully.");
        System.out.println("Users: " + context.database().getUsers().size());
        System.out.println("Tasks: " + context.database().getTasks().size());

        context.save();
        System.out.println("Database saved to: " + dataFile);
    }
}
