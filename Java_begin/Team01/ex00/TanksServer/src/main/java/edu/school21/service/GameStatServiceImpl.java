package edu.school21.service;

import edu.school21.dao.GameStatDAO;
import edu.school21.entity.GameStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameStatServiceImpl implements GameStatService {
    private final GameStatDAO gameStatDAO;


    @Override
    public void createClient(GameStat stat) {
        gameStatDAO.save(stat);
    }

    @Override
    public void addShot(GameStat stat) {
        stat.setShot(stat.getShot() + 1);
        gameStatDAO.save(stat);
    }

    @Override
    public void addHit(GameStat stat) {
        stat.setHit(stat.getHit() + 1);
        gameStatDAO.save(stat);
    }

    @Override
    public String getStatistics(int numberClient) {
        var stat = gameStatDAO.findByNumberClient(numberClient);
        var shot = stat.getShot();
        var hit = stat.getHit();

        numberClient = numberClient == 1 ? 2 : 1;
        var stat2 = gameStatDAO.findByNumberClient(numberClient);
        var shot2 = stat2.getShot();
        var hit2 = stat2.getHit();

        return "stat: %s : %s : %s : %s : %s : %s".formatted(shot, hit, shot - hit, shot2, hit2, shot2 - hit2);
    }
}
