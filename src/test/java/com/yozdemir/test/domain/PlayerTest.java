package com.yozdemir.test.domain;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yozdemir.domain.Constants;
import com.yozdemir.domain.Player;

public class PlayerTest {
	private final static Logger logger = LoggerFactory.getLogger(PlayerTest.class);
	public static Player player1 = new Player(1, Constants.PLAYER_ONE);
	public static Player player2 = new Player(2, Constants.PLAYER_TWO);

	@Test
	public void testCreatePlayer() {

		Assert.assertNotNull(player1);
		Assert.assertNotNull(player1.getId());
		Assert.assertNotNull(player1.getName());

	}

	@Test
	public void testToString() {
		logger.info("test :" + player1.toString());
	}
}
