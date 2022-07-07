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
import com.yozdemir.domain.Constants;
import com.yozdemir.domain.Player;
import com.yozdemir.repository.PlayerRepository;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { Application.class, Player.class })
public class PlayerRepoTest {
	private final static Logger logger = LoggerFactory.getLogger(PlayerRepoTest.class);
	@Autowired
	PlayerRepository repo;
	public static Player player1 = new Player(1, Constants.PLAYER_ONE);
	public static Player player2 = new Player(2, Constants.PLAYER_TWO);

	@Before
	public void setup() {
		logger.info("MancalaRepoTest is running: ");
	}

	@Test
	public void testCreatePlayer() {
		repo.save(player1);
	}

	@Test
	public void testDeletePlayer() {
		repo.delete(player1);
	}
}
