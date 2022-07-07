package com.yozdemir.exception;

public class PitEmptyException extends BaseException {

	private static final long serialVersionUID = -881297777847530526L;
	private static final String EXCEPTION_MESSAGE = " Selected pit is empty";

	public PitEmptyException(int id) {
		super("game id:" + id + EXCEPTION_MESSAGE);
	}
}
