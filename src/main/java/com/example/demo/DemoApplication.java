package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling  // Add the annotation here

public class DemoApplication {
	@RestController
	@RefreshScope  // To refresh configuration dynamically
	@Scope("prototype")
	@CrossOrigin(origins = "*") // Allow requests from any origin
	public class FeatureFlagsController {
		@Value("${FEATURE_FLAG_TEST}")
		private String FEATURE_FLAG_TEST;

		@Value("${FEATURE_FLAG_TEST2}")
		private String FEATURE_FLAG_TEST2;

		@GetMapping("/featureFlags")
		public Map<String, String> getFeatureFlags() {
			Map<String, String> featureFlagsMap = new HashMap<>();
			featureFlagsMap.put("FEATURE_FLAG_TEST", FEATURE_FLAG_TEST);
			featureFlagsMap.put("FEATURE_FLAG_TEST2", FEATURE_FLAG_TEST2);
			return featureFlagsMap;
		}
	}
	@Configuration
	public class RestTemplateConfig {

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}

	@Component // Make this a Spring-managed component
	public static class RefreshScheduler {

		@Autowired
		private RestTemplate restTemplate;

		@Scheduled(fixedDelay = 6000) // Refresh every minute.
		public void refreshConfig() {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON); // Add this line
			HttpEntity<String> entity = new HttpEntity<>(headers);
			restTemplate.postForEntity("http://localhost:8082/actuator/refresh", entity, Void.class);			}
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}


