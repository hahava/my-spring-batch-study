package me.kalin.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
public class UserRegistrationWriterListener<UserRegistration> implements ItemWriteListener<UserRegistration> {

    private int count = 1;

    @Override
    public void beforeWrite(List<? extends UserRegistration> list) {
        log.info("[ItemWrite] Item Write : chunk size *" + count++);
    }

    @Override
    public void afterWrite(List<? extends UserRegistration> list) {
        log.info("[ItemWrite] Item Write : { \n" +
                list.stream().map(UserRegistration::toString).collect(joining("\n")) +
                " }"
        );
    }

    @Override
    public void onWriteError(Exception e, List<? extends UserRegistration> list) {
        log.error("[ItemWrite] Item Write Fail...", e);
    }
}
