package capstone.fe.spring.conf;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import capstone.fe.spring.model.UserWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configurable
public class LogedInterceptor implements HandlerInterceptor {

	@Autowired
	private UserWrapper authData;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("intercepted request: " + request.getRequestURI());
		if (request.getRequestURI().endsWith("login") || request.getRequestURI().endsWith("register")
				|| request.getRequestURI().endsWith("login_reset")) {
			log.info("intercepted request not authenticated");
			return true;
		} else {
			log.info("intercepted authenticated request");
			if (request.getRequestURI().endsWith("home") && request.getMethod().equalsIgnoreCase("post")) {
				return true;
			}
			if (this.authData.getUser() == null) {

				HttpSession session = request.getSession();
				try {
					if (session != null) { // Infinite
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login_reset");

						// request.getParameterMap().clear();
						requestDispatcher.forward(request, response);
						// Map<String, Object> m = new HashMap<>();
						// m.put("notlogged", true);
						// ModelAndView mav = new ModelAndView("login", m);
						// eventually populate the model

						// throw new ModelAndViewDefiningException(mav);

					}
				} catch (Exception e) {
					e.toString();
				}
				return false;
			}
			return true;
		}

	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*
		 * log.info("intercepted request: " + request.getRequestURI()); if
		 * (request.getRequestURI().endsWith("login") ||
		 * request.getRequestURI().endsWith("register") ||
		 * request.getRequestURI().endsWith("login_reset")) {
		 * log.info("intercepted request not authenticated");
		 * 
		 * } else { log.info("intercepted authenticated request"); if
		 * (this.authData.getUser() == null) {
		 * 
		 * HttpSession session = request.getSession(); try { if (session != null) { //
		 * Infinite RequestDispatcher requestDispatcher =
		 * request.getRequestDispatcher("login_reset");
		 * requestDispatcher.forward(request, response);
		 * 
		 * } } catch (Exception e) { e.toString(); }
		 * 
		 * }
		 * 
		 * }
		 */
	}

}
