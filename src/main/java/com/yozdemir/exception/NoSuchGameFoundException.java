package com.yozdemir.exception;

public class NoSuchGameFoundException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static final String EXCEPTION_MESSAGE = "No such game found with ";
	private static final String EXCEPTION_MESSAGE_WITHOUT_GAME = "No Game ";

	public NoSuchGameFoundException(int id) {
		super(EXCEPTION_MESSAGE + "id " + id);
	}
	public NoSuchGameFoundException() {
		super(EXCEPTION_MESSAGE_WITHOUT_GAME);
	}

}
