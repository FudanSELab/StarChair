package fudan.se.lab2.security.jwt;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
class JwtRequestFilterTest {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Test
    @Transactional
    void testFilter() throws Exception{
        User user1 = new User("admin",
                "administrator",
                "password",
                "1400208597@qq.com",
                "fudan",
                "shanghai",
                null);
        String token = jwtUtil.generateToken(user1);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization","Bearer " + token);
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletResponse.setStatus(200);
        MockFilterChain mockFilterChain = new MockFilterChain();
        jwtRequestFilter.doFilterInternal(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain);
    }
}