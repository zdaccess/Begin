package edu.school21.service;

import edu.school21.entity.GameStat;

public interface GameStatService {
    void createClient(GameStat stat);
    void addShot(GameStat stat);
    void addHit(GameStat stat);
    String getStatistics(int numberClient);
}
