package com.yozdemir.exception;

public class WrongMoveException extends BaseException {

	private static final long serialVersionUID = 1L;
	private static final String EXCEPTION_MESSAGE = " wrong pit moved";

	public WrongMoveException(int id) {
		super(id + EXCEPTION_MESSAGE);
	}
}
