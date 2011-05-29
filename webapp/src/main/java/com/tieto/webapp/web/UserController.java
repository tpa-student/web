package com.tieto.webapp.web;

import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.roo.addon.web.mvc.controller.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.tieto.webapp.domain.*;
import com.tieto.webapp.service.*;

@RooWebScaffold(path = "user", automaticallyMaintainView = true, formBackingObject = User.class)
@RequestMapping("/user/**")
@Controller
public class UserController {
	
	@Autowired 
//	@Qualifier("in-memory-repository")
	@Qualifier("jpa")
	private UserRepository userRepository;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult result, ModelMap modelMap) {
        if (user == null) {
        	throw new IllegalArgumentException("A user is required");
        }
        if (result.hasErrors()) {
            modelMap.addAttribute("user", user);
            return "user/create";
        }
        userRepository.save(user);
        return "redirect:/user/" + user.getId();
    }

	@RequestMapping(value = "/user/form", method = RequestMethod.GET)
    public String createForm(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "user/create";
    }

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) {
        	throw new IllegalArgumentException("An Identifier is required");
        }
        modelMap.addAttribute("user", userRepository.findUser(id));
        return "user/show";
    }

	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("users", userRepository.findUserEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) userRepository.countUsers() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("users", userRepository.findAllUsers());
        }
        return "user/list";
    }

	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid User user, BindingResult result, ModelMap modelMap) {
        if (user == null) {
        	throw new IllegalArgumentException("A user is required");
        }
        if (result.hasErrors()) {
            modelMap.addAttribute("user", user);
            return "user/update";
        }
        User updated = userRepository.update(user);
        return "redirect:/user/" + updated.getId();
    }

	@RequestMapping(value = "/user/{id}/form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) {
        	throw new IllegalArgumentException("An Identifier is required");
        }
        modelMap.addAttribute("user", userRepository.findUser(id));
        return "user/update";
    }

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) {
        	throw new IllegalArgumentException("An Identifier is required");
        }
        User user = userRepository.findUser(id);
        userRepository.remove(user);
        return "redirect:/user?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
}
