package com.gabrielmaia.akumafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.gabrielmaia.akumafood.AkumafoodApiApplication;
import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;

public class ConsultationKitchenMain {
	
	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AkumafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		KitchenRepository createKitchen = context.getBean(KitchenRepository.class);
		List<Kitchen> kitchens = createKitchen.all();

		for (Kitchen kitchen : kitchens) {
			System.out.println(kitchen.getName());
		}
	}
}
