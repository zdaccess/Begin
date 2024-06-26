package edu.school21.game.logic.mode;

public enum GameMode {
    PRODUCTION, DEVELOP;

    private static GameMode mode = GameMode.PRODUCTION;

    public static GameMode get() {
        return mode;
    }

    public static void setDevelop() {
        mode = GameMode.DEVELOP;
    }

}
