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
import com.gabrielmaia.akumafood.domain.functions.MergeObjects;
import com.gabrielmaia.akumafood.domain.model.State;
import com.gabrielmaia.akumafood.domain.repository.StateRepository;
import com.gabrielmaia.akumafood.domain.service.StateServiceRegistration;

@RestController
@RequestMapping(value = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateServiceRegistration stateService;

	@Autowired
	private MergeObjects merge;

	@GetMapping
	public List<State> all() {
		return stateRepository.findAll();
	}

	@GetMapping("/{statesId}")
	public ResponseEntity<State> search(@PathVariable Long statesId) {
		Optional<State> state = stateRepository.findById(statesId);
		return state.isPresent() ? ResponseEntity.ok(state.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<State> add(@RequestBody State state) {
		return ResponseEntity.status(HttpStatus.CREATED).body(stateService.save(state));
	}

	@PutMapping("/{statesId}")
	public ResponseEntity<State> update(@PathVariable Long statesId, @RequestBody State state) {
		Optional<State> currentState = stateRepository.findById(statesId);

		if (currentState.isPresent()) {
			BeanUtils.copyProperties(state, currentState, "id");
			State updateState = stateService.save(currentState.get());
			return ResponseEntity.ok(updateState);
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{statesId}")
	public ResponseEntity<State> partialUpdate(@PathVariable Long statesId, @RequestBody State state) {
		Optional<State> currentState = stateRepository.findById(statesId);

		if (currentState.isPresent()) {
			merge.objects(state, currentState.get());
			State mergeState = stateService.save(currentState.get());
			return ResponseEntity.ok(mergeState);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{statesId}")
	public ResponseEntity<?> remove(@PathVariable Long statesId) {
		try {
			stateService.remove(statesId);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();

		} catch (EntityExceptionInUse e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}