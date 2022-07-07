package com.yozdemir.facade;

import org.springframework.beans.factory.annotation.Autowired;

import com.yozdemir.domain.dto.GameDTO;
import com.yozdemir.exception.BaseException;
import com.yozdemir.exception.NoSuchGameFoundException;
import com.yozdemir.service.GameApplicationService;

/**
 * 
 * Objects and data structures 
 * Hide internal structure. 
 * Prefer data structures.
 * Avoid hybrids structures (half object and half data). 
 * Should be small. 
 * Do one thing.
 * Small number of instance variables. 
 * Base class should know nothing about their derivatives. 
 * Better to have many functions than to pass some code into a function to select a behavior. 
 * Prefer non-static methods to static methods.
 */
@org.springframework.stereotype.Service
public class FacadeImpl implements Facade {

	@Autowired
	GameApplicationService gameService;

	@Override
	public GameDTO findGame(Integer manId) throws NoSuchGameFoundException {

		return gameService.createGameByGameId(manId);
	}

	@Override
	public GameDTO createGame() {
		GameDTO dto = GameDTO.convertFromMancalaToGameDTO(gameService.createMancala());

		return dto;
	}

	@Override
	public GameDTO play(Integer id, Integer move, String player) throws BaseException {

		return GameDTO.convertFromMancalaToGameDTO(gameService.playOneTurn(id, move, player));
	}

	@Override
	public GameDTO joinAGame() throws BaseException {
		return gameService.joinAGame();
	}

	@Override
	public boolean isGameOver(Integer gameId) throws BaseException {

		return gameService.isGameOver(gameId);
	}

	@Override
	public String getWinner(Integer gameId) throws BaseException {
		// TODO Auto-generated method stub
		return gameService.getWinner(gameId);
	}

}
