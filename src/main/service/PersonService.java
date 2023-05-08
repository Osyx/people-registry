package service;

import db.PersonRepository;
import model.Person;
import model.error.PersonException;
import model.error.PersonServiceError;
import validation.PersonValidation;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PersonService {

    private final PersonRepository repository;

    public PersonService() {
        repository = PersonRepository.getRepository();
    }

    public Person getPerson(String ssn) {
        return repository.getPerson(ssn)
                .orElseThrow(() -> new PersonException(PersonServiceError.NOT_FOUND, ssn));
    }

    public void savePerson(Person person) {
        PersonValidation.validate(person);

        repository.savePerson(person);
    }

    public Person getOldestChild(String ssn) {
        List<Person> children = getPerson(ssn).children();
        return getOldestChild(children)
                .orElseThrow(() -> new PersonException(PersonServiceError.NO_CHILD_FOUND, ssn));
    }

    private static Optional<Person> getOldestChild(Collection<Person> children) {
        return children.stream().max(Comparator.comparing(Person::age));
    }
}
