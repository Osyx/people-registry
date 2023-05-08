package validation;

import model.Person;
import model.error.PersonException;
import model.error.PersonValidationError;

import java.util.regex.Pattern;

public final class PersonValidation {

    private static final Pattern SSN_PATTERN = Pattern.compile("^\\d{8}-\\d{4}$");

    private PersonValidation() {
    }

    public static void validate(Person person) {
        String ssn = person.ssn();
        if (!SSN_PATTERN.matcher(ssn).matches()) {
            throw new PersonException(PersonValidationError.FAULTY_SSN_FORMAT, ssn);
        }
        if (person.children() == null) {
            throw new PersonException(PersonValidationError.CHILDREN_MUST_BE_A_LIST);
        }
    }
}
