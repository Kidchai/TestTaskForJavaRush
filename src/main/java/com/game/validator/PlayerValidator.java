package com.game.validator;

import com.game.entity.Player;

import java.util.Date;

public class PlayerValidator {

    public boolean isIDIncorrect(Long id) {
        return (id < 1);
    }

    public boolean isRequestForUpdateIncorrect(Player player) {
        if (player.getId() != null) {
            if (player.getId() < 1) {
                return true;
            }
        }

        String name = player.getName();
        String title = player.getTitle();
        Integer experience = player.getExperience();
        Date birthday = player.getBirthday();
        if (name != null) {
            if (name.length() > 12) {
                return true;
            }
        }

        if (title != null) {
            if (title.length() > 30) {
                return true;
            }
        }

        if (experience != null) {
            if (experience < 0 || experience > 10_000_000) {
                return true;
            }
        }

        if (birthday != null) {
            if (birthday.getTime() < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isRequestEmpty(Player player) {
        return (player.getName() == null && player.getTitle() == null &&
                player.getRace() == null && player.getBirthday() == null);
    }
}
