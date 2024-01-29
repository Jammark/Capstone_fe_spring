package capstone.fe.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import capstone.fe.spring.model.Appartamento;
import capstone.fe.spring.model.Città;
import capstone.fe.spring.model.Destinazione;
import capstone.fe.spring.model.Hotel;
import capstone.fe.spring.model.MetaImgWrapper;
import capstone.fe.spring.model.User;
import capstone.fe.spring.model.UserWrapper;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MeteController {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserWrapper authData;

	@Value("${baseUrl}")
	private String baseUrl;

	@RequestMapping(value = "/dest/{metaId}", method = RequestMethod.GET)
	public String dest(Model model, @PathVariable("metaId") Long metaId) {

		AsyncHttpClient client = Dsl.asyncHttpClient();
		User user = this.authData.getUser();
		model.addAttribute("token", user.getToken());
		model.addAttribute("baseUrl", this.baseUrl);
		Request getRequest = Dsl.get("http://localhost:3018/mete/destinazioni/" + metaId)
				.setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json").setHeader("Authorization", "Bearer " + user.getToken())
				.setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);

		try {

			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;

			if (result) {
				Destinazione d = mapper.readValue(res.getResponseBody(), Destinazione.class);
				model.addAttribute("dest", d);
				model.addAttribute("img", "http://localhost:3018/mete/image/" + d.getImgUrl());
				log.info("" + d);

				model.addAttribute("hotels", new ArrayList<Hotel>());
				model.addAttribute("appartamenti", new ArrayList<Appartamento>());
				model.addAttribute("cities", new ArrayList<String>());

				Request getNCRequest = Dsl.get("http://localhost:3018/mete/città")
						.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
						.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

				Future<Response> responseNCFuture = client.executeRequest(getNCRequest);

				res = responseNCFuture.get();
				result = res.getStatusCode() == 200;

				if (result) {
					List<String> nc = mapper.readValue(res.getResponseBody(), new TypeReference<List<Città>>() {
					}).stream().map(Città::getNome).collect(Collectors.toList());
					model.addAttribute("nc", mapper.writeValueAsString(nc));
				}

				for (int i = 0; i < d.getCityIds().length; i++) {
					Request getHRequest = Dsl.get("http://localhost:3018/alloggi/hotels/meta/" + d.getCityIds()[i])
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseHFuture = client.executeRequest(getHRequest);

					res = responseHFuture.get();
					result = res.getStatusCode() == 200;

					if (result) {
						List<Hotel> lh = mapper.readValue(res.getResponseBody(), new TypeReference<List<Hotel>>() {
						});
						((ArrayList<Hotel>) model.getAttribute("hotels")).addAll(lh);
					}

					Request getARequest = Dsl
							.get("http://localhost:3018/alloggi/appartamenti/meta/" + d.getCityIds()[i])
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseAFuture = client.executeRequest(getARequest);

					res = responseAFuture.get();
					result = res.getStatusCode() == 200;

					if (result) {
						List<Appartamento> lh = mapper.readValue(res.getResponseBody(),
								new TypeReference<List<Appartamento>>() {
								});
						((ArrayList<Appartamento>) model.getAttribute("appartamenti")).addAll(lh);
					}

					Request getCRequest = Dsl.get("http://localhost:3018/mete/città/" + d.getCityIds()[i])
							.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
							.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

					Future<Response> responseCFuture = client.executeRequest(getCRequest);

					res = responseCFuture.get();
					result = res.getStatusCode() == 200;

					if (result) {
						Città città = mapper.readValue(res.getResponseBody(), Città.class);
						((List<String>) model.getAttribute("cities")).add(città.getNome());
					}
				}
				if (((ArrayList<Hotel>) model.getAttribute("hotels")).size() == 0) {
					model.addAttribute("hotels", "empty");
				} else {
					ArrayList<Hotel> lh = (ArrayList<Hotel>) model.getAttribute("hotels");
					List<String> hImgs = lh.stream().map(Hotel::getUrlImmagine)
							.map(s -> "http://localhost:3018/alloggi/image/" + s).collect(Collectors.toList());
					model.addAttribute("jsonH", mapper.writeValueAsString(model.getAttribute("hotels")));
					model.addAttribute("imgsH", hImgs);
				}
				if (((ArrayList<Appartamento>) model.getAttribute("appartamenti")).size() == 0) {

					model.addAttribute("appartamenti", "empty");

				} else {
					ArrayList<Appartamento> la = (ArrayList<Appartamento>) model.getAttribute("appartamenti");
					model.addAttribute("jsonA", mapper.writeValueAsString(model.getAttribute("appartamenti")));
					List<String> aImgs = la.stream().map(Appartamento::getUrlImmagine)
							.map(s -> "http://localhost:3018/alloggi/image/" + s).collect(Collectors.toList());
					model.addAttribute("imgsA", aImgs);
				}

			} else {
				log.info("empty code:" + res.getStatusCode());
				model.addAttribute("dest", null);
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
			return "dest";
		}

	}

	@RequestMapping(value = "/city/{metaId}", method = RequestMethod.GET)
	public String città(Model model, @PathVariable("metaId") Long metaId) {

		AsyncHttpClient client = Dsl.asyncHttpClient();
		User user = this.authData.getUser();

		model.addAttribute("token", user.getToken());
		model.addAttribute("baseUrl", this.baseUrl);
		Request getRequest = Dsl.get("http://localhost:3018/mete/città/" + metaId)
				.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
				.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);

		try {

			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;

			if (result) {
				Città c = mapper.readValue(res.getResponseBody(), Città.class);
				model.addAttribute("city", c);
				model.addAttribute("img", "http://localhost:3018/mete/image/" + c.getImgUrl());
				log.info("" + c);

				Request getNCRequest = Dsl.get("http://localhost:3018/mete/città")
						.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
						.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

				Future<Response> responseNCFuture = client.executeRequest(getNCRequest);

				res = responseNCFuture.get();
				result = res.getStatusCode() == 200;

				if (result) {
					List<String> nc = mapper.readValue(res.getResponseBody(), new TypeReference<List<Città>>() {
					}).stream().map(Città::getNome).collect(Collectors.toList());
					model.addAttribute("nc", mapper.writeValueAsString(nc));
				}

				Request hRequest = Dsl.get("http://localhost:3018/alloggi/hotels/meta/" + metaId)
						.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
						.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

				Future<Response> responseHFuture = client.executeRequest(hRequest);

				res = responseHFuture.get();
				result = res.getStatusCode() == 200;
				if (result) {
					List<Hotel> lh = mapper.readValue(res.getResponseBody(), new TypeReference<List<Hotel>>() {
					});
					List<String> hImgs = lh.stream().map(Hotel::getUrlImmagine)
							.map(s -> "http://localhost:3018/alloggi/image/" + s).collect(Collectors.toList());
					model.addAttribute("hotels", lh);
					model.addAttribute("jsonH", res.getResponseBody());
					model.addAttribute("imgsH", hImgs);
				} else {
					model.addAttribute("hotels", null);
				}

				Request aRequest = Dsl.get("http://localhost:3018/alloggi/appartamenti/meta/" + metaId)
						.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
						.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

				Future<Response> responseAFuture = client.executeRequest(aRequest);

				res = responseAFuture.get();
				result = res.getStatusCode() == 200;
				if (result) {
					List<Appartamento> la = mapper.readValue(res.getResponseBody(),
							new TypeReference<List<Appartamento>>() {
							});
					List<String> aImgs = la.stream().map(Appartamento::getUrlImmagine)
							.map(s -> "http://localhost:3018/alloggi/image/" + s).collect(Collectors.toList());
					model.addAttribute("appartamenti", la);
					model.addAttribute("jsonA", res.getResponseBody());
					model.addAttribute("imgsA", aImgs);
				} else {
					model.addAttribute("appartamenti", null);
				}

			} else {
				log.info("empty code:" + res.getStatusCode());
				model.addAttribute("city", "empty");
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
			return "city";
		}

	}

	@RequestMapping(value = "/mete", method = RequestMethod.GET)
	public String mete(Locale locale, Model model) {
		log.info("Mete Page Requested, locale = " + locale);

		AsyncHttpClient client = Dsl.asyncHttpClient();
		User user = this.authData.getUser();

		Request getRequest = Dsl.get("http://localhost:3018/mete/città").setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json").setHeader("Authorization", "Bearer " + user.getToken())
				.setRequestTimeout(4000).build();

		Future<Response> responseFuture = client.executeRequest(getRequest);
		try {

			Response res = responseFuture.get();
			boolean result = res.getStatusCode() == 200;

			if (result) {
				List<Città> l = mapper.readValue(res.getResponseBody(), new TypeReference<List<Città>>() {
				});
				model.addAttribute("città", l);

				log.info("" + l);


				getRequest = Dsl.get("http://localhost:3018/mete/destinazioni")
						.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
						.setHeader("Authorization", "Bearer " + user.getToken()).setRequestTimeout(4000).build();

				responseFuture = client.executeRequest(getRequest);
				res = responseFuture.get();
				result = res.getStatusCode() == 200;

				if (result) {

					List<Destinazione> l1 = mapper.readValue(res.getResponseBody(),
							new TypeReference<List<Destinazione>>() {
							});
					log.info("" + l1);
					model.addAttribute("destinazioni", l1);
					List<MetaImgWrapper> imgs = l.stream().map(Città::getImgUrl)
							.map(s -> "http://localhost:3018/mete/image/".concat(s)).map(MetaImgWrapper::new)
							.collect(Collectors.toList());
					List<MetaImgWrapper> imgs2 = l1.stream().map(Destinazione::getImgUrl)
							.map(s -> "http://localhost:3018/mete/image/".concat(s)).map(MetaImgWrapper::new)
							.collect(Collectors.toList());

					model.addAttribute("imgC", imgs);
					model.addAttribute("imgD", imgs2);
				} else {
					log.info("empty code:" + res.getStatusCode());
					model.addAttribute("data", "empty");
				}
			} else {
				log.info("empty");
				model.addAttribute("data", "empty");
			}

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
			return "mete";
		}
	}
}
