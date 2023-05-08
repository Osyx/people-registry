package model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record Person(String uuid,
                     String ssn,
                     String name,
                     int age,
                     Person spouse,
                     List<Person> children) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;
        return age == person.age
                && ssn.equals(person.ssn)
                && Objects.equals(name, person.name)
                && Objects.equals(spouse, person.spouse)
                && new HashSet<>(children).containsAll(person.children);
    }

    @Override
    public int hashCode() {
        Integer uuidHash = Optional.ofNullable(uuid).map(String::hashCode).orElse(0);
        return uuidHash + ssn.hashCode();
    }
}
