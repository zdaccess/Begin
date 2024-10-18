package edu.school21.dao;

import edu.school21.entity.GameStat;

public interface GameStatDAO {
    void save(GameStat entity);
    GameStat findByNumberClient(int numberClient);
}
