package bankflow.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/** Describes how often a recurring task occurs (every {interval} days/weeks/months). */
public class RecurrenceRule implements Serializable {
    private static final long serialVersionUID = 1L;

    private Frequency frequency;
    private int interval;

    public RecurrenceRule(Frequency frequency, int interval) {
        this.frequency = frequency;
        this.interval = Math.max(1, interval);
    }

    public Frequency getFrequency() { return frequency; }
    public int getInterval() { return interval; }

    /** True if {@code day} is an occurrence of a series that starts on {@code start}. */
    public boolean matches(LocalDate start, LocalDate day) {
        if (day.isBefore(start)) return false;
        switch (frequency) {
            case DAILY:
                return ChronoUnit.DAYS.between(start, day) % interval == 0;
            case WEEKLY:
                return ChronoUnit.DAYS.between(start, day) % (7L * interval) == 0;
            default: {
                long months = ChronoUnit.MONTHS.between(start.withDayOfMonth(1), day.withDayOfMonth(1));
                if (months % interval != 0) return false;
                int target = Math.min(start.getDayOfMonth(), day.lengthOfMonth());
                return day.getDayOfMonth() == target;
            }
        }
    }

    /** First occurrence strictly after {@code from}, for a series starting on {@code start}. */
    public LocalDate next(LocalDate start, LocalDate from) {
        LocalDate d = from.isBefore(start) ? start.minusDays(1) : from;
        for (int i = 0; i < 800; i++) {
            d = d.plusDays(1);
            if (matches(start, d)) return d;
        }
        return start;
    }

    @Override public String toString() {
        if (interval == 1) return frequency.toString();
        return "Every " + interval + " " + frequency.getUnit() + "s";
    }
}
