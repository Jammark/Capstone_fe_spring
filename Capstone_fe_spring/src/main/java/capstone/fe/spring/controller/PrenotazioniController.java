package capstone.fe.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import capstone.fe.spring.model.Acquisto;
import capstone.fe.spring.model.Alloggio;
import capstone.fe.spring.model.Appartamento;
import capstone.fe.spring.model.Città;
import capstone.fe.spring.model.Hotel;
import capstone.fe.spring.model.Prenotazione;
import capstone.fe.spring.model.User;
import capstone.fe.spring.model.UserWrapper;
import capstone.fe.spring.model.Volo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PrenotazioniController {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserWrapper authData;

	@Value("${baseUrl}")
	private String baseUrl;

	@RequestMapping(value = "/saldo", method = RequestMethod.GET)
	public String dest(Model model) {
		AsyncHttpClient client = Dsl.asyncHttpClient();
		User user = this.authData.getUser();
		model.addAttribute("token", user.getToken());
		model.addAttribute("baseUrl", this.baseUrl);
		Request getRequest = Dsl.get("http://localhost:3018/prenotazioni/saldo")
				.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
				.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);

		try {

			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;

			if (result) {
				List<Prenotazione> lh = mapper.readValue(res.getResponseBody(),
						new TypeReference<List<Prenotazione>>() {
						});
				model.addAttribute("prenotazioni", lh);
				model.addAttribute("json", res.getResponseBody());

				Map<Long, Alloggio> aMap = new HashMap<>();
				model.addAttribute("alloggi", aMap);

				Map<Long, Volo> vMap = new HashMap<>();
				model.addAttribute("trasporti", vMap);

				Map<Long, Città> cMap = new HashMap<>();
				model.addAttribute("cities", cMap);

				for (Prenotazione p : lh) {
					Request getHRequest = Dsl.get("http://localhost:3018/alloggi/hotels/" + p.getAlloggioId())
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseHFuture = client.executeRequest(getHRequest);
					res = responseHFuture.get();
					result = res.getStatusCode() == 200;
					Long metaId;
					if (result) {
						Hotel h = mapper.readValue(res.getResponseBody(), Hotel.class);
						((Map<Long, Alloggio>) model.getAttribute("alloggi")).put(p.getId(), h);
						metaId = h.getMetaId();

					} else {
						Request getARequest = Dsl.get("http://localhost:3018/alloggi/appartamenti/" + p.getAlloggioId())
								.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
								.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000)
								.build();

						Future<Response> responseAFuture = client.executeRequest(getARequest);
						res = responseAFuture.get();
						result = res.getStatusCode() == 200;

						if (result) {
							Appartamento h = mapper.readValue(res.getResponseBody(), Appartamento.class);
							((Map<Long, Alloggio>) model.getAttribute("alloggi")).put(p.getId(), h);

							metaId = h.getMetaId();
						} else {
							metaId = null;
						}
					}
					if (metaId != null) {
						Request getCRequest = Dsl.get("http://localhost:3018/mete/città/" + metaId)
								.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
								.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000)
								.build();

						Future<Response> responseCFuture = client.executeRequest(getCRequest);

						res = responseCFuture.get();
						result = res.getStatusCode() == 200;
						if (result) {
							Città c = mapper.readValue(res.getResponseBody(), Città.class);
							((Map<Long, Città>) model.getAttribute("cities")).put(p.getId(), c);
						}
					}

					Request getARequest = Dsl.get("http://localhost:3018/trasporti/voli/" + p.getTrasportoId())
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseAFuture = client.executeRequest(getARequest);
					res = responseAFuture.get();
					result = res.getStatusCode() == 200;
					if (result) {
						Volo andata = mapper.readValue(res.getResponseBody(), Volo.class);
						((Map<Long, Volo>) model.getAttribute("trasporti")).put(andata.getId(), andata);
					}

					Request getRRequest = Dsl.get("http://localhost:3018/trasporti/voli/" + p.getRitornoId())
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseRFuture = client.executeRequest(getRRequest);
					res = responseRFuture.get();
					result = res.getStatusCode() == 200;
					if (result) {
						Volo andata = mapper.readValue(res.getResponseBody(), Volo.class);
						((Map<Long, Volo>) model.getAttribute("trasporti")).put(andata.getId(), andata);
					}

				}
			} else {
				log.info("saldo nessun risultato.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", true);
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "saldo";
		}

	}

	@RequestMapping(value = "/riepilogo", method = RequestMethod.GET)
	public String riepilogo(Model model) {
		AsyncHttpClient client = Dsl.asyncHttpClient();
		User user = this.authData.getUser();
		model.addAttribute("token", user.getToken());

		Request getRequest = Dsl.get("http://localhost:3018/prenotazioni/acquisti")
				.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
				.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);

		try {

			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;

			List<Acquisto> lh = mapper.readValue(res.getResponseBody(), new TypeReference<List<Acquisto>>() {
			});
			model.addAttribute("acquisti", lh);

			Map<Long, Città> cMap = new HashMap<>();
			model.addAttribute("cities", cMap);
			Map<Long, Alloggio> aMap = new HashMap<Long, Alloggio>();
			model.addAttribute("alloggi", aMap);
			for (Acquisto a : lh) {
				Request getHRequest = Dsl
						.get("http://localhost:3018/alloggi/hotels/" + a.getPrenotazione().getAlloggioId())
						.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
						.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

				Future<Response> responseHFuture = client.executeRequest(getHRequest);
				res = responseHFuture.get();
				result = res.getStatusCode() == 200;
				Long metaId;
				if (result) {
					Hotel h = mapper.readValue(res.getResponseBody(), Hotel.class);
					((Map<Long, Alloggio>) model.getAttribute("alloggi")).put(a.getPrenotazioneId(), h);
					metaId = h.getMetaId();

				} else {
					Request getARequest = Dsl
							.get("http://localhost:3018/alloggi/appartamenti/" + a.getPrenotazione().getAlloggioId())
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseAFuture = client.executeRequest(getARequest);
					res = responseAFuture.get();
					result = res.getStatusCode() == 200;

					if (result) {
						Appartamento h = mapper.readValue(res.getResponseBody(), Appartamento.class);
						((Map<Long, Alloggio>) model.getAttribute("alloggi")).put(a.getPrenotazioneId(), h);

						metaId = h.getMetaId();
					} else {
						metaId = null;
					}
				}
				if (metaId != null) {
					Request getCRequest = Dsl.get("http://localhost:3018/mete/città/" + metaId)
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseCFuture = client.executeRequest(getCRequest);

					res = responseCFuture.get();
					result = res.getStatusCode() == 200;
					if (result) {
						Città c = mapper.readValue(res.getResponseBody(), Città.class);
						((Map<Long, Città>) model.getAttribute("cities")).put(a.getPrenotazioneId(), c);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", true);
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "riepilogo";
		}

	}
}
