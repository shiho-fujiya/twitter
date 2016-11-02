package chapter6.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.User;
import chapter6.beans.UserMessage;
import chapter6.service.MessageService;
import chapter6.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String userId = request.getParameter("user_id");

		User user;
		List<UserMessage> messages;
		boolean isShowMessageForm;

		if (userId == null) {
			user = (User) request.getSession().getAttribute("loginUser");
			messages = new MessageService().getMessage(null);
			if (user != null) {
				isShowMessageForm = true;
			} else {
				isShowMessageForm = false;
			}
		} else {
			int uId = Integer.parseInt(userId);
			user = new UserService().getUser(uId);
			messages = new MessageService().getMessage(uId);
			isShowMessageForm = false;
		}
		request.setAttribute("user", user);
		request.setAttribute("messages", messages);
		request.setAttribute("isShowMessageForm", isShowMessageForm);

		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
