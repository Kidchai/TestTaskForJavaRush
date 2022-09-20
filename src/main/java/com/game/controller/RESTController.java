package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import com.game.validator.PlayerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/rest")
public class RESTController {
    private PlayerService playerService;

    @Autowired
    public void playerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "title", required = false) String title,
                                                      @RequestParam(value = "race", required = false) Race race,
                                                      @RequestParam(value = "profession", required = false) Profession profession,
                                                      @RequestParam(value = "after", required = false) Long after,
                                                      @RequestParam(value = "before", required = false) Long before,
                                                      @RequestParam(value = "banned", required = false) Boolean banned,
                                                      @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                      @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                      @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                      @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                      @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                                      @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        try {
            Pageable sortedBy = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

            Specification<Player> specification = Specification.where(playerService.selectByName(name)
                    .and(playerService.selectByTitle(title))
                    .and(playerService.selectByRace(race))
                    .and(playerService.selectByProfession(profession))
                    .and(playerService.selectByBirthday(after, before))
                    .and(playerService.selectByBanned(banned))
                    .and(playerService.selectByExperience(minExperience, maxExperience))
                    .and(playerService.selectByLevel(minLevel, maxLevel)));

            Page<Player> pages = playerService.getPlayers(specification, sortedBy);

            return new ResponseEntity<>(pages.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> countPlayers(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "title", required = false) String title,
                                                @RequestParam(value = "race", required = false) Race race,
                                                @RequestParam(value = "profession", required = false) Profession profession,
                                                @RequestParam(value = "after", required = false) Long after,
                                                @RequestParam(value = "before", required = false) Long before,
                                                @RequestParam(value = "banned", required = false) Boolean banned,
                                                @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {
        Specification<Player> specification = Specification.where(playerService.selectByName(name)
                .and(playerService.selectByTitle(title))
                .and(playerService.selectByRace(race))
                .and(playerService.selectByProfession(profession))
                .and(playerService.selectByBirthday(after, before))
                .and(playerService.selectByBanned(banned))
                .and(playerService.selectByExperience(minExperience, maxExperience))
                .and(playerService.selectByLevel(minLevel, maxLevel)));

        return new ResponseEntity<>(playerService.countPlayers(specification), HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") long id) {
        Optional<Player> playerData = playerRepository.findById(id);
        PlayerValidator playerValidator = new PlayerValidator();

        if (playerValidator.isIDIncorrect(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return (playerData.isPresent() ? new ResponseEntity<>(playerData.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {

        if (player.getName() == null ||
                player.getTitle() == null ||
                player.getRace() == null ||
                player.getProfession() == null ||
                player.getBirthday() == null ||
                player.getExperience() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        PlayerValidator validator = new PlayerValidator();
        if (validator.isRequestIncorrect(player)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Integer level = playerService.calculateLevel(player.getExperience());
            player.setLevel(level);
            Integer expUntilNextLevel = playerService.calculateExpUntilNextLevel(player.getExperience(), level);
            player.setUntilNextLevel(expUntilNextLevel);

            Player newPlayer = playerRepository.save(player);

            return new ResponseEntity<>(newPlayer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") long id) {
        Optional<Player> playerData = playerRepository.findById(id);
        PlayerValidator playerValidator = new PlayerValidator();

        if (playerValidator.isIDIncorrect(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!playerData.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        playerRepository.deleteById(id);

        return new ResponseEntity<>(playerData.get(), HttpStatus.OK);
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") Long id, @RequestBody Player player) {

        PlayerValidator playerValidator = new PlayerValidator();


        Optional<Player> playerData = playerRepository.findById(id);

        if (playerValidator.isRequestEmpty(player)) {
            return new ResponseEntity<>(playerData.get(), HttpStatus.OK);
        }

        if (playerValidator.isIdIncorrect(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!playerData.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (playerValidator.isRequestIncorrect(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Player updatedPlayer;
        try {
            updatedPlayer = playerData.get();

            if (player.getName() != null) {
                updatedPlayer.setName(player.getName());
            }

            if (player.getTitle() != null) {
                updatedPlayer.setTitle(player.getTitle());
            }

            if (player.getRace() != null) {
                updatedPlayer.setRace(player.getRace());
            }

            if (player.getProfession() != null) {
                updatedPlayer.setProfession(player.getProfession());
            }

            if (player.getExperience() != null) {
                updatedPlayer.setExperience(player.getExperience());
                Integer level = playerService.calculateLevel(player.getExperience());
                updatedPlayer.setLevel(level);
                Integer expUntilNextLevel = playerService.calculateExpUntilNextLevel(player.getExperience(), level);
                updatedPlayer.setUntilNextLevel(expUntilNextLevel);
            }

            if (player.getBirthday() != null) {
                updatedPlayer.setBirthday(player.getBirthday());
            }

            if (player.getBanned() != null) {
                updatedPlayer.setBanned(player.getBanned());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(playerRepository.save(updatedPlayer), HttpStatus.OK);
    }
}

