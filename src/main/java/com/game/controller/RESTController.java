package com.game.controller;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

//мой класс
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/rest")
public class RESTController {

    @Autowired
    //private PlayerService playerService;
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(required = false) String title) {
        try {
            List<Player> players = new ArrayList<Player>();
            playerRepository.findAll().forEach(players::add);

            if (players.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(players, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("players/count")
//    public ResponseEntity<Player> countPlayers(@RequestParam) {
//
//    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") long id) {
        Optional<Player> playerData = playerRepository.findById(id);

        return (playerData.isPresent() ? new ResponseEntity<>(playerData.get(), HttpStatus.OK) :
                                         new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        System.out.println("метод добавления запустился");
        try {
            Player newPlayer = playerRepository
                    .save(player);
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

//    @RequestMapping("/addNewPlayer")
//    public String addNewPlayer() {
//        Player player = new Player();
//        model.addAttribute("player", player);
//
//        return "player-info";
//    }
//
//    @RequestMapping("/savePlayer")
//    public String savePlayer(@ModelAttribute("player") Player player) {
//        playerService.savePlayer(player);
//
//        return "redirect: /";
//    }
//
//    @RequestMapping("/updateInfo")
//    public String updatePlayer(@RequestParam("playerId") long id, Model model) {
//        Player player = playerService.getPlayer(id);
//        model.addAttribute("player", player);
//
//        return "player-info";
//    }
//
//    @RequestMapping("/deletePlayer")
//    public String deletePlayer(@RequestParam("playerId") long id) {
//        playerService.deletePlayer(id);
//
//        return "redirect: /";
//    }
    }

