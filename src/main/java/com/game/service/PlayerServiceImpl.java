package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Specification<Player> selectByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    @Override
    public Specification<Player> selectByTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    @Override
    public Specification<Player> selectByRace(Race race) {
        return (root, query, criteriaBuilder) -> {
            if (race == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("race"), race);
        };
    }

    @Override
    public Specification<Player> selectByProfession(Profession profession) {
        return (root, query, criteriaBuilder) -> {
            if (profession == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("profession"), profession);
        };
    }

    @Override
    public Specification<Player> selectByBirthday(Long after, Long before) {
        return (root, query, criteriaBuilder) -> {
            if (after == null && before == null) {
                return null;
            }

            if (after == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(before));
            }

            if (before == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(after));
            }
            Date dateAfter = new Date(after);
            Date dateBefore = new Date(before);
            Calendar calendarBefore = new GregorianCalendar();
            calendarBefore.setTime(dateBefore);
            calendarBefore.add(Calendar.DAY_OF_MONTH, 1);
            dateBefore.setTime(calendarBefore.getTimeInMillis());

            return criteriaBuilder.between(root.get("birthday"), dateAfter, dateBefore);
        };
    }

    @Override
    public Specification<Player> selectByBanned(Boolean banned) {
        return (root, query, criteriaBuilder) -> {
            if (banned == null) {
                return null;
            }
            if (banned) {
                return criteriaBuilder.isTrue(root.get("banned"));
            } else {
                return criteriaBuilder.isFalse(root.get("banned"));
            }
        };
    }

    @Override
    public Specification<Player> selectByExperience(Integer minExperience, Integer maxExperience) {
        return (root, query, criteriaBuilder) -> {
            if (minExperience == null && maxExperience == null) {
                return null;
            }
            if (minExperience == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience);
            }
            if (maxExperience == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience);
            }
            return criteriaBuilder.between(root.get("experience"), minExperience, maxExperience);
        };
    }

    @Override
    public Specification<Player> selectByLevel(Integer minLevel, Integer maxLevel) {
        return (root, query, criteriaBuilder) -> {
            if (minLevel == null && maxLevel == null) {
                return null;
            }
            if (minLevel == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel);
            }
            if (maxLevel == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel);
            }
            return criteriaBuilder.between(root.get("level"), minLevel, maxLevel);
        };
    }

    @Override
    public Page<Player> getPlayers(Specification<Player> specification, Pageable sortedBy) {
        return playerRepository.findAll(specification, sortedBy);
    }

    @Override
    public Integer countPlayers(Specification<Player> specification) {
        return playerRepository.findAll(specification).size();
    }

    @Override
    public Integer calculateLevel(Integer exp) {
        double result = (Math.sqrt(2500 + 200 * exp) - 50) / 100;
        return (int) result;
    }

    @Override
    public Integer calculateExpUntilNextLevel(Integer exp, Integer lvl) {
        return 50 * (lvl + 1) * (lvl + 2) - exp;
    }
}
