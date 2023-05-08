package model.error;

import java.util.Objects;
import java.util.Optional;

public class PersonException extends RuntimeException {

    private final PersonError error;
    private final Object object;

    public PersonException(PersonError error) {
        this.error = Objects.requireNonNull(error);
        this.object = null;
    }

    public PersonException(PersonError error, Object object) {
        this.error = Objects.requireNonNull(error);
        this.object = object;
    }

    public PersonError getError() {
        return error;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        String objectString = Optional.ofNullable(object)
                .map(Object::toString)
                .orElse("");
        return "PersonException: %s %s".formatted(error.text(), objectString);
    }
}
