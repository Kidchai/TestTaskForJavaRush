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

//мой класс
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/rest")
public class RESTController {
    //@Autowired
    private PlayerService playerService;

    @Autowired
    public void playerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(required = false) String a,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "race", required = false) Race race,
                                              @RequestParam(value = "profession", required = false) Profession profession,
                                              @RequestParam(value = "after", required = false) Date after,
                                              @RequestParam(value = "before", required = false) Date before,
                                              @RequestParam(value = "banned", required = false) Boolean banned,
                                              @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                              @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                              @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                              @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                              @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                              @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                              @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

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
    }

//    @GetMapping("/players")
//    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(required = false) String title) {
//        try {
//            List<Player> players = new ArrayList<>();
//            playerRepository.findAll().forEach(players::add);
//
//            if (players.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(players, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("players/count")
//    public ResponseEntity<Player> countPlayers(@RequestParam) {
//
//    }

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
        System.out.println("метод добавления запустился");

        if (player.getName() == null ||
            player.getTitle() == null ||
            player.getRace() == null ||
            player.getProfession() == null ||
            player.getBirthday() == null ||
            player.getExperience() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Player newPlayer = playerRepository.save(player);
//                    .save(new Player(player.getName(),
//                    player.getTitle()));

//            Player newPlayer = playerRepository
//                    .save(new Player(player.getName(),
//                            player.getTitle(),
//                            player.getRace(),
//                            player.getProfession(),
//                            player.getExperience(),
//                            player.getLevel(),
//                            player.getUntilNextLevel(),
//                            player.getBirthday(),
//                            player.getBanned()));
            System.out.println("игрок добавлен");
            return new ResponseEntity<>(newPlayer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

//
//        if (player.getName().equals(null) ||
//            player.getName().length() > 12 ||
//            player.getTitle().length() > 30 ||
//            player.getExperience() < 0 ||
//            player.getExperience() > 10_000_000 ||
//            player.getBirthday().getTime() < 0) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(player.getBirthday());
//
//        if (calendar.get(Calendar.YEAR) < 2000 ||
//            calendar.get(Calendar.YEAR) > 3000) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        //Мы не можем создать игрока, если:
//        //- указаны не все параметры из Data Params (кроме banned);
//        //- длина значения параметра “name” или “title” превышает
//        //размер соответствующего поля в БД (12 и 30 символов);
//        //- значение параметра “name” пустая строка;
//        //- опыт находится вне заданных пределов;
//        //- “birthday”:[Long] < 0;
//        //- дата регистрации находятся вне заданных пределов.
//        //В случае всего вышеперечисленного необходимо ответить
//        //ошибкой с кодом 400
//
//
//        playerRepository.saveAndFlush(player);
//        return new ResponseEntity<>(HttpStatus.OK);
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

        try {
            playerRepository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(playerData.get(), HttpStatus.OK);
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id, @RequestBody Player player) {

        PlayerValidator playerValidator = new PlayerValidator();

        if (playerValidator.isRequestForUpdateIncorrect(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Player> playerData = playerRepository.findById(id);

        if (!playerData.isPresent()) {
            System.out.println("что-то пошло не так в методе обновления 2");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (playerValidator.isRequestEmpty(player)) {
            return new ResponseEntity<>(playerData.get(), HttpStatus.OK);
        }

        Player updatedPlayer;
        try {
            updatedPlayer = playerData.get();

            if (!player.getName().equals(null)) {
                updatedPlayer.setName(player.getName());
            }

            if (!player.getTitle().equals(null)) {
                updatedPlayer.setTitle(player.getTitle());
            }

            if (!player.getRace().equals(null)) {
                updatedPlayer.setRace(player.getRace());
            }

            if (!player.getProfession().equals(null)) {
                updatedPlayer.setProfession(player.getProfession());
            }

            if (!player.getExperience().equals(null)) {
                updatedPlayer.setExperience(player.getExperience());
            }

            if (!player.getBirthday().equals(null)) {
                updatedPlayer.setBirthday(player.getBirthday());
            }

        } catch (Exception e) {
            System.out.println("что-то пошло не так в методе обновления 3");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(playerRepository.save(updatedPlayer), HttpStatus.OK);
    }
}

