package com.game.controller;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") long id) {
        Optional<Player> playerData = playerRepository.findById(id);

        return (playerData.isPresent() ? new ResponseEntity<>(playerData.get(), HttpStatus.OK) :
                                         new ResponseEntity<>(HttpStatus.NOT_FOUND));

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

