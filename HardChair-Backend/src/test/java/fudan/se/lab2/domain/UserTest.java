package fudan.se.lab2.domain;

import fudan.se.lab2.repository.UserRepository;
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
class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testUser() {
        User user1 = new User("user1",
                "administrator",
                "password",
                "1400208597@qq.com",
                "fudan",
                "shanghai",
                null);
        userRepository.save(user1);
        User myUser = userRepository.findByUsername("user1");
        assertThat(myUser.getUsername(),is(user1.getUsername()));
        assertThat(myUser.getAuthorities(),is(user1.getAuthorities()));
        assertThat(myUser.getPassword(),is(user1.getPassword()));
        assertThat(myUser.getArea(),is(user1.getArea()));
        assertThat(myUser.getCompanie(),is(user1.getCompanie()));
        assertThat(myUser.getE_mail(),is(user1.getE_mail()));
        assertThat(myUser.getFullName(),is(user1.getFullName()));
        myUser.setUsername("user2");
        assertThat(myUser.getUsername(),is("user2"));
        Authority authority = new Authority("admin");
        myUser.setAuthorities(new HashSet<>(Collections.singletonList(authority)));
        assertThat(myUser.getAuthorities(),notNullValue());
        myUser.setPassword("123456");
        assertThat(myUser.getPassword(),is("123456"));
        myUser.setArea("beijing");
        assertThat(myUser.getArea(),is("beijing"));
        myUser.setCompanie("ss");
        assertThat(myUser.getCompanie(),is("ss"));
        myUser.setE_mail("123@qq.com");
        assertThat(myUser.getE_mail(),is("123@qq.com"));
        myUser.setFullName("user2");
        assertThat(myUser.getFullName(),is("user2"));
        assertThat(myUser.isAccountNonExpired(),is(true));
        assertThat(myUser.isAccountNonLocked(),is(true));
        assertThat(myUser.isCredentialsNonExpired(),is(true));
        assertThat(myUser.isEnabled(),is(true));
    }
}