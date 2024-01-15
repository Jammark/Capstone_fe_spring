package capstone.fe.spring.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

import capstone.fe.spring.model.LoginRequest;
/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/Login")
@Configurable
public class LoginServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	private ObjectMapper mapper;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
		// super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		// ApplicationContext ac = (ApplicationContext)
		// config.getServletContext().getAttribute("applicationContext");

		// this.mapper = (ObjectMapper) ac.getBean("objectMapper");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		logger.debug(password);
		logger.debug(username);

		AsyncHttpClient client = Dsl.asyncHttpClient();

		Request getRequest = Dsl.post("http://localhost:3008/auth/login").setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json")
				.setRequestTimeout(4000)
				.setBody(mapper.writeValueAsString(new LoginRequest(username, password))).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);
		try {
			request.setAttribute("obj", new LoginRequest(username, password));
			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;

			if (result) {
				request.setAttribute("logged", true);

			} else {
				request.setAttribute("logged", false);
			}

		} catch (ExecutionException e) {
			e.printStackTrace();
			request.setAttribute("logged", false);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("logged", false);
		} finally {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login_");
			requestDispatcher.forward(request, response);
		}

	}

}
