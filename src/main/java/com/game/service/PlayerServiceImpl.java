package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        playerList.addAll((List<Player>)playerRepository.findAll());
        return playerList;
    }
//
//    @Override
//    @Transactional
//    public List<Player> getAllPlayers() {
//        return playerDAO.getAllPlayers();
//    }

//    @Override
//    @Transactional
//    public void savePlayer(Player player) {
//        playerDAO.savePlayer(player);
//    }
//
//    @Override
//    @Transactional
//    public Player getPlayer(long id) {
//        return playerDAO.getPlayer(id);
//    }
//
//    @Override
//    @Transactional
//    public void deletePlayer(long id) {
//        playerDAO.deletePlayer(id);
//    }
}
