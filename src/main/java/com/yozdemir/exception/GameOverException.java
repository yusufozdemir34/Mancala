package com.yozdemir.exception;

public class GameOverException extends BaseException {

	private static final long serialVersionUID = -3275916021939543946L;
	private static final String EXCEPTION_MESSAGE = " game is already finished";

	public GameOverException(int id) {
		super("game id:" + id + EXCEPTION_MESSAGE);
	}
}
