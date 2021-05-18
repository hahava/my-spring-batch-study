package me.kalin.batch.feat.user.job;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.springframework.batch.test.context.SpringBatchTest;

@SpringBatchTest
public class UserJobTest {

    @Test
    public void createRandomUser() {
//        List<UserRegistration> users = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
////            UserRegistration userRegistration = new UserRegistration();
//
//            userRegistration.setId(i);
//        }
    }

    @Test
//    @Test(expected = NullPointerException.class)
    public void test() {
        Computer computer = null;
//        computer.soundCard = new SoundCard();
//        computer.soundCard.usb = new Usb();
//        computer.soundCard.usb.company = "hello";

//        String company = Optional.of(computer)
//                .map(Computer::getSoundCard)
//                .map(SoundCard::getUsb)
//                .map(Usb::getCompany)
//                .orElseThrow(NullPointerException::new);


//        Assertions.assertEquals(company, "hello");
    }

    @Setter
    @Getter
    class Computer {
        SoundCard soundCard;
    }

    @Setter
    @Getter
    class SoundCard {
        Usb usb;
    }

    @Getter
    @Setter
    class Usb {
        String company;
    }

}
