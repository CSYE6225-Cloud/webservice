package com.chengyan.webapp;

import com.chengyan.webapp.ServiceController.StatsD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebappApplication {

	@Autowired
	private StatsD statsd;

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	@GetMapping("/healthz")
	public void healthz() {
		statsd.getStatsd().incrementCounter("/healthz.http.get");
		return;
	}

}
