package com.yozdemir.facade;

import com.yozdemir.domain.dto.GameDTO;
import com.yozdemir.exception.BaseException;
import com.yozdemir.exception.NoSuchGameFoundException;

/**
 * facade pattern Robert C Martin Clean Code Hide internal Structure This
 * service contains back end services.
 */
public interface Facade {
	public GameDTO findGame(Integer manId) throws NoSuchGameFoundException;

	public GameDTO createGame() ;

	public GameDTO joinAGame() throws BaseException;

	public GameDTO play(Integer id, Integer move, String player) throws BaseException;

	public boolean isGameOver(Integer gameId) throws BaseException;

	public String getWinner(Integer gameId) throws BaseException;

}
