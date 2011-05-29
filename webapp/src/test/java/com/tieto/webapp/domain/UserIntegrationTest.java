package com.tieto.webapp.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tieto.webapp.domain.User;
import com.tieto.webapp.service.UserRepository;


@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@TransactionConfiguration
public class UserIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private TestUserFactory userFactory;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
    public void should_count_users() {
		userFactory.insertTimes(10);
		Long userCount = userRepository.countUsers();
		assertThat(userCount, is(10L));
    }

	@Test
	@Transactional
    public void should_find_user() {
		
		User user = new User("lszylo","password");
		userRepository.save(user);
		Long id = user.getId();
		
        User foundUser = userRepository.findUser(id);

        assertThat(foundUser.getId(), is(id));
	}
	
	@Test
	public void should_not_allow_user_with_duplicated_login() throws Exception {
		
		// Given
		User userOne = new User("lszylo","password");
		User userTwo = new User("lszylo","password");
		userRepository.save(userOne);
		
		// When
		
		try {
			userRepository.save(userTwo);
			fail();
		} catch (DataIntegrityViolationException e) {
			// Then OK :)
		}
	}

}
