package bankflow.model;

import java.io.Serializable;
import java.time.LocalDate;

/** One scheduled occurrence of a recurring task, with its own status. */
public class Occurrence implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate date;
    private Status status;

    public Occurrence(LocalDate date, Status status) { this.date = date; this.status = status; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public void complete(Status completed) { this.status = completed; }
}
