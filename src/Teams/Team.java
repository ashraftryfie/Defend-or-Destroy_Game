package Teams;

import Units.*;
import java.util.*;

public abstract class Team {
    private Player[] players;
    private Vector<Unit> allUnitsInGame=new Vector<>();

    public Team(int numPlayers) {
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++){
            players[i] = new ConsolePlayer();
        }
    }

    public Player[] getPlayers() {
        return players;
    }
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Vector<Unit> getAllUnitsInGame() {
        return allUnitsInGame;
    }
    public void setAllUnitsInGame(Vector<Unit> allUnitsInGame) {
        this.allUnitsInGame = allUnitsInGame;
    }

}
