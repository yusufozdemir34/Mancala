package com.yozdemir.exception;

public abstract class BaseException extends Exception {

	private static final long serialVersionUID = -8916566663328943637L;

	public BaseException(String message) {
		super(message);
	}

	public BaseException() {
		super();
	}
}
