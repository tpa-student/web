package com.tieto.webapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tieto.webapp.domain.User;
import com.tieto.webapp.service.UserRepository;


@Component
@Configurable
@RooDataOnDemand(entity = User.class)
public class TestUserFactory {

	
	@Autowired
	private UserRepository userRepository;

	private User getNewTransientUser(int index) {
        User obj = new User();
        obj.setLogin("login_" + index);
        obj.setPassword("password_" + index);
        return obj;
    }



	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertTimes(int count) {
        for (int i = 0; i < count; i++) {
            User obj = getNewTransientUser(i);
            userRepository.save(obj);
        }
    }
}
