package com.yozdemir.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.yozdemir.app.Application;
import com.yozdemir.domain.Constants;
import com.yozdemir.domain.Mancala;
import com.yozdemir.domain.dto.GameDTO;
import com.yozdemir.exception.BaseException;
import com.yozdemir.repository.MancalaRepository;
import com.yozdemir.service.GameApplicationService;
import com.yozdemir.test.domain.MancalaTest;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { Application.class, GameApplicationService.class })
public class GameApplicationServiceTest {
	private final static Logger logger = LoggerFactory.getLogger(GameApplicationServiceTest.class);
	@Autowired
	GameApplicationService gameService;
	@Autowired
	private MancalaRepository repository;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		logger.info(" MancalaServiceTest is starting");
	}

	@Test
	public void testListAllGames() {
		gameService.createMancala();

		List<Mancala> gameList = gameService.listAllGames();
		gameList.forEach(n -> System.out.println(n.toString()));
		Assert.assertNotNull(gameList);
		Assert.assertFalse(gameList.isEmpty());
	}

	@Test
	@DisplayName("Creating a game at the db.")
	public void testCreateMancala() {
		Mancala game = gameService.createMancala();
		checkNotNull(game);
		checkEquality(game);
		logger.info(game.toString());
	}

	@Test
	public void testJoinAGame() throws BaseException {
		Mancala game = gameService.createMancala();
		GameDTO dto = gameService.joinAGame();
		checkNotNull(game);
		Assert.assertEquals((Integer) 1, dto.getGameId());

	}

	private void checkNotNull(Mancala game) {
		Assert.assertNotNull(game);
		Assert.assertNotNull(game.getId());
		Assert.assertNotNull(game.getBoard());
		Assert.assertNotNull(game.getStartedTime());
	}

	private void checkEquality(Mancala game) {

		Assert.assertEquals(Constants.PLAYER_ONE, game.getPlayer1().getName());
		Assert.assertEquals(Constants.PLAYER_TWO, game.getPlayer2().getName());

	}

	@Test
	public void testFindMancala() {
		Mancala man1 = gameService.createMancala();
		Mancala man2 = repository.findById(man1.getId()).get();
		checkNotNull(man2);
		checkEquality(man2);
		Assert.assertEquals(man1.getId(), man2.getId());
	}

	@Test
	public void playOnce() throws BaseException {
		Mancala controlGame = gameService.createMancala();
		controlGame.setStatus(Constants.GAME_STARTED);
		controlGame = gameService.updateGame(controlGame);
		Integer gameId = controlGame.getId();
		Integer move = 0;
		Mancala game = gameService.playOneTurn(gameId, move, Constants.PLAYER_ONE);
		checkNotNull(game);
		Assert.assertEquals(controlGame.getId(), game.getId());
		Assert.assertEquals((Integer) 0, game.getBoard().getBoard()[0]);
	}

	@Test
	public void terminateGame() throws BaseException {
		Mancala controlGame = gameService.createMancala();
		gameService.terminateGame(controlGame.getId());
		controlGame = repository.findById(controlGame.getId()).get();
		Assert.assertNotNull(controlGame.getFinishedTime());
	}

	@Test
	public void TestIsGameOver() throws BaseException {
		Mancala controlGame = gameService.createMancala();
		controlGame.setBoard(new MancalaTest().finishedBoard);
		controlGame = gameService.updateGame(controlGame);
		Assert.assertEquals(true, gameService.isGameOver(controlGame.getId()));
	}

}
