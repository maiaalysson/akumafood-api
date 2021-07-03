package com.gabrielmaia.akumafood.domain.exception;

public class EntityExceptionInUse extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntityExceptionInUse(String message) {
		super(message);
	}
}
