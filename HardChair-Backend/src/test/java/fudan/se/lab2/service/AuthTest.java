package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.BadCredentialsException;
import fudan.se.lab2.exception.MailBoxHasBeenRegisteredException;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegisterAndLogin() {
        //1. 测试正常注册
        RegisterRequest registerRequest = new RegisterRequest("registerTest", "testUser1",
                "123456", "123456",
                "123@registerRequestTest.com", "fudan", "shanghai");
        User newUser = authService.register(registerRequest);
        assertThat(newUser, notNullValue());
        assertThat(newUser.getUsername(), is("registerTest"));

        //2. 测试邮箱已存在异常
        RegisterRequest registerRequestWithMailBoxHasBeenRegisteredException = new RegisterRequest("register", "register1",
                "123456", "123456",
                "123@registerRequestTest.com", "fudan", "shanghai");
        try {
            authService.register(registerRequestWithMailBoxHasBeenRegisteredException);
            fail("Expect a MailBoxHasBeenRegisteredException");
        }catch (MailBoxHasBeenRegisteredException e){
            assertThat(e.getMessage(),is("Mailbox has been registered!"));
        }

        //3. 测试用户名已存在异常
        RegisterRequest registerRequestWithUsernameHasBeenRegisteredException = new RegisterRequest("registerTest", "register1",
                "123456", "123456",
                "123@registerRequestTest.com", "fudan", "shanghai");
        try {
            authService.register(registerRequestWithUsernameHasBeenRegisteredException);
            fail("Expect a UsernameHasBeenRegisteredException");
        } catch (UsernameHasBeenRegisteredException e) {
            assertThat(e.getMessage(), is("Username 'registerTest' has been registered"));
        }

        //4. 测试正常登录
        Map<String, Object> map = authService.login("registerTest", "123456");
        assertThat(map, notNullValue());
        assertThat(map.containsKey("token"), equalTo(true));
        assertThat(map.containsKey("userDetails"), equalTo(true));
        assertThat(map.get("token"), notNullValue());
        assertThat(map.get("userDetails"), notNullValue());

        //5. 测试登录用户名不存在异常
        try {
            authService.login("abc", "password");
            fail("Expected a UsernameNotFoundException");
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage(), is("Username 'abc' not found"));
        }

        //6. 测试密码错误异常
        try {
            authService.login("registerTest", "abc");
            fail("Expected a BadCredentialsException");
        } catch (BadCredentialsException e) {
            assertThat(e.getMessage(), is("Password incorrect!"));
        }
    }
}
