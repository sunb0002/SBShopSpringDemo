package com.madoka.sunb0002.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Sun Bo
 *
 */
@Configuration
@PropertySource("classpath:app.properties")
public class PropertyConfig { // NOSONAR

	// Unable to create bean if I put private constructor as Sonar instructs

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setNullValue("");
		c.setIgnoreResourceNotFound(false);
		c.setIgnoreUnresolvablePlaceholders(false);
		return c;
	}

}
