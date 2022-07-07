package com.yozdemir.exception;

public class TurnException extends BaseException {

	private static final long serialVersionUID = -8255587092250565919L;
	private static final String EXCEPTION_MESSAGE = "It is not your turn in this game: Game id is ";

	public TurnException(int id) {
		super(EXCEPTION_MESSAGE + id);
	}
}
