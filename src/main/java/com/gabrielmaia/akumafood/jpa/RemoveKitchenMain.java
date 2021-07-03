package com.gabrielmaia.akumafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.gabrielmaia.akumafood.AkumafoodApiApplication;
import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;

public class RemoveKitchenMain {
	
	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AkumafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		KitchenRepository createKitchen = context.getBean(KitchenRepository.class);
		
		Kitchen kitchen = new Kitchen();
		kitchen.setId(1L);
		
		createKitchen.remove(kitchen.getId());
	}
}
