package com.chengyan.webapp;

import com.chengyan.webapp.ServiceController.StatsD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(WebappApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	@GetMapping("/healthz")
	public void healthz() {
		logger.debug("Debugging log");
		logger.info("Info log");
		statsd.getStatsd().incrementCounter("/healthz.http.get");
		return;
	}

}
