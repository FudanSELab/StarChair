package fudan.se.lab2.security.jwt;

import fudan.se.lab2.domain.User;
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
class JwtTokenUtilTest {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Test
    @Transactional
    void generateToken_getUsernameFromToken_validateToken() {
        User user1 = new User("user1",
                "administrator",
                "password",
                "1400208597@qq.com",
                "fudan",
                "shanghai",
                null);
        String token = jwtTokenUtil.generateToken(user1);
        assertThat(token,notNullValue());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        assertThat(username,is("user1"));
        assertThat(jwtTokenUtil.validateToken(token,user1),is(true));
    }
}