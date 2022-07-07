package com.yozdemir.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.yozdemir.exception.BaseException;
import com.yozdemir.exception.GameOverException;
import com.yozdemir.exception.NoOppononentInGame;
import com.yozdemir.exception.NoSuchGameFoundException;
import com.yozdemir.exception.PitEmptyException;
import com.yozdemir.exception.TurnException;
import com.yozdemir.exception.WrongMoveException;

/**
 * Functions rules
 * Small.
 * Do one thing. 
 * Use descriptive names. 
 * Prefer fewer arguments.
 * Have no side effects. 
 * Don't use flag arguments. 
 * Split method into several independent methods that can be called from the client without the flag.
 */
@Entity
@Table(name = "Mancala")
public class Mancala {
	@Transient
	private static final Logger logger = LoggerFactory.getLogger(Mancala.class);
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = false)
	private Board board;
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = false)
	private Player player1;
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = false)
	private Player player2;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Player turn;
	private String status;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startedTime;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date finishedTime;

	public Mancala() {
		super();
		this.board = new Board();
		this.player1 = new Player(1, Constants.PLAYER_ONE);
		this.player2 = new Player(2, Constants.PLAYER_TWO);
		this.turn = player1;
		this.status = Constants.PENDING_OPPONENT;
		this.startedTime = new Date();

	}
	@Transient
	public static Mancala join(List<Mancala> list) throws BaseException {

		if (list.size() < 1) {
			throw new NoSuchGameFoundException();
		}
		Mancala man = null;
		for (Mancala mancala : list) {
			if (mancala.getStatus().equals(Constants.PENDING_OPPONENT)) {
				man = mancala;
				break;
			}
		}
		if (man.getId() == null) {
			throw new NoOppononentInGame();
		}
		man.setStatus(Constants.GAME_STARTED);
		return man;
	}
	@Transient
	public void playOneTurn(Integer move, String player) throws BaseException {
		boolean turn = false;
		checkValidations(move, player);
		turn = markBoard(move);
		if (!turn) {
			switchTurn();
			logger.info("Turn changed to " + getTurn().getName());
		}
	}

	@Transient
	public void finishGame() {
		setFinishedTime(new Date());
		setStatus(Constants.GAME_FINISHED);
	}

	@Transient
	public boolean isGameFinished() {
		if (this.finishedTime == null) {
			return false;
		}
		return true;
	}

	@Transient
	public boolean isPitEmpty(Integer selectedPit) {
		if (this.getBoard().getBoard()[selectedPit] == Constants.EMPTY_PIT) {
			return true;
		}
		return false;
	}

	@Transient
	public boolean isItYourTurn(String player) {
		if (this.turn.getName() == player) {
			return true;
		}
		return false;
	}

	@Transient
	public boolean isItYourPit(String player, int selectedPit) {
		if ((player.equalsIgnoreCase(Constants.PLAYER_ONE) && selectedPit < Constants.FIRST_PLAYER_BIG_PIT_INDEX)
				|| (player.equalsIgnoreCase(Constants.PLAYER_TWO) && selectedPit > Constants.FIRST_PLAYER_BIG_PIT_INDEX
						|| selectedPit < Constants.SECOND_PLAYER_BIG_PIT_INDEX)) {
			return true;
		}

		return false;
	}

	@Transient
	public void reset() {
		for (int i = 0; i < board.getBoard().length; i++) {
			board.getBoard()[i] = Constants.STARTING_AMOUNT;
		}
		board.getBoard()[Constants.FIRST_PLAYER_BIG_PIT_INDEX] = Constants.INITIAL_STONES_ON_BIGPIT;
		board.getBoard()[Constants.SECOND_PLAYER_BIG_PIT_INDEX] = Constants.INITIAL_STONES_ON_BIGPIT;

		turn = player1;
	}

	@Transient
	public boolean isOver() {
		return sumStonesInPits(player1) == Constants.EMPTY_PIT || sumStonesInPits(player2) == Constants.EMPTY_PIT;
	}

	@Transient
	public Player getWinner() {
		Player winner = null;
		if (isOver()) {
			calculateScores();
			winner = findChampion();
			resetUserPits();

		}
		return winner;
	}

	@Transient
	private void calculateScores() {
		board.getBoard()[Constants.FIRST_PLAYER_BIG_PIT_INDEX] += sumStonesInPits(player1);
		board.getBoard()[Constants.SECOND_PLAYER_BIG_PIT_INDEX] += sumStonesInPits(player2);
	}

	@Transient
	private Player findChampion() {
		Player winner = null;
		int totalOne = board.getBoard()[Constants.FIRST_PLAYER_BIG_PIT_INDEX];
		int totalTwo = board.getBoard()[Constants.SECOND_PLAYER_BIG_PIT_INDEX];
		if (totalOne > totalTwo) {
			winner = player1;
		} else if (totalOne < totalTwo) {
			winner = player2;
		}
		return winner;
	}

	@Transient
	private void resetUserPits() {
		for (int i = 0; i < board.getBoard().length; i++) {
			if (i != Constants.FIRST_PLAYER_BIG_PIT_INDEX && i != Constants.SECOND_PLAYER_BIG_PIT_INDEX) {
				board.getBoard()[i] = Constants.EMPTY_PIT;
			}
		}
	}

	@Transient
	public int sumStonesInPits(Player m) {
		int sum = Constants.EMPTY_PIT;		
		int skipPit = getSkipPit(m);

		int start = (skipPit + 1) % board.getBoard().length;
		for (int i = start; i < start + (board.getBoard().length - 1) / 2; i++) {
			sum += board.getBoard()[i];
		}
		return sum;
	}

	@Transient
	public String whoIsTurnPlayer(Player m) {
		if (m.getName() == Constants.PLAYER_ONE) {
			return Constants.PLAYER_ONE;
		} else
			return Constants.PLAYER_TWO;
	}

	@Transient
	public Integer getCollectionPit(Player m) {
		if (m.getName() == Constants.PLAYER_ONE) {
			return Constants.FIRST_PLAYER_BIG_PIT_INDEX;
		} else
			return Constants.SECOND_PLAYER_BIG_PIT_INDEX;
	}

	@Transient
	public Integer getSkipPit(Player m) {
		if (m.getName() == Constants.PLAYER_ONE) {
			return Constants.SECOND_PLAYER_BIG_PIT_INDEX;
		} else
			return Constants.FIRST_PLAYER_BIG_PIT_INDEX;
	}

	@Transient
	public boolean isOpponentSkipPit(Integer selectedPit) {

		if (getSkipPit(turn) == selectedPit) {
			return true;
		}
		return false;
	}

	@Transient
	public boolean isLastPitEmpty(Integer selectedPit) {
		//TODO  Avoid negative conditionals. convert to positive attitude
		if(!isItYourPit(turn.getName(),selectedPit)) {
			return false;
		}
		if (selectedPit != getCollectionPit(turn) && board.getBoard()[selectedPit] == (Constants.EMPTY_PIT + 1)
				&& board.getBoard()[board.getOpposite(selectedPit)] != Constants.EMPTY_PIT) {
			return true;
		}
		return false;
	}

	@Transient
	public boolean markBoard(Integer pos) {
		int handAmount = board.getBoard()[pos];
		boolean returning = false;
		board.getBoard()[pos] = Constants.EMPTY_PIT;

		while (handAmount > Constants.EMPTY_PIT) {
			pos = (pos + 1) % board.getBoard().length;
			handAmount--;

			if (isOpponentSkipPit(pos)) {
				pos = (pos + 1) % board.getBoard().length;
			}
			board.getBoard()[pos]++;
		}
		boolean taken = false;

		if (isLastPitEmpty(pos)) {
			board.getBoard()[getCollectionPit(turn)] += board.getBoard()[pos]
					+ board.getBoard()[board.getOpposite(pos)];
			board.getBoard()[pos] = Constants.EMPTY_PIT;
			board.getBoard()[board.getOpposite(pos)] = Constants.EMPTY_PIT;
			taken = true;
		}
		board.printBoard();
		if (taken) {
			System.out.println("You took the pieces from both your own pit and your opponent's pit!");
		} else if (!isOver() && pos == getCollectionPit(turn)) {
			System.out.println("You can play again " + turn.getName() + " because  you landed in the Kalah.");
			return true;
		}
		return returning;
	}

	@Transient
	public void checkValidations(Integer selectedPit, String player) throws BaseException {

		if (isGameFinished()) {
			throw new GameOverException(this.getId());
		}
		if (getStatus() == Constants.PENDING_OPPONENT) {
			throw new NoOppononentInGame(this.getId());
		}

		if (!isItYourTurn(player)) {

			throw new TurnException(this.getId());

		}
		if (isPitEmpty(selectedPit)) {
			throw new PitEmptyException(this.getId());
		}

		if (!isItYourPit(player, selectedPit)) {
			throw new WrongMoveException(this.getId());
		}
	}

	@Transient
	public void switchTurn() {
		if (getTurn().getId().equals(getPlayer1().getId())) {
			setTurn(getPlayer2());

		} else {
			setTurn(getPlayer1());
		}
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder("id is:" + id);
		build.append("\n status: " + status);
		build.append("\n starttime: " + startedTime);
		build.append("\n finishedTime: " + finishedTime);
		build.append("\n " + board.toString());
		build.append("\n " + player1.toString());
		build.append("\n " + player2.toString());
		build.append("\n " + turn.toString() + "\n\n");

		return build.toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getTurn() {
		return turn;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(Date startedTime) {
		this.startedTime = startedTime;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

}