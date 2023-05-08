package model.error;

public enum PersonServiceError implements PersonError {
    NOT_FOUND("Could not find person with SSN:"),
    NO_CHILD_FOUND("Could not find a child for the person with SSN:");

    private final String errorText;

    PersonServiceError(String errorText) {
        this.errorText = errorText;
    }

    @Override
    public String text() {
        return errorText;
    }
}
