package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.service.AuthService;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthController authController;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @Transactional
    void register() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("testRegister", "testRegister1",
                "password", "password",
                "123@testRegister.com", "fudan", "shanghai");
        Map<String, String> register = new HashMap<>();
        register.put("username", registerRequest.getUsername());
        register.put("fullName", registerRequest.getFullname());
        register.put("password", registerRequest.getPassword());
        register.put("comfirmPassword", registerRequest.getCheckPassword());
        register.put("e_mail", registerRequest.getE_mail());
        register.put("companie", registerRequest.getCompanie());
        register.put("area", registerRequest.getArea());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(register)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void login() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin", "password");
        Map<String, String> login = new HashMap<>();
        login.put("username", loginRequest.getUsername());
        login.put("password", loginRequest.getPassword());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(login)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void conferenceApplication() throws Exception {
        String[] topic = {"science"};
        MeetingRequest meetingRequest = new MeetingRequest("controllerTest", "CT",
                "2020-4-10", "shanghai", "2020-4-20",
                "2020-4-30", "chair1", topic);
        Map<String, Object> meeting = new HashMap<>();
        meeting.put("fullName", meetingRequest.getFullName());
        meeting.put("shortenForm", meetingRequest.getShortenForm());
        meeting.put("time", meetingRequest.getTime());
        meeting.put("location", meetingRequest.getLocation());
        meeting.put("ddl", meetingRequest.getDdl());
        meeting.put("publishingTime", meetingRequest.getPublishingTime());
        meeting.put("applicant", meetingRequest.getApplicant());
        meeting.put("topic", meetingRequest.getTopic());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applyConference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(meeting)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void getUncheckedMeetings() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getUncheckedConference"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void applicationStatus() throws Exception {
        Meeting meeting = meetingRepository.findByFullName("test0");
        ChangeApplicationStatusRequest changeApplicationStatusRequest = new ChangeApplicationStatusRequest(meeting.getId().toString(),
                "pass");
        Map<String, String> applicationStatus = new HashMap<>();
        applicationStatus.put("applicationId", changeApplicationStatusRequest.getApplicationId());
        applicationStatus.put("applyStatus", changeApplicationStatusRequest.getApplyStatus());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/changeApplicationStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(applicationStatus)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult checkResult = mockMvc.perform(MockMvcRequestBuilders.get("/checkIfChair?username=test0&meetingId=" + meetingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(checkResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void getAllMeetings() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getAllPassedMeetings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void getMyMeetingApplication() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/myApplication?username=user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void getMyMeeting() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/userConf?username=test0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void getAuthorityList() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getAuthorityList?id=" + meetingId + "&username=test0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void getInvitationIReceived() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/invitationIReceived?id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void getInvitationISent() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/invitationISent?username=test1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void sendInvitation() throws Exception {
        String[] invitee = {"invitee"};
        SendInvitationRequest invitationRequest = new SendInvitationRequest("2", "test0", invitee);
        Map<String, Object> invitation = new HashMap<>();
        invitation.put("meetingID", invitationRequest.getMeetingID());
        invitation.put("inviter", invitationRequest.getInviter());
        invitation.put("invitee", invitationRequest.getInvitee());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/sendInvitation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(invitation)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void getSearchForUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/searchUserInfo?fullName=test1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void changeSubmitStatus() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/changeSubmitStatus?meetingID=" + meetingId + "&submitStatus=pass"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    @Transactional
    void getAuthorContribution() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/author?username=test0&meetingId=" + meetingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void getPcList() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getPcList?meetingID=" + meetingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void startReview() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/startReview?meetingId=" + meetingId + "&strategy=withBurden"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void confirmFinish() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/confirmFinish?meetingId=" + meetingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void viewAllArticles() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/viewAllArticles?meetingId=" + meetingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @WithMockUser(roles = "CHAIR")
    @Transactional
    void publishScores() throws Exception {
        String meetingId = meetingRepository.findByFullName("test0").getId().toString();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/firstPublishScores?meetingId=" + meetingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @Transactional
    void welcome() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());
    }
}

