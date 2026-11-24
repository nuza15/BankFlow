package bankflow.model;

import java.time.LocalDate;
import java.util.Collections;

public class OneTimeTask extends Task {
    private static final long serialVersionUID = 1L;

    public OneTimeTask(int id, TeamMember createdBy) { super(id, createdBy); }

    @Override public boolean isRecurring() { return false; }
    @Override public boolean occursOn(LocalDate day) { return rawDates().contains(day); }
    @Override public Status getStatusOn(LocalDate day, Status fallback) { return getStatus(); }
    @Override public void setStatusOn(LocalDate day, Status s) { changeStatus(s); }

    @Override public void reschedule(LocalDate from, LocalDate to) {
        if (!rawDates().remove(from)) return;
        if (!rawDates().contains(to)) rawDates().add(to);
        Collections.sort(rawDates());
    }

    @Override public LocalDate getNextDate(LocalDate from) {
        for (LocalDate d : rawDates()) if (!d.isBefore(from)) return d;
        return rawDates().isEmpty() ? from : rawDates().get(rawDates().size() - 1);
    }
}
