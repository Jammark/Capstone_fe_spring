package capstone.fe.spring.conf;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

//@EnableWebMvc
@Configuration
//@ComponentScan(basePackages = "capstone.fe.spring.servlet")
public class AppConfig implements WebMvcConfigurer {

	@Bean

	public ObjectMapper objectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		// module.addSerializer(LOCAL_DATETIME_SERIALIZER);
		return new ObjectMapper()
				// .setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.registerModule(module);
	}

	/*
	 * @Bean public SpringApplicationContextListener getListener() { return new
	 * SpringApplicationContextListener(); }
	 */
	@Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet servlet = new DispatcherServlet();
		// servlet.getServletContext().addListener(getListener());
		return servlet;
	}

	/*
	 * @Bean public ServletRegistrationBean dispatcherServletRegistration() {
	 * DispatcherServlet ds = dispatcherServlet(); ServletRegistrationBean
	 * registrationBean = new ServletRegistrationBean(); //
	 * registrationBean.setServlet((Servlet) dispatcherServlet()); //
	 * registrationBean.setName(DispatcherServletAutoConfiguration.
	 * DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
	 * 
	 * return registrationBean; }
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub

	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub

	}
}
