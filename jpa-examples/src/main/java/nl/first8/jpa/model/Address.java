package nl.first8.jpa.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zip;
    private String city;

    protected Address() {}

    public Address(String zip) {
        this.zip = zip;
    }
    public Address(String zip, String city) {
        this.zip = zip;
        this.city = city;
    }

}
