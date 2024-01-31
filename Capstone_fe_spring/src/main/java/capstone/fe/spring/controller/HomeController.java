package capstone.fe.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

import capstone.fe.spring.model.Alloggio;
import capstone.fe.spring.model.Appartamento;
import capstone.fe.spring.model.Città;
import capstone.fe.spring.model.Hotel;
import capstone.fe.spring.model.MetaImgWrapper;
import capstone.fe.spring.model.Prenotazione;
import capstone.fe.spring.model.Trasporto;
import capstone.fe.spring.model.User;
import capstone.fe.spring.model.UserWrapper;
import capstone.fe.spring.model.Volo;
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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String start(Locale locale, Model model) {
		log.info("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("baseUrl", baseUrl);
		User user = this.authData.getUser();
		model.addAttribute("user", user);
		return loadData(user, model);
	}

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
		return user != null ? loadData(user, model) : "home";
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


		Request getRequest = Dsl.get(this.baseUrl + "mete/città/most_rated")
				.setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json")// .setHeader("Authorization", "Bearer " + user.getToken())
				.setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);
		try {

			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;


			Map<Long, List<Prenotazione>> pMap = new HashMap<>();
			model.addAttribute("pacchetti", pMap);

			Map<Long, String> aImgMap = new HashMap<Long, String>();
			model.addAttribute("alloggiImgs", aImgMap);

			Map<Long, Alloggio> aMap = new HashMap<>();
			model.addAttribute("alloggi", aMap);

			Map<Long, Trasporto> tMap = new HashMap<>();
			model.addAttribute("trasporti", tMap);

			if (result) {
				List<Città> l = mapper.readValue(res.getResponseBody(), new TypeReference<List<Città>>() {
				});
				model.addAttribute("data", l);
				List<MetaImgWrapper> imgs = l.stream().map(Città::getImgUrl).map(MetaImgWrapper::new)
						.collect(Collectors.toList());
				model.addAttribute("jsonImgs", mapper.writeValueAsString(imgs));
				model.addAttribute("json",
						mapper.writeValueAsString(l.stream().map(Città::getId).collect(Collectors.toList())));
				log.info("" + l);
				log.info("" + mapper.writeValueAsString(imgs));



				if (user != null) {
					model.addAttribute("token", user.getToken());
					addCartCount(model, client, user.getToken());

				for (Città c : l) {
					Request getPRequest = Dsl.get(this.baseUrl + "prenotazioni/" + c.getId())
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responsePFuture = client.executeRequest(getPRequest);

					res = responsePFuture.get();
					result = res.getStatusCode() == 200;

					if (result) {
						List<Prenotazione> pl = mapper.readValue(res.getResponseBody(),
								new TypeReference<List<Prenotazione>>() {
								});
						((Map<Long, List<Prenotazione>>) model.getAttribute("pacchetti")).put(c.getId(), pl);

						for (Prenotazione p : pl) {
							Request getHRequest = Dsl.get(this.baseUrl + "alloggi/hotels/" + p.getAlloggioId())
									.setHeader("Content-Type", "application/json")
									.setHeader("Accept", "application/json")
									.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000)
									.build();

							Future<Response> responseHFuture = client.executeRequest(getHRequest);

							res = responseHFuture.get();
							result = res.getStatusCode() == 200;

							if (result) {
								Hotel h = mapper.readValue(res.getResponseBody(), Hotel.class);
								((Map<Long, Alloggio>) model.getAttribute("alloggi")).put(h.getId(), h);
								((Map<Long, String>) model.getAttribute("alloggiImgs")).put(h.getId(),
										this.baseUrl + "alloggi/image/" + h.getUrlImmagine());
							} else {
								Request getARequest = Dsl
										.get(this.baseUrl + "alloggi/appartamenti/" + p.getAlloggioId())
										.setHeader("Content-Type", "application/json")
										.setHeader("Accept", "application/json")
										.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000)
										.build();

								Future<Response> responseAFuture = client.executeRequest(getARequest);

								res = responseAFuture.get();
								result = res.getStatusCode() == 200;

								if (result) {
									Appartamento a = mapper.readValue(res.getResponseBody(), Appartamento.class);
									((Map<Long, Alloggio>) model.getAttribute("alloggi")).put(a.getId(), a);
									((Map<Long, String>) model.getAttribute("alloggiImgs")).put(a.getId(),
											this.baseUrl + "alloggi/image/" + a.getUrlImmagine());
								}
							}

							Request getTARequest = Dsl.get(this.baseUrl + "trasporti/voli/" + p.getTrasportoId())
									.setHeader("Content-Type", "application/json")
									.setHeader("Accept", "application/json")
									.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000)
									.build();

							Future<Response> responseTAFuture = client.executeRequest(getTARequest);

							res = responseTAFuture.get();
							result = res.getStatusCode() == 200;

							if (result) {
								Volo v = mapper.readValue(res.getResponseBody(), Volo.class);
								((Map<Long, Trasporto>) model.getAttribute("trasporti")).put(v.getId(), v);
							}

							Request getTRRequest = Dsl.get(this.baseUrl + "trasporti/voli/" + p.getRitornoId())
									.setHeader("Content-Type", "application/json")
									.setHeader("Accept", "application/json")
									.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000)
									.build();

							Future<Response> responseTRFuture = client.executeRequest(getTRRequest);

							res = responseTRFuture.get();
							result = res.getStatusCode() == 200;

							if (result) {
								Volo v = mapper.readValue(res.getResponseBody(), Volo.class);
								((Map<Long, Trasporto>) model.getAttribute("trasporti")).put(v.getId(), v);
							}
						}
					}
				}

				model.addAttribute("jsonP", mapper.writeValueAsString(model.getAttribute("pacchetti")));

			}
			} else {
				log.info("empty");
				model.addAttribute("data", "empty");
			}

			log.info("pacchetti: " + model.getAttribute("pacchetti"));



		} catch (Exception e) {
			e.printStackTrace();
			log.info("error");

			model.addAttribute("error", true);
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
