package fudan.se.lab2.exception;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ControllerAdvisorTest {

    @Autowired
    private ControllerAdvisor controllerAdvisor;

    @Test
    void handleUsernameNotFoundException() {
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("user");
        ResponseEntity<?> responseEntity = controllerAdvisor.handleUsernameNotFoundException(usernameNotFoundException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void handlerUsernameHasBeenRegisteredException() {
        UsernameHasBeenRegisteredException usernameHasBeenRegisteredException = new UsernameHasBeenRegisteredException("user");
        ResponseEntity<?> responseEntity = controllerAdvisor.handlerUsernameHasBeenRegisteredException(usernameHasBeenRegisteredException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleBadCredentialsException() {
        BadCredentialsException badCredentialsException = new BadCredentialsException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleBadCredentialsException(badCredentialsException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    void handleUserNotFoundException() {
        UserNotFoundException userNotFoundException = new UserNotFoundException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleUserNotFoundException(userNotFoundException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void handleMeetingNotFoundException() {
        MeetingNotFoundException meetingNotFoundException = new MeetingNotFoundException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleMeetingNotFoundException(meetingNotFoundException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void handleInvitationNotFoundException() {
        InvitationNotFoundException invitationNotFoundException = new InvitationNotFoundException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleInvitationNotFoundException(invitationNotFoundException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void handleAllocationException() {
        AllocationException allocationException = new AllocationException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleAllocationException(allocationException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleMailBoxHasBeenRegisteredException() {
        MailBoxHasBeenRegisteredException mailBoxHasBeenRegisteredException = new MailBoxHasBeenRegisteredException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleMailBoxHasBeenRegisteredException(mailBoxHasBeenRegisteredException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleArticleHasBeenContributedException() {
        ArticleHasBeenContributedException articleHasBeenContributedException = new ArticleHasBeenContributedException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleArticleHasBeenContributedException(articleHasBeenContributedException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleReviewNotFinishedException() {
        ReviewNotFinishedException reviewNotFinishedException = new ReviewNotFinishedException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleReviewNotFinishedException(reviewNotFinishedException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleNoDiscussionException() {
        NoDiscussionException noDiscussionException = new NoDiscussionException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleNoDiscussionException(noDiscussionException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleScoreNotConfirmedException() {
        ScoreNotConfirmedException scoreNotConfirmedException = new ScoreNotConfirmedException();
        ResponseEntity<?> responseEntity = controllerAdvisor.handleScoreNotConfirmedException(scoreNotConfirmedException);
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}