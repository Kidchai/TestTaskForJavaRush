package com.game.validator;

import java.util.Date;

public class PlayerValidator {
    public PlayerValidator(String name, String title, Integer experience, Date birthday) {
        if (!name.equals(null)) {
            if (name.length() > 12) {
                throw new IllegalArgumentException();
            }
        }

        if (!title.equals(null)) {
            if (title.length() > 30) {
                throw new IllegalArgumentException();
            }
        }

        if (!experience.equals(null)) {
            if (experience < 0 || experience > 10_000_000) {
                throw new IllegalArgumentException();
            }
        }

        if (!birthday.equals(null)) {
            if (birthday.getTime() < 0) {
                throw new IllegalArgumentException();
            }
        }
    }
}
