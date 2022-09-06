package nl.first8.jpa.model;

public class NamesOnlyDto {

    private final String name, surname;

    public NamesOnlyDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
