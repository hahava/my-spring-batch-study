package me.kalin.batch.feat.user;

import me.kalin.batch.feat.user.model.UserRegistration;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public final class UserRegistrationGenerator {

    private UserRegistrationGenerator() {
    }

    private static final List<String> countries = List.of("korea", "Japan", "China", "USA", "England", "France", "Finland");
    private static final List<String> domains = List.of("http://wwww.naver.com", "http://wwww.daum.net", "http://wwww.yahoo.com", "http://wwww.line.jp", "http://wwww.nhn.com");
    private static final List<String> companies = List.of("Samsung", "LG", "Naver", "Kakao", "NHN", "Line");

    public static List<UserRegistration> createRandomUsers(int numberOfUsers) {
        List<UserRegistration> users = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            UserRegistration userRegistration = UserRegistration.builder()
                    .id(i)
                    .firstName(randomAlphabetic(4, 10))
                    .lastName(randomAlphabetic(4, 10))
                    .company(getRandomValueInConstant(companies))
                    .address(randomAlphabetic(10))
                    .city(randomAlphabetic(5, 15))
                    .state(randomAlphabetic(5, 16))
                    .zip(randomNumeric(6))
                    .country(getRandomValueInConstant(countries))
                    .url(getRandomValueInConstant(domains))
                    .phoneNumber("010-" + randomNumeric(4) + "-" + randomNumeric(4))
                    .fax(randomNumeric(10))
                    .build();

            users.add(userRegistration);
        }

        return users;
    }

    private static String getRandomValueInConstant(List<String> values) {
        return values.get(nextInt(0, values.size() - 1));
    }
}
