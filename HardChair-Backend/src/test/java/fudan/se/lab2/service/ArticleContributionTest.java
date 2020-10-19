package fudan.se.lab2.service;

import fudan.se.lab2.repository.ArticleRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.service.AllMeetingsService;
import fudan.se.lab2.service.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleContributionTest {
    @Autowired
    private AllMeetingsService allMeetingsService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testArticleContribution() throws IOException {
        //1. 测试投稿人是否是chair
        assertThat(allMeetingsService.checkIfChair("registerTest", meetingRepository.findByFullName("conferenceTest1").getId().toString()), is("warning"));
        assertThat(allMeetingsService.checkIfChair("pc1", meetingRepository.findByFullName("conferenceTest1").getId().toString()), is("success"));

        //2. 测试投稿
        File file = new File("C:\\Users\\nqxxz\\Desktop\\MidExam19.pdf");
        FileInputStream in_file = new FileInputStream(file);
        MultipartFile multi1 = new MockMultipartFile("MidExam19.pdf", "MidExam19.pdf", "application/pdf", in_file);
        String[] topic = {"science"};
        allMeetingsService.storeArticleContribution(multi1, "MidExam19",
                "[{\"name\":\"w\",\"email\":\"qdw@1.co\",\"company\":\"sda\",\"area\":\"asd\"}]",
                "summary", topic, "pc1",
                meetingRepository.findByFullName("conferenceTest1").getId().toString(),
                "C:\\Users\\nqxxz\\Downloads\\");

        //3. 测试author查看投稿详情
        assertThat(authorService.getContribution("pc1", meetingRepository.findByFullName("conferenceTest1").getId().toString()), notNullValue());

        //4. 测试修改稿件
        File newFile = new File("C:\\Users\\nqxxz\\Desktop\\MidExam19.pdf");
        FileInputStream in_newFile = new FileInputStream(newFile);
        MultipartFile multi2 = new MockMultipartFile("MidExam19.pdf", "MidExam19.pdf", "application/pdf", in_newFile);
        authorService.modifyArticleContribution(multi2, "MidExam19",
                "[{\"name\":\"w\",\"email\":\"qdw@1.co\",\"company\":\"sda\",\"area\":\"asd\"}]",
                "summary", topic, "pc1", meetingRepository.findByFullName("conferenceTest1").getId().toString(),
                articleRepository.getArticle("MidExam19", "summary", "pc1", meetingRepository.findByFullName("conferenceTest1").getId().toString()).getId().toString(),
                "C:\\Users\\nqxxz\\Downloads\\test");
    }
}
