package bankflow;

import bankflow.data.FileTaskRepository;
import bankflow.model.User;
import bankflow.service.AuthService;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        Path dataFile = FileTaskRepository.defaultFile();
        FileTaskRepository repository = new FileTaskRepository(dataFile);
        AppContext context = new AppContext(repository);

        System.out.println("BankFlow - week 2 (model, authentication, demo data)");
        System.out.println("Users: " + context.database().getUsers().size());
        System.out.println("Tasks: " + context.database().getTasks().size());

        AuthService auth = new AuthService(repository);
        User admin = auth.login("admin", "admin123");
        System.out.println("Login admin/admin123: " + (admin == null ? "FAILED" : "ok (" + admin.getRole() + ")"));
        System.out.println("Login admin/wrong: " + (auth.login("admin", "wrong") == null ? "rejected (correct)" : "ACCEPTED (bug)"));

        context.save();
        System.out.println("Database saved to: " + dataFile);
    }
}
