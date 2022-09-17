package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
//мой интерфейс
public interface PlayerService {
    public List<Player> getAllPlayers();

    Specification<Player> selectByName(String name);
    Specification<Player> selectByTitle(String title);
    Specification<Player> selectByRace(Race race);
    Specification<Player> selectByProfession(Profession profession);
    Specification<Player> selectByBirthday(Date after, Date before);
    Specification<Player> selectByBanned(Boolean banned);
    Specification<Player> selectByExperience(Integer minExperience, Integer maxExperience);
    Specification<Player> selectByLevel(Integer minLevel, Integer maxLevel);
    Page<Player> getPlayers(Specification<Player> specification, Pageable sortedBy);
}
