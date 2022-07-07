package com.yozdemir.test.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.yozdemir.app.Application;
import com.yozdemir.domain.Board;
import com.yozdemir.domain.Constants;
import com.yozdemir.domain.Mancala;
import com.yozdemir.domain.Player;
import com.yozdemir.exception.BaseException;
import com.yozdemir.exception.GameOverException;
import com.yozdemir.exception.NoOppononentInGame;
import com.yozdemir.exception.TurnException;
import com.yozdemir.exception.WrongMoveException;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { Application.class, Mancala.class })
public class MancalaTest {
	private final static Logger logger = LoggerFactory.getLogger(MancalaTest.class);
	public static Mancala game = new Mancala();
	public Board board = new Board();
	public Board finishedBoard = new Board();

	@Before
	public void setup() {
		game.setId(1);
		Integer[] finishedBoardIndex = { 0, 0, 0, 0, 0, 0, 22, 0, 8, 9, 10, 11, 0, 12 };
		Integer[] boardIndex1 = { 7, 1, 0, 3, 4, 5, 6, 0, 8, 9, 10, 11, 0, 12 };
		board.setBoard(boardIndex1);
		finishedBoard.setBoard(finishedBoardIndex);

	}

	@Test
	public void testCreateGame() {
		Mancala game = new Mancala();
		game.setId(1);
		Assert.assertNotNull(game);
		Assert.assertNotNull(game.getBoard());
		Assert.assertNotNull(game.getPlayer1());
		Assert.assertNotNull(game.getPlayer2());
		Assert.assertNotNull(game.getStatus());
		Assert.assertNotNull(game.getStartedTime());
	}

	private void testCheckValidations(Mancala game) {
		Assert.assertNotNull(game);
		Assert.assertNotNull(game.getBoard());
		Assert.assertNotNull(game.getPlayer1());
		Assert.assertNotNull(game.getPlayer2());
		Assert.assertNotNull(game.getStatus());
		Assert.assertNotNull(game.getStartedTime());
	}

	@Test
	public void testIsGameFinished() {
		logger.info("testUpdateGame is working");
		Mancala game = new Mancala();
		game.setId(1);
		testCheckValidations(game);
		game.finishGame();
		logger.info(game.toString());

		Assert.assertNotNull(game.getStatus());
		Assert.assertNotNull(game.getFinishedTime());
		Assert.assertEquals(Constants.GAME_FINISHED, game.getStatus());

	}

	@Test
	public void testReset() {
		game.setBoard(board);
		game.reset();
		Assert.assertEquals(Constants.PLAYER_ONE, game.getTurn().getName());
		for (int i = 0; i < board.getBoard().length; i++) {
			if (i == Constants.FIRST_PLAYER_BIG_PIT_INDEX || i == Constants.SECOND_PLAYER_BIG_PIT_INDEX) {
				Assert.assertEquals((Integer) Constants.INITIAL_STONES_ON_BIGPIT,
						(Integer) game.getBoard().getBoard()[i]);
			} else {
				Assert.assertEquals((Integer) Constants.STARTING_AMOUNT, (Integer) game.getBoard().getBoard()[i]);
			}
		}

	}

	@Test
	public void testWinner() {
		game.setBoard(finishedBoard);
		Player winner = game.getWinner();
		Assert.assertNotNull(winner);
		Assert.assertEquals(Constants.PLAYER_TWO, winner.getName());

	}

	@Test
	public void testIsOver() {
		game.setBoard(finishedBoard);
		Assert.assertEquals(true, game.isOver());

	}

	@Test
	public void testSum() {
		Integer sum = game.sumStonesInPits(game.getPlayer1());
		Integer oneSideTotalStones = BoardTest.totalStones / 2;
		Assert.assertEquals(oneSideTotalStones, sum);
	}

	@Test
	public void testMarkBoard() {
		game.markBoard(0);
		Integer boarIndex = game.getCollectionPit(game.getPlayer1());
		Integer test = game.getBoard().getBoard()[boarIndex];
		Assert.assertEquals((Integer) 1, test);
	}

	@Test
	public void testSwitchTurn() {
		Mancala game = new Mancala();
		game.getPlayer1().setId(1);
		game.getPlayer2().setId(2);
		String firstTurn = game.getTurn().getName();
		game.switchTurn();
		String secondTurn = game.getTurn().getName();
		Assert.assertNotEquals(firstTurn, secondTurn);
	}

	@Test
	public void checkValidation() {
		Integer selectedPit = 5;
		try {
			game.checkValidations(selectedPit, Constants.PLAYER_TWO);
		} catch (GameOverException e) {
			logger.info(e.getLocalizedMessage());
		} catch (NoOppononentInGame e) {
			logger.info(e.getLocalizedMessage());
		} catch (TurnException e) {
			logger.info(e.getLocalizedMessage());
		} catch (WrongMoveException e) {
			logger.info(e.getLocalizedMessage());
		} catch (BaseException e) {
			logger.info(e.getLocalizedMessage());
		}
	}

}
