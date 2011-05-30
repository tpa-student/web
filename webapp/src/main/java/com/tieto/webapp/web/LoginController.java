package com.tieto.webapp.web;

import java.io.*;

import javax.servlet.http.*;
import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.tieto.webapp.domain.*;
import com.tieto.webapp.service.*;

@Controller
@Scope(value = "session")
@RequestMapping("/login/**")
public class LoginController implements Serializable {

	private static final long serialVersionUID = 134243275534L;
	
	@Autowired
	private Authenticator authenticator;

	public LoginController() {
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		modelMap.put("credentials", new Credentials());
		return "login/show";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid Credentials credentials, BindingResult result,
			ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (authenticator.authenticate(credentials)) {
			request.getSession(true).setAttribute("user", authenticator.getUser());
			return showLoginSuccess(modelMap);
		}
		return showLoginFailure(modelMap);
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(@Valid Credentials credentials, BindingResult result,
			ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			request.getSession(true).removeAttribute("user");
			return "login/success";
	}

	@RequestMapping(value = "/login/success", method = RequestMethod.GET)
	public String showLoginSuccess(ModelMap modelMap) {
		modelMap.put("credentials", new Credentials());
		return "login/success";
	}

	@RequestMapping(value = "/login/failure", method = RequestMethod.GET)
	public String showLoginFailure(ModelMap modelMap) {
		modelMap.put("credentials", new Credentials());
		return "login/failure";
	}

}
