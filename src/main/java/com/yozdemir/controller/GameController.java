package com.yozdemir.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yozdemir.domain.Constants;
import com.yozdemir.domain.dto.GameDTO;
import com.yozdemir.exception.BaseException;
import com.yozdemir.exception.NoSuchGameFoundException;
import com.yozdemir.facade.Facade;

@Controller
public class GameController {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	@Autowired
	Facade facade;

	@GetMapping("/")
	public String renderGame(HttpServletRequest request, Map<String, Object> model) {
		final HttpSession session = request.getSession(true);
		GameDTO game = getGameData(session);

		try {
			if (game != null && game.getGameId() != null && facade.isGameOver(game.getGameId())) {
				String winResult = facade.getWinner(game.getGameId());
				model.put(Constants.GAME_MESSAGE_MODEL, winResult);

			}
		} catch (BaseException e) {
			model.put(Constants.ERROR_MESSAGE, e.getLocalizedMessage());
			logger.info(e.getLocalizedMessage());
		}
		model.put(Constants.PIT_STONE_MODEL, game.getBoard().getBoard());
		model.put(Constants.CURRENT_PLAYER_MODEL, game.getTurn());
		model.put(Constants.ERROR_MESSAGE, session.getAttribute(Constants.ERROR_MESSAGE) != null ? session.getAttribute(Constants.ERROR_MESSAGE) : "");
		model.put(Constants.GAME_ID, session.getAttribute(Constants.GAME_ID) != null ? session.getAttribute(Constants.GAME_ID) : "");
		session.setAttribute(Constants.CURRENT_PLAYER_MODEL, game.getTurn());

		if (session != null && session.getAttribute(Constants.YOU_ARE) != null) {
			model.put(Constants.YOU_ARE, session.getAttribute(Constants.YOU_ARE));
		} else {
			model.put(Constants.YOU_ARE, game.getTurn());
		}

		return Constants.GAME_PANEL;
	}

	@GetMapping("/input/{move}")
	public String handleUserMove(HttpServletRequest request, @PathVariable String move) {
		final HttpSession session = request.getSession(true);
		Integer position = Integer.parseInt(move);
		String player = (String) session.getAttribute(Constants.YOU_ARE);
		GameDTO game = getGameData(session);
		if (isWrongMove(game, player, position)) {
			return "redirect:/";
		}

		try {
			facade.play(game.getGameId(), position, player);

		} catch (BaseException e) {
			logger.info(e.getLocalizedMessage());
			session.setAttribute(Constants.ERROR_MESSAGE, e.getLocalizedMessage());
		} catch (Exception e) {
			logger.info("handleUserMove general exception is" + e.getLocalizedMessage());

		}
		session.setAttribute(Constants.CURRENT_PLAYER_MODEL, game.getTurn());
		session.setAttribute(Constants.GAME_ID, game.getGameId());
		return "redirect:/";
	}

	@GetMapping("/create")
	public String createGame(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		GameDTO dto = null;

		try {
			dto = facade.createGame();
		} catch (Exception e) {
			logger.info(e.getLocalizedMessage());
			session.setAttribute(Constants.ERROR_MESSAGE, e.getLocalizedMessage());
		}

		logger.info(" id :" + dto.getGameId());
		setSession(session, dto);
		session.setAttribute(Constants.YOU_ARE, Constants.PLAYER_ONE);

		return "redirect:/";
	}

	@GetMapping("/joingame")
	public String joinGame(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		GameDTO man;
		try {
			logger.info("joinGame starts: ");
			man = facade.joinAGame();

			setSession(session, man);
			session.setAttribute(Constants.YOU_ARE, Constants.PLAYER_TWO);
		} catch (BaseException e) {
			logger.info(e.getLocalizedMessage());

		} catch (Exception e) {
			logger.info("general exception is" + e.getLocalizedMessage());

		}
		logger.info("joinGame finishes: ");
		return "redirect:/";
	}

	private GameDTO getGameData(HttpSession session) {
		Object attribute = session.getAttribute(Constants.GAME_ID);
		GameDTO man = new GameDTO();
		if (attribute == null) {
			logger.debug("Starting new game");
			return man;
		}
		Integer id = (Integer) attribute;

		try {
			man = facade.findGame(id);
			setSession(session, man);
		} catch (NoSuchGameFoundException e) {
			logger.info(e.getLocalizedMessage());
			session.setAttribute(Constants.ERROR_MESSAGE, e.getLocalizedMessage());
		} catch (Exception e) {
			logger.info("getGameData da genel bir hata aldik." + e.getLocalizedMessage());

		}
		return man;
	}

	private void setSession(HttpSession session, GameDTO dto) {
		StringBuilder build = new StringBuilder();
		build.append("game id: " + dto.getGameId());

		session.setAttribute(Constants.GAME_ID, dto.getGameId());
		session.setAttribute(Constants.GAME_STATUS, dto.getStatus());
		session.setAttribute(Constants.PIT_STONE_MODEL, dto.getBoard().getBoard());
		session.setAttribute(Constants.CURRENT_PLAYER_MODEL, dto.getTurn());
	}

	private boolean isWrongMove(GameDTO game, String player, Integer position) {
		boolean state = true;
		if (game == null || game.getGameId() == null) {
			return state;
		} else if (player != null && player != game.getTurn()) {
			return state;
		}
		if (player == Constants.PLAYER_ONE) {
			if (position >Constants.FIRST_PLAYER_BIG_PIT_INDEX-1) {
				return state;
			}
		} else if (position < Constants.FIRST_PLAYER_BIG_PIT_INDEX || position >Constants.SECOND_PLAYER_BIG_PIT_INDEX-1) {
			return state;
		}
		return false;
	}

}
