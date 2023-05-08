package model.error;

public enum PersonValidationError implements PersonError {

    CHILDREN_MUST_BE_A_LIST("Children should be an empty list instead of null."),
    FAULTY_SSN_FORMAT("Given SSN does not match the standard of YYYYMMDD-XXXX:");

    private final String errorText;

    PersonValidationError(String errorText) {
        this.errorText = errorText;
    }

    @Override
    public String text() {
        return errorText;
    }
}
