package bankflow.model;

/** Task priority. Ordered Low < Medium < High; the system can only raise it. */
public enum Priority {
    LOW("Low"), MEDIUM("Medium"), HIGH("High");

    private final String label;

    Priority(String label) { this.label = label; }

    /** Next higher level (High stays High). */
    public Priority next() { return this == LOW ? MEDIUM : HIGH; }

    @Override public String toString() { return label; }
}
