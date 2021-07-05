package com.gabrielmaia.akumafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
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
	
	@GetMapping
	public List<State> all(){
		return stateRepository.all();
	}
	
	@GetMapping("/{stateId}")
	public ResponseEntity<State> search(@PathVariable Long stateId){
		State state = stateRepository.search(stateId);
		
		if (state != null)
			return ResponseEntity.ok(state);
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<State> add(@RequestBody State state) {
		return ResponseEntity.status(HttpStatus.CREATED).body(stateService.save(state));
	}
	
	@PutMapping("/{stateId}")
	public ResponseEntity<State> update(@PathVariable Long stateId, @RequestBody State state){
		State newState = stateRepository.search(stateId);
		
		if (newState != null) {
			BeanUtils.copyProperties(state, newState, "id");
			newState = stateService.save(newState);
			return ResponseEntity.ok(newState);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{stateId}")
	public ResponseEntity<?> remove(@PathVariable Long stateId){
		try {
			stateService.remove(stateId);	
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntityExceptionInUse e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
}