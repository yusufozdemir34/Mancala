package com.yozdemir.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yozdemir.domain.Player;

/**
 * @author yusuf ozdemir 
 * Spring Data JPA is Template Pattern *
 */
public interface PlayerRepository extends JpaRepository<Player, Integer> {

}
