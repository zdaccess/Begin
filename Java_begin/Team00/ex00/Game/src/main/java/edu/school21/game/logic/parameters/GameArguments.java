package edu.school21.game.logic.parameters;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import edu.school21.game.exceptions.IllegalParametersException;
import edu.school21.game.logic.mode.GameMode;
import edu.school21.game.logic.parameters.validators.ValidateInteger;
import edu.school21.game.logic.parameters.validators.ValidateMode;

@Parameters(separators = "=")
public class GameArguments {
    @Parameter(names = { "--enemiesCount", "-e" }, validateWith = {
            ValidateInteger.class }, required = true)
    private Integer enemiesCount = null;

    @Parameter(names = { "--wallsCount", "-w" }, validateWith = {
            ValidateInteger.class }, required = true)
    private Integer wallsCount = null;

    @Parameter(names = { "--size", "-s" }, validateWith = {
            ValidateInteger.class }, required = true)
    private Integer mapSize = null;

    @Parameter(names = { "--profile", "-p" }, validateWith = {
            ValidateMode.class }, required = true)
    private String profile = null;

    public GameArguments(String[] args) throws IllegalParametersException {
        JCommander.newBuilder().addObject(this).build().parse(args);
        if (profile.equals("development")) {
            GameMode.setDevelop();
        }
        validate();
    }

    public Integer getEnemiesCount() {
        return this.enemiesCount;
    }

    public Integer getWallsCount() {
        return this.wallsCount;
    }

    public Integer getMapSize() {
        return this.mapSize;
    }

    public String getMode() {
        return this.profile;
    }

    private void validate() throws IllegalParametersException {
        int size = mapSize * mapSize;
        int player = 1;
        int goal = 1;
        int allElements = player + goal + enemiesCount + wallsCount;
        int emptySpace = size - allElements;

        if (emptySpace < size / 2) {
            throw new IllegalParametersException("Error: Map is too populated");
        }
    }
}
