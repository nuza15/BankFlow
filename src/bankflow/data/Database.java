package bankflow.data;

import bankflow.model.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** In-memory data container. The repository persists this object to disk. */
public class Database implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<User> users = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private final List<TaskType> taskTypes = new ArrayList<>();
    private final List<Status> statuses = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final List<HistoryEntry> history = new ArrayList<>();
    private int nextId = 1;
    private LocalDate lastProcessedDay;

    public int nextId() { return nextId++; }

    public List<User> getUsers() { return users; }
    public List<Project> getProjects() { return projects; }
    public List<TaskType> getTaskTypes() { return taskTypes; }
    public List<Status> getStatuses() { return statuses; }
    public List<Task> getTasks() { return tasks; }
    public List<HistoryEntry> getHistory() { return history; }
    public LocalDate getLastProcessedDay() { return lastProcessedDay; }
    public void setLastProcessedDay(LocalDate d) { this.lastProcessedDay = d; }

    private Status defaultAt(int index) {
        int n = 0;
        for (Status s : statuses) if (s.isDefaultStatus()) { if (n++ == index) return s; }
        throw new IllegalStateException("Default statuses missing");
    }
    public Status todo() { return defaultAt(0); }
    public Status inProgress() { return defaultAt(1); }
    public Status completed() { return defaultAt(2); }

    public User findUser(String username) {
        for (User u : users) if (u.getUsername().equalsIgnoreCase(username)) return u;
        return null;
    }

    public Task findTask(int id) {
        for (Task t : tasks) if (t.getId() == id) return t;
        return null;
    }

    /** Adds the default statuses and demo data when the database is first created. */
    public void initializeDefaults() {
        if (statuses.isEmpty()) seed();
    }

    private void seed() {
        LocalDate today = LocalDate.now();
        statuses.add(new Status(nextId(), "To Do", true));
        statuses.add(new Status(nextId(), "In Progress", true));
        statuses.add(new Status(nextId(), "Completed", true));

        String[] typeNames = {"KYC Review", "Loan Processing", "Fraud Investigation",
                "Compliance Reporting", "Customer Request", "Reconciliation"};
        for (String n : typeNames) taskTypes.add(new TaskType(nextId(), n));
        TaskType kyc = taskTypes.get(0), loan = taskTypes.get(1), fraud = taskTypes.get(2),
                comp = taskTypes.get(3), cust = taskTypes.get(4), rec = taskTypes.get(5);

        Project pLoans = new Project(nextId(), "Loan Processing", "Retail and mortgage loan applications");
        Project pComp = new Project(nextId(), "Compliance and Audit", "AML, KYC and regulatory reporting");
        Project pBranch = new Project(nextId(), "Branch Operations", "Day-to-day branch work");
        projects.add(pLoans); projects.add(pComp); projects.add(pBranch);

        Administrator admin = new Administrator(nextId(), "Maria Kapanadze", "admin", "maria@bankflow.local", "admin123", true);
        TeamMember nino = new TeamMember(nextId(), "Nino Beridze", "nino", "nino@bankflow.local", "nino123", true);
        TeamMember luka = new TeamMember(nextId(), "Luka Mgeladze", "luka", "luka@bankflow.local", "luka123", true);
        TeamMember giorgi = new TeamMember(nextId(), "Giorgi Kvaratskhelia", "giorgi", "giorgi@bankflow.local", "giorgi123", true);
        users.add(admin); users.add(nino); users.add(luka); users.add(giorgi);

        add(new OneTimeTask(nextId(), admin), "Verify KYC documents", "Check passport, proof of address and source of funds for the new customer.",
                kyc, Priority.HIGH, 90, pLoans, todo(), List.of(today), List.of(nino), List.of());
        add(new OneTimeTask(nextId(), admin), "Review mortgage application #2041", "Full credit review before committee.",
                loan, Priority.HIGH, 120, pLoans, inProgress(), List.of(today), List.of(nino, luka),
                List.of("Check income documents:1", "Verify property valuation:1", "Credit score check:1", "Prepare decision memo:0"));
        add(new OneTimeTask(nextId(), admin), "Investigate card dispute #771", "Customer reports an unknown card payment abroad.",
                fraud, Priority.MEDIUM, 60, pComp, inProgress(), List.of(today), List.of(luka), List.of("Request merchant data:1", "Contact cardholder:0", "File chargeback:0"));
        add(new OneTimeTask(nextId(), admin), "Update customer address", "",
                cust, Priority.LOW, 20, pBranch, todo(), List.of(today), List.of(giorgi), List.of());
        add(new OneTimeTask(nextId(), admin), "Update loan-rate disclosure", "New rates published by the treasury.",
                comp, Priority.HIGH, 45, pComp, todo(), List.of(today.plusDays(1)), List.of(nino), List.of());
        add(new OneTimeTask(nextId(), admin), "Quarterly access review", "Review system access rights of the branch team.",
                comp, Priority.MEDIUM, 120, pComp, todo(), List.of(today.plusDays(3)), List.of(luka), List.of());
        add(new OneTimeTask(nextId(), admin), "Close dormant accounts batch", "",
                cust, Priority.LOW, 60, pBranch, todo(), List.of(today.plusDays(5)), List.of(giorgi), List.of());
        add(new RecurringTask(nextId(), admin, new RecurrenceRule(Frequency.DAILY, 1)), "Reconcile ATM cash - Branch 4", "Count and reconcile the ATM cassette.",
                rec, Priority.MEDIUM, 45, pBranch, todo(), List.of(today.minusDays(3)), List.of(giorgi), List.of());
        add(new RecurringTask(nextId(), admin, new RecurrenceRule(Frequency.WEEKLY, 1)), "Prepare weekly AML report", "Compile suspicious activity indicators for compliance.",
                comp, Priority.LOW, 60, pComp, todo(), List.of(today), List.of(luka), List.of());
        lastProcessedDay = today.minusDays(1);
    }

    private void add(Task t, String title, String desc, TaskType type, Priority p, int minutes, Project project,
                     Status status, List<LocalDate> dates, List<TeamMember> assignees, List<String> subtasks) {
        t.setTitle(title); t.setDescription(desc); t.setType(type); t.setPriority(p);
        t.setEstimatedMinutes(minutes); t.setProject(project); t.setStatus(status);
        t.setDates(dates); t.setAssignees(assignees);
        for (String s : subtasks) {
            int i = s.lastIndexOf(':');
            Subtask st = new Subtask(nextId(), s.substring(0, i));
            st.setCompleted(s.endsWith(":1"));
            t.addSubtask(st);
        }
        tasks.add(t);
    }
}
