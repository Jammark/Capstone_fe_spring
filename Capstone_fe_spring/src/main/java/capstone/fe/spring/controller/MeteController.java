package capstone.fe.spring.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import capstone.fe.spring.model.Città;
import capstone.fe.spring.model.Destinazione;
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
			return "mete";
		}
	}
}
