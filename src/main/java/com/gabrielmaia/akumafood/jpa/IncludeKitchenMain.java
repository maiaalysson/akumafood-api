package com.gabrielmaia.akumafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.gabrielmaia.akumafood.AkumafoodApiApplication;
import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;

public class IncludeKitchenMain {
	
	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AkumafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		KitchenRepository createKitchen = context.getBean(KitchenRepository.class);
		
		Kitchen kitchen = new Kitchen();
		Kitchen kitchen1 = new Kitchen();
		
		kitchen.setName("Japanese");
		kitchen1.setName("Brazilian");
		
		createKitchen.save(kitchen);
		createKitchen.save(kitchen1);
		
		System.out.printf("%d - %s\n", kitchen.getId(), kitchen.getName());
		System.out.printf("%d - %s\n", kitchen1.getId(), kitchen1.getName());
	}
}
