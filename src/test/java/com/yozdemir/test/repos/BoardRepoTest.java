package com.yozdemir.test.repos;

import org.junit.Before;
import org.junit.Test;
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
import com.yozdemir.domain.Board;
import com.yozdemir.repository.BoardRepository;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { Application.class, Board.class })
public class BoardRepoTest {
	private final static Logger logger = LoggerFactory.getLogger(BoardRepoTest.class);
	@Autowired
	BoardRepository repo;
	public static Board board = new Board();

	@Before
	public void setup() {

	}

	@Test
	public void testCreateBoard() {
		repo.save(board);
	}

	@Test
	public void FindBoardAndPrint() {
		Integer id = 1;
		Board board = repo.findById(id).get();
		logger.info(board.toString());
	}
}
