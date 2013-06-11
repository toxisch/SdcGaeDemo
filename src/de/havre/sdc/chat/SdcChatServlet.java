package de.havre.sdc.chat;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * SdcChatServlet is a simple chat application to demonstrate the GAE Channel
 * and User API.
 */
@SuppressWarnings("serial")
public class SdcChatServlet extends HttpServlet {

	Logger logger = Logger.getLogger(SdcChatServlet.class.getName());
	
	/**
	 * A id to identify an channel. The channel can be unique to just one client
	 * (1 to 1 communication). Is is also possible to connect more than one
	 * client on the same channel. Any connected client will receive the
	 * messages of the channel (1 to * communication).
	 */
	private static final String SDC_CHANNEL = "sdc_channel";

	/*
	 * The initial call is always an GET request, which initialize the
	 * JavaScript client.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		/* Use UserService to resolve the current user. */
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			/* Make logoutUrl and UserName available for JSP template */
			String logoutUrl = userService.createLogoutURL(req.getRequestURI());
			req.setAttribute("userLogout", logoutUrl);
			req.setAttribute("userName", user.getNickname());

			/*
			 * Create a new client token for the channel an make it available
			 * for JSP template
			 */
			ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			String token = channelService.createChannel(SDC_CHANNEL);
			req.setAttribute("token", token);

			/* Render page */
			RequestDispatcher rd = this.getServletContext()
					.getRequestDispatcher("/sdcchat.jsp");
			rd.forward(req, resp);
		} else {
			/* No user was resolved. redirect login page. */
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}
	}

	/*
	 * The JavaScript client posts new messages by an inner ajax request.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		/* Use UserService to resolve the current user. */
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {

			/*
			 * Create the posted message to create a chennelMessage, which will
			 * be posted to any connected client.
			 */
			String message = req.getParameter("message");
			logger.info("New message posted: " +  message);
			String channelMessageText = "[" + user.getNickname() + "] : "
					+ message;

			ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			channelService.sendMessage(new ChannelMessage(SDC_CHANNEL,
					channelMessageText));
		}
	}

}
