package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//мой класс
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/rest")
public class RESTController {

    @Autowired
    //private PlayerService playerService;
    PlayerRepository playerRepository;

    //    @GetMapping("/players")
//    public @ResponseBody List<Player> showAllPlayers(@RequestParam(value="name", required = false) String name,
//                                                       @RequestParam(value = "title", required = false) String title,
//                                                       @RequestParam(value = "race", required = false) Race race,
//                                                       @RequestParam(value = "profession", required = false) Profession profession,
//                                                       @RequestParam(value = "after", required = false) Long after,
//                                                       @RequestParam(value = "before", required = false) Long before,
//                                                       @RequestParam(value = "banned", required = false) String banned,
//                                                       @RequestParam(value = "minExperience", required = false) Integer minExperience,
//                                                       @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
//                                                       @RequestParam(value = "minLevel", required = false) Integer minLevel,
//                                                       @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
//                                                       @RequestParam(defaultValue = "0", value="pageNumber")String page,
//                                                       @RequestParam(defaultValue = "3", value = "pageSize")String pageSize,
//                                                       @RequestParam(defaultValue = "ID", value = "order")String order){
////        List<Player> resultList = playerService.getFilteredPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
////        List<Player> playerList = playerService.getSortedPlayers(resultList, page, pageSize, order);
//        List<Player> playerList = playerService.getAllPlayers();
//        return playerList;
//    }
    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Player> tutorials = new ArrayList<Player>();
            playerRepository.findAll().forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<List<Player>> showAllPlayers(@RequestParam(required = false) String title) {
//        try {
//            List<Player> players = new ArrayList<>(playerRepository.findAll());
//
//            if (players.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(players, HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }

//        return new ResponseEntity<>(HttpStatus.OK);
//        List<Player> allPlayers = playerService.getAllPlayers();
//        return allPlayers;
//    }

//    @RequestMapping("/addNewPlayer")
//    public String addNewPlayer(Model model) {
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

