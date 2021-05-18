package me.kalin.batch.feat.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserRegistration implements Serializable {
    @Builder
    public UserRegistration(
            long id,
            String firstName,
            String lastName,
            String company,
            String address,
            String city,
            String state,
            String zip,
            String country,
            String url,
            String phoneNumber,
            String fax
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.url = url;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
    }

    private long id;
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String url;
    private String phoneNumber;
    private String fax;

    public static List<String> headerName() {
        return List.of(
                "id",
                "firstName",
                "lastName",
                "company",
                "address",
                "city",
                "state",
                "zip",
                "country",
                "url",
                "phoneNumber",
                "fax"
        );
    }

    @Override
    public String toString() {
        return +id + ","
                + firstName + ","
                + lastName + ","
                + company + ","
                + address + ","
                + city + ","
                + state + ","
                + zip + ","
                + country + ","
                + url + ","
                + phoneNumber + ","
                + fax;
    }
}
