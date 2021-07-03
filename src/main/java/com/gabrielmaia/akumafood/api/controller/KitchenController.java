package com.gabrielmaia.akumafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;
import com.gabrielmaia.akumafood.domain.service.KitchenServiceRegistration;

@RestController
@RequestMapping(value = "/kitchens") // , produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController {

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private KitchenServiceRegistration kichenRegistration;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Kitchen add(@RequestBody Kitchen kitchen) {
		return kichenRegistration.save(kitchen);
	}

	@GetMapping
	public List<Kitchen> all() {
		return kitchenRepository.all();
	}

	// @ResponseStatus(HttpStatus.OK)
	@GetMapping("/{kitchensId}")
	public ResponseEntity<Kitchen> search(@PathVariable Long kitchensId) {
		Kitchen kitchen = kitchenRepository.search(kitchensId);

		// HttpHeaders headers = new HttpHeaders();
		// headers.add(HttpHeaders.LOCATION,
		// "http://api.akumafood.local:8080/kitchens");

		// return ResponseEntity.status(HttpStatus.OK).body(kitchen);
		return kitchen != null ? ResponseEntity.ok(kitchen) : ResponseEntity.notFound().build();

		// return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	}

	@PutMapping("/{kitchensId}")
	public ResponseEntity<Kitchen> update(@PathVariable Long kitchensId, @RequestBody Kitchen kitchen) {
		Kitchen currentKitchen = kitchenRepository.search(kitchensId);

		if (currentKitchen != null) {

			// currentKitchen.setName(kitchen.getName());
			BeanUtils.copyProperties(kitchen, currentKitchen, "id");

			currentKitchen = kitchenRepository.save(currentKitchen);
			return ResponseEntity.ok(currentKitchen);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{kitchensId}")
	public ResponseEntity<Kitchen> delete(@PathVariable Long kitchensId) {
		try {
			kichenRegistration.remove(kitchensId);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
		} catch (EntityExceptionInUse e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}