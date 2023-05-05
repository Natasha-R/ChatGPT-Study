package thkoeln.st.st2praktikum;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;


@SpringBootApplication
public class ProjectApplication {

	/**
	 * Entry method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Configuration
	class WebMvcConfiguration extends WebMvcConfigurationSupport {
		@Override
		protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
			for(HttpMessageConverter converter: converters) {
				if(converter instanceof MappingJackson2HttpMessageConverter) {
					ObjectMapper mapper = ((MappingJackson2HttpMessageConverter)converter).getObjectMapper();
					mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				}
			}
		}
	}
}
