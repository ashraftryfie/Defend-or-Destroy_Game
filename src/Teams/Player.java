package Teams;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Player {
    private static final AtomicInteger count = new AtomicInteger(-1);
    private final int PlayerID;
    protected int coins=3000;

    /** Setter & Getter **/
    public Player() {
        this.PlayerID = count.incrementAndGet();
        //this.coins=3000;
    }
    public int getPlayerID() {
        return PlayerID;
    }

    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public abstract void newGame();
    public abstract void loadGame();
    public abstract void buyUnit();
}
