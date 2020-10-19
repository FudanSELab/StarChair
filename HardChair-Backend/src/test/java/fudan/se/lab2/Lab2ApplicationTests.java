package fudan.se.lab2;

import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
class Lab2ApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    void contextLoads() {
        if (userRepository.findByUsername("test0") == null) {
            User user = new User("test0", "test0",
                    "password", "123@test0.com",
                    "fudan", "shanghai", new HashSet<>());
            userRepository.save(user);
        }
        if (meetingRepository.findByFullName("test0") == null) {
            Meeting meeting = new Meeting("test0", "test0",
                    "1", "shanghai", "1", "1",
                    "test0", "false", "pass", new HashSet<>());
            meetingRepository.save(meeting);
        }
    }
}
