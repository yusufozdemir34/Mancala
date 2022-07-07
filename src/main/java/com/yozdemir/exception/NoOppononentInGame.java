package com.yozdemir.exception;

public class NoOppononentInGame extends BaseException {

	private static final long serialVersionUID = 6730452935118897558L;
	private static final String EXCEPTION_MESSAGE = "There is no opponent. ";
	private static final String EXCEPTION_MESSAGE_FOR_JOINED = "There is no opponent. you should create new Game ";
	

	public NoOppononentInGame(int id) {
		super(EXCEPTION_MESSAGE + " Game is id " + id+" Wait for an opponent");
	}
	
	public NoOppononentInGame() {
		super(EXCEPTION_MESSAGE_FOR_JOINED);
	}
}
