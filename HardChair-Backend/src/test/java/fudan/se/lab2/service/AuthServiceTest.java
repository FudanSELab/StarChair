package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.BadCredentialsException;
import fudan.se.lab2.exception.MailBoxHasBeenRegisteredException;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void register() {
        //测试邮箱已存在异常
        RegisterRequest registerRequestWithMailBoxHasBeenRegisteredException = new RegisterRequest("register", "register1",
                "123456", "123456",
                "123@1.com", "fudan", "shanghai");
        try {
            authService.register(registerRequestWithMailBoxHasBeenRegisteredException);
            fail("Expect a MailBoxHasBeenRegisteredException");
        }catch (MailBoxHasBeenRegisteredException e){
            assertThat(e.getMessage(),is("Mailbox has been registered!"));
        }

        //测试用户名已存在异常
        RegisterRequest registerRequestWithUsernameHasBeenRegisteredException = new RegisterRequest("admin", "register1",
                "123456", "123456",
                "123@registerRequestTest.com", "fudan", "shanghai");
        try {
            authService.register(registerRequestWithUsernameHasBeenRegisteredException);
            fail("Expect a UsernameHasBeenRegisteredException");
        } catch (UsernameHasBeenRegisteredException e) {
            assertThat(e.getMessage(), is("Username 'admin' has been registered"));
        }

        //测试正常注册
        RegisterRequest registerRequest = new RegisterRequest("registerRequestTest", "register1",
                "123456", "123456",
                "123@registerRequestTest.com", "fudan", "shanghai");
        User newUser = authService.register(registerRequest);
        assertThat(newUser, notNullValue());
        assertThat(newUser.getUsername(), is("registerRequestTest"));
    }

    @Test
    @Transactional
    void login() {
        //测试正常登录
        Map<String, Object> map = authService.login("admin", "password");
        assertThat(map, notNullValue());
        assertThat(map.containsKey("token"), equalTo(true));
        assertThat(map.containsKey("userDetails"), equalTo(true));
        assertThat(map.get("token"), notNullValue());
        assertThat(map.get("userDetails"), notNullValue());

        //测试用户名不存在异常
        try {
            authService.login("abc", "password");
            fail("Expected a UsernameNotFoundException");
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage(), is("Username 'abc' not found"));
        }

        //测试密码错误异常
        try {
            authService.login("admin", "abc");
            fail("Expected a BadCredentialsException");
        } catch (BadCredentialsException e) {
            assertThat(e.getMessage(), is("Password incorrect!"));
        }
    }
}