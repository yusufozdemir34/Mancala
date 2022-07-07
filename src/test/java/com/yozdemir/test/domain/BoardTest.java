package com.yozdemir.test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.yozdemir.app.Application;
import com.yozdemir.domain.Board;
import com.yozdemir.domain.Constants;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { Application.class, Board.class })
public class BoardTest {

	public static Board board = new Board();
	public static Board playedBoard = new Board();
	public static Integer totalStones = 72;

	@Before
	public void setup() {
		board.setId(1);
	}

	@Test
	public void testCreateBoardCorrectly() {
		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getId());
		Assert.assertNotNull(board.getBoard());
		Assert.assertEquals(Constants.BOARD_SIZE, board.getBoard().length);

	}

	@Test
	public void testCreateBoard() {
		board.createBoard();
		Assert.assertNotNull(board);
		Assert.assertEquals(Constants.BOARD_SIZE, board.getBoard().length);
		Assert.assertEquals((Integer)Constants.INITIAL_STONES_ON_BIGPIT, board.getBoard()[Constants.FIRST_PLAYER_BIG_PIT_INDEX]);
		Assert.assertEquals((Integer)Constants.INITIAL_STONES_ON_BIGPIT, board.getBoard()[Constants.SECOND_PLAYER_BIG_PIT_INDEX]);
		Assert.assertEquals((Integer)Constants.STARTING_AMOUNT, board.getBoard()[0]);

	}

	@Test
	public void testPrintBoard() {

		 board.printBoard();

	}

	@Test
	public void testGetOpposite() {
		Integer position = 2;
		Integer oppositePosition = 10;
		Integer testPositionOpposite = board.getOpposite(position);
		assertEquals(testPositionOpposite, oppositePosition);
	}
}
