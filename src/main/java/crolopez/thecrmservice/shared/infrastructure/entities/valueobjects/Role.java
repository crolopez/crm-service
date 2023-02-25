package crolopez.thecrmservice.shared.infrastructure.entities.valueobjects;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
