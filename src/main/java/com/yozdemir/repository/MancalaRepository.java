
package com.yozdemir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yozdemir.domain.Mancala;

/**
 * 
 * @author yusuf ozdemir Spring Data JPA is Template Pattern
 *
 */
public interface MancalaRepository extends JpaRepository<Mancala, Integer>, JpaSpecificationExecutor<Mancala> {

}
