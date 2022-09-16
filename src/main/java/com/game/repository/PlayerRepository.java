package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//мой интерфейс
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}

//@Repository
//public interface PlayerRepository extends CrudRepository<Player, Long> {
//
//}