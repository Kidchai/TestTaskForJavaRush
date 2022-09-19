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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//мой класс
@Service
public class PlayerServiceImpl implements PlayerService {

    private List<Player> playerList = new ArrayList<>();

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    @Transactional
    public List<Player> getAllPlayers(){
        playerList.addAll(playerRepository.findAll());
        return playerList;
    }

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
            return criteriaBuilder.like(root.get("race"), "%" + race + "%");
        };
    }

    @Override
    public Specification<Player> selectByProfession(Profession profession) {
        return (root, query, criteriaBuilder) -> {
            if (profession == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("profession"), "%" + profession + "%");
        };
    }

    @Override
    public Specification<Player> selectByBirthday(Date after, Date before) {
        return (root, query, criteriaBuilder) -> {
            if (after == null && before == null) {
                return null;
            }

            if (after == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), before);
            }

            if (before == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), after);
            }
//                Calendar beforeCalendar = new GregorianCalendar();
//                beforeCalendar.setTime(before);
//                beforeCalendar.set(Calendar.HOUR, 0);
//                beforeCalendar.add(Calendar.MILLISECOND, -1);
//
//                Date tempBefore = beforeCalendar.getTime();

            return criteriaBuilder.between(root.get("birthday"), after, before);
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
    public Integer calculateLevel(Integer exp) {
        double result = (Math.sqrt(2500 + 200 * exp) - 50) / 100;
        return (int) result;
    }

    @Override
    public Integer calculateExpUntilNextLevel(Integer exp, Integer lvl) {
        return 50 * (lvl + 1) * (lvl + 2) - 2;
    }


}
