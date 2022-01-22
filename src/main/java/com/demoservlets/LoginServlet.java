package com.demoservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**** UC-2:- Create a simple Login with predefined user. ****/

@WebServlet(
	description = "Login Servlet Testing",
	urlPatterns = { "/LoginServlet" },
	initParams = {
			@WebInitParam(name = "user" , value = "Nikhil"),
			@WebInitParam(name = "password" , value = "Nikhil@010")
	}
)
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		String userID = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");

		/** UC-3:- Extend the Servlet to accept a valid Name. **/
		boolean userPatternCheck = Pattern.compile("^[A-Z]{1}[a-z]{2,}$").matcher(user).matches();
		
		if(userPatternCheck != true) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Name pattern not matched.</font>");
			rd.include(request, response);
			return;
		}
		
		/** UC-4:- Extend the Servlet to accept a valid password. **/
		boolean passwordPatternCheck = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=[\\w]*[\\W][\\w]*$)(?=.*[a-z]).{8,}$").matcher(pwd).matches();
		
		if(passwordPatternCheck != true) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Password pattern not matched.</font>");
			rd.include(request, response);
			return;
		}
		if(userID.equals(user) && password.equals(pwd)) {
			request.setAttribute("user", user);
			request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
		}
		else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Either username or password is wrong.</font>");
			rd.include(request, response);
		}
	}
}
