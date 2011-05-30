package com.tieto.sample.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {

	private static final String NAME_PARAM = "name";
	private static final long serialVersionUID = -1129707722235190427L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        setContentType(response);
        PrintWriter writer = response.getWriter();
        
        String name = request.getParameter(NAME_PARAM);
        if (name != null && !name.isEmpty()) {
        	sayHello(writer, name);
        } else {
        	printUsage(writer, request);
        }
        
        
		setResponseStatus(response);
	}

	private void printUsage(PrintWriter writer, HttpServletRequest request) {
		writer.println("<h3>Hello, User</h3>");
		StringBuffer requestURL = request.getRequestURL();
		requestURL.append("?name=SampleName");
		writer.format("<p>If you wanna receive some greetings, please specify your name " +
			"as a request parameter, for example: </br><a href=\"%s\"><b>%s</b></a></p>",
			requestURL, requestURL);		
	}

	private void setContentType(HttpServletResponse response) {
		response.setContentType("text/html");
	}

	private void sayHello(PrintWriter writer, String name) {
		writer.format("<h1>Hello, %s!</h1>", name);
	}

	private void setResponseStatus(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	
}
