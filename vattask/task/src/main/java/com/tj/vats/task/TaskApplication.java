package com.tj.vats.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tj.vats.task.model.CountryVats;
import com.tj.vats.task.service.VatsService;

@SpringBootApplication
public class TaskApplication {
	
    private static final Logger log = LoggerFactory.getLogger(TaskApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner run(VatsService vatService) throws Exception {
		return args -> {
			
			try {
				Date today = new Date();
				List<CountryVats> countries = vatService.getRatesByStandard(today);
				
				List<CountryVats> lowest = vatService.getRatesByStandard(3, true, today);
				List<CountryVats> highest = vatService.getRatesByStandard(3, false, today);
				
				System.out.println("\nAll vats: ");
				countries.forEach(rate -> printVat(rate));
				
				System.out.println("\nLowest: ");
				lowest.forEach(rate -> printVat(rate));
				
				System.out.println("\nHighest: ");
				highest.forEach(rate -> printVat(rate));
			} catch (Exception e) {
				System.out.println("Exception occured while fetching VATs: " + e.getMessage());
				log.error(e.getMessage());
			}
			
		};
	}
	
	private void printVat(CountryVats c) {
		Double standard = c.getRates()==null?null:c.getRates().getStandard();
		System.out.println(c.getName() + " -> " + (standard==null?"none":standard));
	}

}

