package com.yozdemir.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yozdemir.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}