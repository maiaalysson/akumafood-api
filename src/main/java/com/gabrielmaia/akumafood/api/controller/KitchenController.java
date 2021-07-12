package com.gabrielmaia.akumafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;
import com.gabrielmaia.akumafood.domain.service.KitchenServiceRegistration;
import com.gabrielmaia.akumafood.infrastructure.repository.MergeObjects;

@RestController
@RequestMapping(value = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController {

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private KitchenServiceRegistration kitchenService;
	
	@Autowired
	private MergeObjects merge;

	@GetMapping
	public List<Kitchen> all() {
		return kitchenRepository.findAll();
	}

	@GetMapping("/{kitchensId}")
	public ResponseEntity<Kitchen> search(@PathVariable Long kitchensId) {
		Optional<Kitchen> kitchen = kitchenRepository.findById(kitchensId);
		return kitchen.isPresent() ? ResponseEntity.ok(kitchen.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Kitchen> add(@RequestBody Kitchen kitchen) {
		return ResponseEntity.status(HttpStatus.CREATED).body(kitchenService.save(kitchen));
	}
	
	@PutMapping("/{kitchensId}")
	public ResponseEntity<Kitchen> update(@PathVariable Long kitchensId, @RequestBody Kitchen kitchen) {
		Optional<Kitchen> currentKitchen = kitchenRepository.findById(kitchensId);

		if (currentKitchen.isPresent()) {
			BeanUtils.copyProperties(kitchen, currentKitchen.get(), "id");
			Kitchen updateKitchen = kitchenService.save(currentKitchen.get());
			return ResponseEntity.ok(updateKitchen);
		}

		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/{kitchensId}")
	public ResponseEntity<Kitchen> partialUpdate(@PathVariable Long kitchensId, @RequestBody Kitchen kitchen){
		Optional<Kitchen> currentKitchen = kitchenRepository.findById(kitchensId);
		
		if(currentKitchen.isPresent()) {
			merge.objects(kitchen, currentKitchen.get());
			Kitchen mergeKitchen = kitchenService.save(currentKitchen.get());
			return ResponseEntity.ok(mergeKitchen);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{kitchensId}")
	public ResponseEntity<?> delete(@PathVariable Long kitchensId) {
		try {
			kitchenService.remove(kitchensId);
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
		
		} catch (EntityExceptionInUse e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}