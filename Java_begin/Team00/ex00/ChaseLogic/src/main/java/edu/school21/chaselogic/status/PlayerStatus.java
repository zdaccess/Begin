package edu.school21.chaselogic.status;

public enum PlayerStatus {
    CONTINUE, WAIT, WIN, LOSE, WATCHIG;

    private static PlayerStatus status = PlayerStatus.CONTINUE;

    public static PlayerStatus get() {
        return status;
    }

    public static void setWait() {
        status = PlayerStatus.WAIT;
    }

    public static void setContinue() {
        status = PlayerStatus.CONTINUE;
    }

    public static void setWin() {
        status = PlayerStatus.WIN;
    }

    public static void setLose() {
        status = PlayerStatus.LOSE;
    }

    public static void setWatching() {
        status = PlayerStatus.WATCHIG;
    }

    public static boolean isEnd() {
        return (status.equals(WIN) || status.equals(LOSE)) ? true : false;
    }

}
