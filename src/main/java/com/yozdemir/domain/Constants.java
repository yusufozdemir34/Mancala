package com.yozdemir.domain;

public final class Constants {
	// Game constants for backend
	public static final String PENDING_OPPONENT = "Pending Opponent";
	public static final String GAME_STARTED = "Game Started";
	public static final String GAME_FINISHED = "Game Finished";
	public static final String PLAYER_ONE = "Player One";
	public static final String PLAYER_TWO = "Player Two";
	
	//Board constants
	public static final int BOARD_SIZE = 14;
	public static final int FIRST_PLAYER_BIG_PIT_INDEX = 6;
	public static final int SECOND_PLAYER_BIG_PIT_INDEX = 13;
	public static final int STARTING_AMOUNT = 6;
	public static final int INITIAL_STONES_ON_BIGPIT = 0;
	public static final int EMPTY_PIT = 0;

	// Game constants for frontend
	public static final String GAME_ID = "gameId";
	public static final String PIT_STONE_MODEL = "pitStones";
	public static final String CURRENT_PLAYER_MODEL = "currentPlayer";
	public static final String YOU_ARE = "youAre";
	public static final String GAME_MESSAGE_MODEL = "gameMessage";
	public static final String GAME_STATUS = "gameStatus";
	public static final String MANCALA_STATUS = "status";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String GAME_PANEL = "gamePanel";

	public Constants() {

	}
}
