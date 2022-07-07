package com.yozdemir.domain.dto;

import com.yozdemir.domain.Board;
import com.yozdemir.domain.Constants;
import com.yozdemir.domain.Mancala;

public class GameDTO {
	private String error;
	private String status;
	private Integer gameId;
	private Integer move;
	private Integer playerToken;
	private String turn;
	private Board board;

	public GameDTO() {
		super();
		this.board=new Board();
		this.turn=Constants.PLAYER_ONE;
	}
	public GameDTO(Integer gameId, Integer move, Integer playerToken) {
		super();
		this.gameId = gameId;
		this.move = move;
		this.playerToken = playerToken;
	}

	public static GameDTO convertFromMancalaToGameDTO(Mancala man) {
		GameDTO dto = new GameDTO();
		dto.setGameId(man.getId());
		dto.setBoard(man.getBoard());
		dto.setStatus(man.getStatus());
		dto.setTurn(man.getTurn().getName());
		return dto;

	}


	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getMove() {
		return move;
	}

	public void setMove(Integer move) {
		this.move = move;
	}

	public Integer getPlayerToken() {
		return playerToken;
	}

	public void setPlayerToken(Integer playerToken) {
		this.playerToken = playerToken;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

}
