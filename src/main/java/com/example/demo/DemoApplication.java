package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

public class DemoApplication {
	@RestController
	@RefreshScope  // To refresh configuration dynamically
	public class FeatureFlagsController {
		@Value("${FEATURE_FLAG_TEST}")
		private String test;

		@GetMapping("/test")
		public String getFeatureFlags() {
			return test;
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


}
