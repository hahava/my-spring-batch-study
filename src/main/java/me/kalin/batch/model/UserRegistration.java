package me.kalin.batch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class UserRegistration implements Serializable {
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
        return +id + ", "
                + firstName + ", "
                + lastName + ", "
                + company + ", "
                + address + ", "
                + city + ", "
                + state + ", "
                + zip + ", "
                + country + ", "
                + url + ", "
                + phoneNumber + ", "
                + fax;
    }
}
