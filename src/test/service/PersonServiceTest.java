package service;

import db.DBTestUtil;
import model.Person;
import model.error.PersonException;
import model.error.PersonServiceError;
import model.error.PersonValidationError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonServiceTest {

    private PersonService service;

    @BeforeEach
    void setUp() {
        service = new PersonService();
    }

    @AfterEach
    void tearDown() {
        DBTestUtil.clearDB();
    }

    @Test
    void savePerson() {
        var person = new Person(null,
                                "20001231-1234",
                                "Pelle Svanslös",
                                48,
                                null,
                                Collections.emptyList());

        Executable savePerson = () -> service.savePerson(person);

        assertDoesNotThrow(savePerson);
    }

    @Test
    void savePersonWithFaultySSN() {
        var person = new Person(null,
                                "001231-1234",
                                "Pelle Svanslös",
                                48,
                                null,
                                Collections.emptyList());

        Executable savePerson = () -> service.savePerson(person);

        var exception = assertThrows(PersonException.class, savePerson);
        assertEquals(PersonValidationError.FAULTY_SSN_FORMAT, exception.getError());
    }

    @Test
    void getPersonWithEmptySSN() {
        var ssn = "";

        Executable getPerson = () -> service.getPerson(ssn);

        var exception = assertThrows(PersonException.class, getPerson);
        assertEquals(PersonServiceError.NOT_FOUND, exception.getError());
    }

    @Test
    void getPersonWithNullSSN() {
        String ssn = null;

        Executable getPerson = () -> service.getPerson(ssn);

        var exception = assertThrows(PersonException.class, getPerson);
        assertEquals(PersonServiceError.NOT_FOUND, exception.getError());
    }

    @Test
    void getPerson() {
        var ssn = "20001231-1234";
        var savedPerson = savePerson(ssn, null, Collections.emptyList());

        var person = service.getPerson(ssn);

        assertEquals(savedPerson, person);
    }

    @Test
    void getPersonWithSpouse() {
        var ssn = "20001231-1234";
        var savedSpouse = new Person(null,
                                     "20001231-9999",
                                     "Kim Possible",
                                     55,
                                     null,
                                     Collections.emptyList());
        var savedPerson = savePerson(ssn, savedSpouse, Collections.emptyList());

        var person = service.getPerson(ssn);

        assertEquals(savedPerson, person);
        assertEquals(savedSpouse, person.spouse());
    }

    @Test
    void getPersonWithChild() {
        var ssn = "20001231-1234";
        var savedChild = new Person(null,
                                    "20001231-9999",
                                    "Nathan Possible",
                                    20,
                                    null,
                                    Collections.emptyList());
        var savedPerson = savePerson(ssn, null, List.of(savedChild));

        var person = service.getPerson(ssn);

        var child = person.children().stream()
                .reduce((p1, p2) -> {
                    throw new IllegalStateException();
                })
                .orElseThrow();
        assertEquals(savedPerson, person);
        assertEquals(savedChild, child);
    }

    @Test
    void getChild() {
        var childSsn = "20001231-9999";
        var savedChild = new Person(null,
                                    childSsn,
                                    "Nathan Possible",
                                    20,
                                    null,
                                    Collections.emptyList());
        savePerson("20001231-1234", null, List.of(savedChild));

        var person = service.getPerson(childSsn);

        assertEquals(savedChild, person);
    }

    @Test
    void getSpouse() {
        var spouseSsn = "20001231-9999";
        var savedSpouse = new Person(null,
                                     spouseSsn,
                                     "Nathan Possible",
                                     20,
                                     null,
                                     Collections.emptyList());
        savePerson("20001231-1234", savedSpouse, Collections.emptyList());

        var person = service.getPerson(spouseSsn);

        assertEquals(savedSpouse, person);
    }

    @Test
    void getOldestChild() {
        var ssn = "20001231-1234";
        var firstSavedChild = new Person(null,
                                         "20001231-9999",
                                         "Nathan Possible",
                                         20,
                                         null,
                                         Collections.emptyList());
        var secondSavedChild = new Person(null,
                                          "19961231-1111",
                                          "Jenna Possible",
                                          24,
                                          null,
                                          Collections.emptyList());
        var thirdSavedChild = new Person(null,
                                         "19991231-1111",
                                         "Jenna Possible",
                                         21,
                                         null,
                                         Collections.emptyList());
        savePerson(ssn, null, List.of(firstSavedChild, secondSavedChild, thirdSavedChild));

        var oldestChild = service.getOldestChild(ssn);

        assertEquals(secondSavedChild, oldestChild);
    }

    private Person savePerson(String ssn, Person spouse, List<Person> children) {
        var person = new Person(null,
                                ssn,
                                "Pelle Svanslös",
                                48,
                                spouse,
                                children);
        service.savePerson(person);
        return person;
    }
}
