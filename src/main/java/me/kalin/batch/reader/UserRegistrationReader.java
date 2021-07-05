package me.kalin.batch.reader;

import lombok.extern.slf4j.Slf4j;
import me.kalin.batch.common.helper.UserRegistrationGenerator;
import me.kalin.batch.model.UserRegistration;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.List;

@Slf4j
public class UserRegistrationReader implements ItemReader<UserRegistration>, ItemStreamReader<UserRegistration> {

    public UserRegistrationReader(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    private final int numberOfUsers;

    private List<UserRegistration> userRegistrations;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        log.info("[Reader] Creating Sample users...");
        userRegistrations = UserRegistrationGenerator.createRandomUsers(numberOfUsers);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        //noop()
    }

    @Override
    public void close() throws ItemStreamException {
        log.info("[Reader] Sample User is Created...");
    }

    @Override
    public UserRegistration read() {
        if (!userRegistrations.isEmpty()) {
            return userRegistrations.remove(0);
        }

        return null;
    }
}
