package capstone.fe.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import capstone.fe.spring.model.Città;
import capstone.fe.spring.model.MetaImgWrapper;
import capstone.fe.spring.model.Prenotazione;
import capstone.fe.spring.model.User;
import capstone.fe.spring.model.UserWrapper;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class HomeController {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserWrapper authData;

	@Value("${baseUrl}")
	private String baseUrl;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		log.info("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("baseUrl", baseUrl);
		User user = this.authData.getUser();
		model.addAttribute("user", user);
		return loadData(user, model);
	}

	@RequestMapping(value = "/home", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String user(User user, Model model) {
		log.info("User Page Requested");
		log.info(user.toString());
		model.addAttribute("user", user);
		model.addAttribute("baseUrl", baseUrl);
		this.authData.setUser(user);

		return loadData(user, model);
	}

	private String loadData(User user, Model model) {
		AsyncHttpClient client = Dsl.asyncHttpClient();


		Request getRequest = Dsl.get("http://localhost:3018/mete/città/most_rated")
				.setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json").setHeader("Authorization", "Bearer " + user.getToken())
				.setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);
		try {

			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;

			addCartCount(model, client, user.getToken());

			if (result) {
				List<Città> l = mapper.readValue(res.getResponseBody(), new TypeReference<List<Città>>() {
				});
				model.addAttribute("data", l);
				List<MetaImgWrapper> imgs = l.stream().map(Città::getImgUrl).map(MetaImgWrapper::new)
						.collect(Collectors.toList());
				model.addAttribute("json", mapper.writeValueAsString(imgs));
				log.info("" + l);
				log.info("" + mapper.writeValueAsString(imgs));
			} else {
				log.info("empty");
				model.addAttribute("data", "empty");
			}

		} catch (ExecutionException e) {
			log.info("error");
			e.printStackTrace();
			model.addAttribute("data", "empty");
		} catch (InterruptedException e) {
			log.info("error");
			e.printStackTrace();
			model.addAttribute("data", "empty");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("error");
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "home";
		}
	}

	private void addCartCount(Model model, AsyncHttpClient client, String token) throws Exception {
		Request getRequest = Dsl.get("http://localhost:3018/prenotazioni/saldo")
				.setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json").setHeader("Authorization", "Bearer " + token)
				.setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);
		Response res = responseFuture.get();
		List<Prenotazione> l = mapper.readValue(res.getResponseBody(), new TypeReference<List<Prenotazione>>() {
		});

		model.addAttribute("count", l.size());
	}
}
