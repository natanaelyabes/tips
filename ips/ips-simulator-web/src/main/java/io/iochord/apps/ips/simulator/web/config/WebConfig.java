package io.iochord.apps.ips.simulator.web.config;

/**
*
* @package ips-simulator-web
* @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since   2019
*
*/
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * SpringBoot Web Configuration Injector
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Override default REST output as a JSON format
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(false).ignoreAcceptHeader(true)
				.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

}