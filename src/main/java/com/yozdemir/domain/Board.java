package com.yozdemir.domain;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Names rules Choose descriptive and unambiguous names. 
 * Make meaningful distinction. 
 * Use pronounceable names. 
 * Use searchable names. 
 * Replace magic numbers with named constants. 
 * Avoid encodings. 
 * Don't append prefixes or type information
 */
@Entity
@Table(name = "Board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer[] boardIndex;

	@Transient
	@Override
	public String toString() {
		StringBuilder build = new StringBuilder("id is:" + id + " Board details: ");

		Arrays.stream(boardIndex).forEach(num -> build.append(num + " "));

		return build.toString();
	}

	public Board() {
		super();
		createBoard();

	}

	@Transient
	public void createBoard() {
		boardIndex = new Integer[Constants.BOARD_SIZE];
		for (int i = 0; i < boardIndex.length; i++) {
			boardIndex[i] = Constants.STARTING_AMOUNT;
		}
		boardIndex[Constants.FIRST_PLAYER_BIG_PIT_INDEX] = Constants.INITIAL_STONES_ON_BIGPIT;
		boardIndex[Constants.SECOND_PLAYER_BIG_PIT_INDEX] = Constants.INITIAL_STONES_ON_BIGPIT;
	}

	@Transient
	public Integer getOpposite(int pos) {
		System.out.println(pos + "  getopposite position");
		return boardIndex.length - 2 - pos;
	}

	@Transient
	public void printBoard() {

		System.out.println("    (1) (2) (3) (4) (5) (6) ");

		System.out.println("-------------------------------");
		System.out.print("|  ");
		for (int i = boardIndex.length - 2; i >= boardIndex.length / 2; i--) {
			System.out.print("| ");
			System.out.printf("%-2s", boardIndex[i]);
		}
		System.out.print("|  |\n|");
		System.out.printf("%-2d|-----------------------|%2d|\n", boardIndex[13], boardIndex[6]);//

		System.out.print("|  ");
		for (int i = 0; i < (boardIndex.length / 2) - 1; i++) {
			System.out.print("| ");
			System.out.printf("%-2s", boardIndex[i]);
		}
		System.out.println("|  |");
		System.out.println("-------------------------------");
		System.out.println("    (1) (2) (3) (4) (5) (6) ");
	}

	public Integer[] getBoard() {
		return boardIndex;
	}

	public void setBoard(Integer[] board) {
		this.boardIndex = board;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
