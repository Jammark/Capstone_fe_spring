package capstone.fe.spring.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Value("${baseUrl}")
	private String baseUrl;


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		model.addAttribute("baseUrl", this.baseUrl);
		return "login";
	}

	@RequestMapping(value = "/login_reset", method = RequestMethod.GET)
	public String login_reset(Locale locale, Model model, RedirectAttributes ra) {
		ra.addAttribute("notlogged", true);
		return "redirect:login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Locale locale, Model model) {

		return "register";
	}

	@RequestMapping(value = "/rl", method = RequestMethod.GET)
	public String rl(Locale locale, Model model) {

		return "redirect:login";
	}
	/*
	 * @RequestMapping(value = "/loginview", method = { RequestMethod.POST,
	 * RequestMethod.GET }) public String loginview(Locale locale, Model model,
	 * LoginRequest lr, final RedirectAttributes ra,
	 * 
	 * @RequestParam(name = "username", required = false) String username,
	 * 
	 * @RequestParam(name = "password", required = false) String password) {
	 * 
	 * 
	 * ra.addAttribute("username", username); ra.addAttribute("password", password);
	 * return "forward:login"; }
	 */
	/*
	 * @RequestMapping(value = "/_login", method = RequestMethod.POST) public String
	 * login_(@Validated LoginRequest lr, Model model, final RedirectAttributes ra)
	 * { // ra.addAttribute("obj", lr); ra.addAttribute("username",
	 * lr.getUsername()); ra.addAttribute("password", lr.getPassword()); return
	 * "redirect:loginview"; }
	 */
	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.GET) public String
	 * _login(Locale locale, Model model, @RequestParam(name = "username", required
	 * = false) String username,
	 * 
	 * @RequestParam(name = "password", required = false) String password) { if
	 * (username != null && password != null) { LoginRequest lr = new
	 * LoginRequest(username, password); model.addAttribute("obj", lr); } return
	 * "login"; }
	 */

	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.POST) public String
	 * login(Model model) {
	 * 
	 * return "redirect:/login"; }
	 */
}
