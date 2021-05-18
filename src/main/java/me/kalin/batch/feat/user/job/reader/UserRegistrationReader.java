package me.kalin.batch.feat.user.job.reader;

import me.kalin.batch.feat.user.model.UserRegistration;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class UserRegistrationReader implements ItemReader<List<UserRegistration>>, ItemStreamReader<List<UserRegistration>> {

    public UserRegistrationReader(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    private List<UserRegistration> userRegistrations;
    private int numberOfUsers;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        userRegistrations = createRandomUsers();
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
    }

    @Override
    public void close() throws ItemStreamException {
        System.out.println("close");
    }

    @Override
    public List<UserRegistration> read() {
        if (!userRegistrations.isEmpty()) {
            return List.of(userRegistrations.remove(0));
        }

        return null;
    }

    private List<UserRegistration> createRandomUsers() {
        List<UserRegistration> users = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            UserRegistration userRegistration = UserRegistration.builder()
                    .id(i)
                    .firstName(randomAlphabetic(10))
                    .lastName(randomAlphabetic(10))
                    .company(randomAlphabetic(10))
                    .address(randomAlphabetic(10))
                    .city(randomAlphabetic(10))
                    .state(randomAlphabetic(10))
                    .zip(randomAlphanumeric(10))
                    .country(randomAlphabetic(10))
                    .url(randomAlphabetic(10))
                    .phoneNumber(randomAlphanumeric(10))
                    .fax(randomAlphanumeric(10))
                    .build();

            users.add(userRegistration);
        }

        return users;
    }
}
