package bankflow.model;

import java.time.LocalDate;
import java.util.*;

/**
 * A task that repeats by a {@link RecurrenceRule}. Dates: the first date is the start of the series,
 * any further dates are extra one-off occurrences. Every occurrence has its own status.
 */
public class RecurringTask extends Task {
    private static final long serialVersionUID = 1L;

    private RecurrenceRule rule;
    private final List<Occurrence> occurrences = new ArrayList<>();
    private final Set<LocalDate> skipped = new HashSet<>();

    public RecurringTask(int id, TeamMember createdBy, RecurrenceRule rule) {
        super(id, createdBy);
        this.rule = rule;
    }

    public RecurrenceRule getRule() { return rule; }
    public void setRule(RecurrenceRule rule) { this.rule = rule; }
    public List<Occurrence> getOccurrences() { return Collections.unmodifiableList(occurrences); }
    public Set<LocalDate> getSkipped() { return Collections.unmodifiableSet(skipped); }
    public void restoreSkipped(Collection<LocalDate> days) { skipped.clear(); skipped.addAll(days); }
    public void restoreOccurrence(Occurrence o) { occurrences.add(o); }
    public LocalDate getStart() { return rawDates().isEmpty() ? null : rawDates().get(0); }

    @Override public boolean isRecurring() { return true; }

    @Override public boolean occursOn(LocalDate day) {
        if (rawDates().isEmpty() || skipped.contains(day)) return false;
        return rawDates().indexOf(day) > 0 || rule.matches(getStart(), day);
    }

    public Occurrence findOccurrence(LocalDate day) {
        for (Occurrence o : occurrences) if (o.getDate().equals(day)) return o;
        return null;
    }

    /** Gets the occurrence for {@code day}, creating it with {@code initial} status if needed. */
    public Occurrence occurrenceOn(LocalDate day, Status initial) {
        Occurrence o = findOccurrence(day);
        if (o == null) { o = new Occurrence(day, initial); occurrences.add(o); }
        return o;
    }

    /** Materialises the occurrence of {@code day} (if the task occurs then) as incomplete. */
    public void generate(LocalDate day, Status initial) {
        if (occursOn(day)) occurrenceOn(day, initial);
    }

    @Override public Status getStatusOn(LocalDate day, Status fallback) {
        Occurrence o = findOccurrence(day);
        return o == null ? fallback : o.getStatus();
    }

    @Override public void setStatusOn(LocalDate day, Status s) { occurrenceOn(day, s).setStatus(s); }

    /** Moves one occurrence only. */
    @Override public void reschedule(LocalDate from, LocalDate to) {
        if (!occursOn(from) || from.equals(to)) return;
        int idx = rawDates().indexOf(from);
        if (idx > 0) rawDates().set(idx, to);
        else { skipped.add(from); if (!rawDates().contains(to)) rawDates().add(to); }
        Occurrence o = findOccurrence(from);
        if (o != null) o.setDate(to);
        skipped.remove(to);
        List<LocalDate> d = rawDates();
        LocalDate start = d.get(0);
        d.subList(1, d.size()).sort(Comparator.naturalOrder());
        if (d.indexOf(start) != 0) { d.remove(start); d.add(0, start); }
    }

    /** Shifts the whole series (start date, extra dates, recorded occurrences) by some days. */
    public void rescheduleSeries(long days) {
        rawDates().replaceAll(d -> d.plusDays(days));
        for (Occurrence o : occurrences) o.setDate(o.getDate().plusDays(days));
        Set<LocalDate> moved = new HashSet<>();
        for (LocalDate s : skipped) moved.add(s.plusDays(days));
        skipped.clear(); skipped.addAll(moved);
    }

    @Override public void reschedule(LocalDate newDate) {
        if (getStart() != null) rescheduleSeries(java.time.temporal.ChronoUnit.DAYS.between(getStart(), newDate));
    }

    @Override public LocalDate getNextDate(LocalDate from) {
        if (rawDates().isEmpty()) return from;
        if (occursOn(from)) return from;
        return rule.next(getStart(), from);
    }
}
