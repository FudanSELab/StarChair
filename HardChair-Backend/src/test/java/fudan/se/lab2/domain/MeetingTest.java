package fudan.se.lab2.domain;

import fudan.se.lab2.repository.MeetingRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MeetingTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    @Transactional
    void testMeeting() {
        Meeting meeting = new Meeting("meeting0", "T2",
                "2020-4-10", "shanghai",
                "2020-4-20", "2020-4-30",
                "applicant", "false","false", null);
        meeting.setFullName("test");
        assertThat(meeting.getFullName(),is("test"));
        meeting.setShortenForm("t");
        assertThat(meeting.getShortenForm(),is("t"));
        meeting.setTime("2020-4-9");
        assertThat(meeting.getTime(),is("2020-4-9"));
        meeting.setLocation("beijing");
        assertThat(meeting.getLocation(),is("beijing"));
        meeting.setDdl("2020-4-10");
        assertThat(meeting.getDdl(),is("2020-4-10"));
        meeting.setPublishingTime("2020-4-20");
        assertThat(meeting.getPublishingTime(),is("2020-4-20"));
        meeting.setApplicant("me");
        assertThat(meeting.getApplicant(),is("me"));
        meeting.setIsVarified("pass");
        assertThat(meeting.getIsVarified(),is("pass"));
        Authority authority = new Authority("admin");
        meeting.setAuthorities(new HashSet<>(Collections.singletonList(authority)));
        assertThat(meeting.getAuthorities(),notNullValue());
    }
}