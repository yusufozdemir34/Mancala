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
import com.yozdemir.domain.Mancala;
import com.yozdemir.repository.MancalaRepository;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { Application.class, Mancala.class })
public class MancalaRepoTest {
	private final static Logger logger = LoggerFactory.getLogger(MancalaRepoTest.class);
	@Autowired
	MancalaRepository repo;
	public static Mancala game = new Mancala();

	@Before
	public void setup() {
		logger.info("MancalaRepoTest is running: ");
	}

	@Test
	public void testCreateMancala() {
		repo.save(game);
	}
	@Test
	public void testdeleteMancala() {
		repo.delete(game);
	}
}
