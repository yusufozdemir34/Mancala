package com.yozdemir.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yozdemir.config.ApplicationConstants;
import com.yozdemir.domain.Constants;
import com.yozdemir.domain.Mancala;
import com.yozdemir.domain.dto.GameDTO;
import com.yozdemir.exception.BaseException;
import com.yozdemir.exception.NoSuchGameFoundException;
import com.yozdemir.repository.MancalaRepository;

/**
 * Source code structure Separate concepts vertically. Related code should
 * appear vertically dense. Declare variables close to their usage. Dependent
 * functions should be close. Similar functions should be close. Place functions
 * in the downward direction. Keep lines short. Don't use horizontal alignment.
 * Use white space to associate related things and disassociate weakly related.
 * Don't break indentation.
 */
@Service
@Transactional(ApplicationConstants.TRANSACTION_MANAGER)
public class GameApplicationService {
	private final Logger logger = LoggerFactory.getLogger(GameApplicationService.class);
	@Autowired
	private MancalaRepository repository;

	public GameDTO createGameByGameId(Integer manId) throws NoSuchGameFoundException {
		return GameDTO.convertFromMancalaToGameDTO(repository.findById(manId).get());
	}

	public List<Mancala> listAllGames() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Mancala createMancala() {
		Mancala game = new Mancala();
		game = repository.save(game);

		return game;
	}

	public Mancala playOneTurn(Integer id, Integer move, String player) throws BaseException {
		Mancala mancala = repository.findById(id).get();
		mancala.playOneTurn(move, player);
		mancala = updateGame(mancala);
		return mancala;
	}

	public GameDTO joinAGame() throws BaseException {
		Mancala man2 = repository.findOne((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Constants.MANCALA_STATUS), Constants.PENDING_OPPONENT)).get();
		man2.setStatus(Constants.GAME_STARTED);
		logger.info(man2.toString());

		man2 = repository.save(man2);
		return GameDTO.convertFromMancalaToGameDTO(man2);

	}

	public void terminateGame(Integer gameId) {
		Mancala game = repository.findById(gameId).get();
		game.setFinishedTime(new Date());
		game = repository.save(game);
	}

	public boolean isGameOver(Integer gameId) throws NoSuchGameFoundException {
		Mancala man = repository.findById(gameId).get();

		return man.isOver();
	}

	@Transactional
	public Mancala updateGame(Mancala game) {
		return repository.save(game);
	}

	public String getWinner(Integer gameId) throws BaseException {
		logger.info("game id: " + gameId);
		return repository.findById(gameId).get().getWinner().getName();
	}

}