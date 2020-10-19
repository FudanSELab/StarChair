package fudan.se.lab2.domain;

import fudan.se.lab2.repository.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorityTest {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    @Transactional
    void testAuth() {
        Authority authority = new Authority("admin");
        authority.setAuthority("chair");
        assertThat(authority.getAuthority(), is("chair"));
        Meeting meeting = new Meeting("test2", "T2",
                "2020-4-10", "shanghai",
                "2020-4-20", "2020-4-30",
                "applicant", "false","false", null);
        authority.setMeeting(meeting);
        assertThat(authority.getMeeting(), notNullValue());
        User user1 = new User("user1",
                "administrator",
                "password",
                "1400208597@qq.com",
                "fudan",
                "shanghai",
                null);
        authority.setUser(user1);
        assertThat(authority.getUser(), notNullValue());
    }
}