package bankflow.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String text;
    private final LocalDateTime createdAt;
    private final TeamMember author;

    public Note(int id, String text, TeamMember author) {
        this(id, text, author, LocalDateTime.now().withNano(0));
    }

    public Note(int id, String text, TeamMember author, LocalDateTime createdAt) {
        this.id = id; this.text = text; this.author = author; this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public TeamMember getAuthor() { return author; }
}
