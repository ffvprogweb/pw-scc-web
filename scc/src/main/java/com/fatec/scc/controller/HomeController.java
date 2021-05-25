package com.fatec.scc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	Logger logger = LogManager.getLogger(HomeController.class);
	@GetMapping("/login")
	public ModelAndView autenticacao() {
		logger.info(">>>>>> 1. frontcontroller chamou pagina login");
		return new ModelAndView("paginaLogin");
	}

	@GetMapping("/")
	public ModelAndView home() {
		logger.info(">>>>>> 1. frontcontroller chamou pagina menu");
		return new ModelAndView ("paginaMenu");
	}
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, Authentication authentication) {
		logger.info("Logout para o usuario: " + authentication.getName());
		HttpSession session= request.getSession(false);
	        	SecurityContextHolder.clearContext();
	    	 if(session != null) {
	            		session.invalidate();
	       	}
	       	 return new ModelAndView("paginaLogin");
	}
	
}
