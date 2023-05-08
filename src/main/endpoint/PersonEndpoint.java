package endpoint;

import model.Person;
import service.PersonService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class PersonEndpoint {

    private final PersonService personService;

    public PersonEndpoint() {
        personService = new PersonService();
    }

    public PersonView getPerson(String ssn) {
        Person person = personService.getPerson(ssn);

        return makePersonForView(person);
    }

    public SsnAndNamePair getNameOfOldestChild(String ssn) {
        Person oldestChild = personService.getOldestChild(ssn);

        return new SsnAndNamePair(ssn, oldestChild.name());
    }

    private static PersonView makePersonForView(Person person) {
        String spouseName = getSpouseName(person);
        List<ChildView> childViews = makeChildViews(person.children());

        return new PersonView(person.ssn(),
                              person.name(),
                              spouseName,
                              childViews);
    }

    private static String getSpouseName(Person person) {
        return Optional.ofNullable(person.spouse()).map(Person::name).orElse(null);
    }

    private static List<ChildView> makeChildViews(Collection<Person> children) {
        return children.stream()
                .map(c -> new ChildView(c.name(), c.age()))
                .toList();
    }

    public record SsnAndNamePair(String ssn, String name) {

    }

    public record PersonView(String ssn,
                             String name,
                             String spouseName,
                             List<ChildView> children) {

    }

    public record ChildView(String name,
                            int age) {

    }
}
