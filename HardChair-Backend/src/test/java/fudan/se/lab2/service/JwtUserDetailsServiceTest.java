package fudan.se.lab2.service;

import fudan.se.lab2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class JwtUserDetailsServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    @Transactional
    void loadUserByUsername() {
        // 正常加载用户信息
        UserDetails user = jwtUserDetailsService.loadUserByUsername("admin");
        assertThat(user, notNullValue());
        // 检查用户不存在
        try {
            jwtUserDetailsService.loadUserByUsername("abc");
            fail("Expected a UsernameNotFoundException");
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage(), is("User: 'abc' not found."));
        }
    }
}