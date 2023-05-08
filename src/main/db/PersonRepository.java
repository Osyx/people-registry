package db;

import model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class PersonRepository {

    private static final PersonRepository REPO = new PersonRepository();

    private final Map<String, Person> persons;

    public static PersonRepository getRepository() {
        return REPO;
    }

    private PersonRepository() {
        persons = new HashMap<>();
    }

    public Optional<Person> getPerson(String ssn) {
        return Optional.ofNullable(persons.get(ssn));
    }

    public void savePerson(Person person) {
        person.children().forEach(child -> persons.put(child.ssn(), child));
        Optional.ofNullable(person.spouse()).ifPresent(spouse -> persons.put(spouse.ssn(), spouse));
        persons.put(person.ssn(), person);
    }

    void clear() {
        persons.clear();
    }
}
