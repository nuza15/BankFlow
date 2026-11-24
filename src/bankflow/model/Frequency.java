package bankflow.model;

public enum Frequency {
    DAILY("Daily", "day"), WEEKLY("Weekly", "week"), MONTHLY("Monthly", "month");

    private final String label;
    private final String unit;

    Frequency(String label, String unit) { this.label = label; this.unit = unit; }

    public String getUnit() { return unit; }

    @Override public String toString() { return label; }
}
