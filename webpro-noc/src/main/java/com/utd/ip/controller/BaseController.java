package com.utd.ip.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utd.ip.service.RestService;

@Controller
public class BaseController {

	private static final String VIEW_INDEX = "login";
	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private RestService restService;

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		restService.testService();

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "redirect:/mininoc/home";

	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		restService.testService();

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return VIEW_INDEX;

	}
	
	
	@RequestMapping(value = "/login/fail", method = RequestMethod.GET)
	public String loginFail(Model model) {

		//restService.testService();
		model.addAttribute("statusMessage", "Username and password doesn't match.");

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "login";

	}

}
