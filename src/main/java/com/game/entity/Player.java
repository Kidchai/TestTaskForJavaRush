package com.game.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import javax.persistence.*;
import java.util.Date;
//мой класс
@Entity
@Table(name = "player")
public class Player {
    public Player() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    //@JsonSerialize(using = DateSerializer.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    private Long id;
    @Column (name = "name", nullable = false)
    private String name;
    @Column (name = "title", nullable = false)
    private String title;
    @Column (name = "race", nullable = false)
    @Enumerated(EnumType.STRING)
    private Race race;
    @Column (name = "profession", nullable = false)
    @Enumerated(EnumType.STRING)
    private Profession profession;
    @Column (name = "experience", nullable = false)
    private Integer experience;
    @Column (name = "level", nullable = false)
    private Integer level;
    @Column (name = "untilNextLevel", nullable = false)
    private Integer untilNextLevel;
    @Column (name = "birthday", nullable = false)
    private Date birthday;
    @Column (name = "banned", nullable = false)
    private Boolean banned;

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                ", birthday=" + birthday +
                ", banned=" + banned +
                '}';
    }
}
