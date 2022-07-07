package com.yozdemir.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yozdemir.facade.Facade;

/**
 * 
 * @author yusuf ozdemir
 *
 *
 */
@RestController
@RequestMapping("/rest")
public class RestTestController {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
	@Autowired
	Facade facade;

	@RequestMapping(value = "/create}", method = RequestMethod.PUT)
	public ResponseEntity createGame() throws Exception {

		return new ResponseEntity(facade.createGame(), HttpStatus.OK);

	}

	@RequestMapping(value = "/join}", method = RequestMethod.PUT)
	public ResponseEntity joinGame() throws Exception {

		return new ResponseEntity(facade.joinAGame(), HttpStatus.OK);

	}

	@RequestMapping(value = "/play/{Id}/{move}/{player}", method = RequestMethod.POST)
	public ResponseEntity play(@PathVariable("Id") Integer id,@PathVariable("move") Integer move,@PathVariable("player") String player) throws Exception {
		return new ResponseEntity(facade.play(id, move, player) , HttpStatus.OK);
	}

	@RequestMapping(value = "/isGameOver/{Id}", method = RequestMethod.GET)
	public ResponseEntity isGameOver(@PathVariable("Id") Integer id) throws Exception {
		return new ResponseEntity(facade.isGameOver(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/getWinner/{Id}", method = RequestMethod.GET)
	public ResponseEntity getWinner(@PathVariable("Id") Integer id) throws Exception {
		return new ResponseEntity(facade.getWinner(id), HttpStatus.OK);
	}
}