package Game;

import Arena.Grid;
import Teams.Player;
import Teams.Team;
import Units.Unit;
import Units.UnitDestroyObserver;
import Units.UnitFactory;
import Units.UnitType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DorD_Game extends GameManager implements UnitDestroyObserver {
    private int remainingAttackerUnits;
    private double remainingTime;
    protected static DorD_Game instance;
    private static Grid grid = new Grid();
    private Unit mainBase;
    private ArrayList<int[]> airplanesCoordinates;
    private Team teamAttack;
    private Team teamDefend;
    private Team teamStatic;
    private final UnitFactory unitFactory;
    private GameState state;
    private Timer timer;

    /** DorDGame Constructor **/
    private DorD_Game(Team teamAttack, Team teamDefend, Team teamStatic,Pauser pauser) {
        this.airplanesCoordinates = new ArrayList<>();
        this.unitFactory = new UnitFactory();
        this.state = GameState.Running;
        this.teamAttack = teamAttack;
        this.teamDefend = teamDefend;
        this.teamStatic = teamStatic;
        this.timer=new Timer(pauser);

    }
    public static DorD_Game getInstance(Team teamAttack, Team teamDefend, Team teamStatic,Pauser pauser) {
        if (instance == null) {
            synchronized (DorD_Game.class) {
                if (instance == null) {
                    instance = new DorD_Game(teamAttack, teamDefend, teamStatic,pauser);
                }
            }
        }
        return instance;
    }

    /** Getter & Setter **/
    public ArrayList<int[]> getAirplanesCoordinates() {
        return airplanesCoordinates;
    }
    public void setAirplanesCoordinates(ArrayList<int[]> airplanesCoordinates) {
        this.airplanesCoordinates = airplanesCoordinates;
    }

    public Timer getTimer() {
        return timer;
    }

    public Grid getGrid() {
        return grid;
    }
    public void setGrid(Grid grid) {
        DorD_Game.grid = grid;
    }

    public Team getTeamAttack() {
        return teamAttack;
    }
    public void setTeamAttack(Team teamAttack) {
        this.teamAttack = teamAttack;
    }

    public Team getTeamDefend() {
        return teamDefend;
    }
    public void setTeamDefend(Team teamDefend) {
        this.teamDefend = teamDefend;
    }

    public Team getTeamStatic() {
        return teamStatic;
    }
    public void setTeamStatic(Team teamStatic) {
        this.teamStatic = teamStatic;
    }

    public Unit getMainBase() {
        return mainBase;
    }
    public boolean setMainBase(int x, int y, UnitDestroyObserver observer) {
        String line = "MainBase 10000 0 0.00 0.00 0 0 0 50 Structure";

        this.mainBase = new Unit(1000, 1000, 1, observer, line);

        if(!grid.addUnit(mainBase))
            return false;
        return true;

    }

    public int getRemainingAttackerUnits() {
        return remainingAttackerUnits;
    }
    public void setRemainingAttackerUnits(int remainingAttackerUnits) {
        this.remainingAttackerUnits = remainingAttackerUnits;
    }

    public double getRemainingTime() {
        return remainingTime;
    }
    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public GameState getState() {
        return state;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    /** Buy Unit **/
    public boolean BuyUnit(Team team, Player player, int x, int y, String unitName, UnitDestroyObserver observer) {
        Unit newUnit;
        double mode;
        if (team==teamAttack) {
            mode = 0;
            newUnit = unitFactory.createUnit(unitName, x, y, mode, observer);

        } else if (team==teamDefend) {
            mode = 1;
            newUnit = unitFactory.createUnit(unitName, x, y, mode, observer);
        }
        else {
            mode = 2;
            newUnit = unitFactory.createUnit(unitName, x, y, mode, observer);
        }
        if (newUnit == null)
            return false;
        
        /** 
         * MUST CHANGE BEFORE BUYING UNIT CHECK COST!!!!
        **/ 
        if (newUnit.getUnitProperties()[7].getPropertyValue() > player.getCoins()) {
            System.out.println("No Coins Enough");
            return false;
        }
        /**************************************************/

//        int xx,yy;
//        Scanner input = new Scanner(System.in);
//        while (!grid.addUnit(newUnit)){
//            xx = input.nextInt();
//            yy = input.nextInt();
//            newUnit.getPosition().setCenterX(xx);
//            newUnit.getPosition().setCenterY(yy);
//        }
        if (!grid.addUnit(newUnit)) {
            System.out.println("Can't Add Unit "+newUnit.getUnitName()+"#"+newUnit.getID()+" To The Grid");
            return false;
        }
        player.setCoins((int) (player.getCoins() - newUnit.getUnitProperties()[7].getPropertyValue()));
        team.getAllUnitsInGame().add(newUnit);
        if (newUnit.getUnitProperties()[4].getPropertyValue() == 0)
            this.setRemainingAttackerUnits(this.getRemainingAttackerUnits()+1);
        if(newUnit.getUnitType()== UnitType.Airplane){
            int[] arr= new int[]{newUnit.getID(),newUnit.getPosition().getCenterX(),newUnit.getPosition().getCenterY(),3};///////
            airplanesCoordinates.add(arr);
        }
        return true;
    }

    /** Tell The Game Manager That A Unit Has Died **/
    @Override
    public void OnUnitDestroy(Unit destroyedUnit) {
        if (!destroyedUnit.isAlive())
            return;
        if ((int) destroyedUnit.getUnitProperties()[4].getPropertyValue() == 0) {
            System.out.println("Unit "+destroyedUnit.getUnitName()+"#"+destroyedUnit.getID()+" Team(Attacker) is now DEAD.");
            this.setRemainingAttackerUnits(this.getRemainingAttackerUnits()-1);
            destroyedUnit.setAlive(false);
        }
        else if ((int) destroyedUnit.getUnitProperties()[4].getPropertyValue() == 1){
            if (destroyedUnit.getID() == mainBase.getID()) {
                System.out.println("MainBase Team(Defender) is now DEAD.");
                destroyedUnit.setAlive(false);
                Unit.myState=false;
            }
            else {
                System.out.println("Unit " + destroyedUnit.getUnitName() + "#" + destroyedUnit.getID() + " Team(Defender) is now DEAD.");
                destroyedUnit.setAlive(false);
            }
        }
        else {
            System.out.println("Unit "+destroyedUnit.getUnitName()+"#"+destroyedUnit.getID()+" is now DEAD.");
            destroyedUnit.setAlive(false);
        }
        if (remainingAttackerUnits == 0) {
            this.setState(GameState.DefenderWon);
            System.out.println(GameState.DefenderWon);
            System.exit(0);

        }
        else if (mainBase.getUnitProperties()[0].getPropertyValue()<=0){
            this.setState(GameState.AttackerWon);
            System.out.println(GameState.AttackerWon);
            System.exit(0);

        }

    }

    /** Read Data Entry From Input File **/
    public void FileInput(UnitDestroyObserver observer,Team team,int i) throws FileNotFoundException {
        String BuyingFile;
        if(team == teamDefend){
            BuyingFile = "BuyUnitDefend";
        }
        else if(team == teamAttack){
            BuyingFile = "BuyUnitAttack";
        }
        else {
            BuyingFile = "C:\\Users\\cmoka\\OneDrive\\Desktop\\Final Project\\FinalDorDGame_13_12\\src\\StaticUnit";
        }
        int xx;
        int yy;
        String s;
        Scanner input = new Scanner(new File(BuyingFile));
        try {
            while (input.hasNextLine())
            {
                s = input.nextLine();
                String unitName = s.split(" ")[0];
                xx = Integer.parseInt(s.split(" ")[1]);
                yy = Integer.parseInt(s.split(" ")[2]);
                if(team == teamDefend)
                    this.BuyUnit(this.getTeamDefend(), this.getTeamDefend().getPlayers()[i], xx, yy, unitName, observer);
                else if(team == teamAttack)
                    this.BuyUnit(this.getTeamAttack(), this.getTeamAttack().getPlayers()[i], xx, yy, unitName, observer);
                else
                    this.BuyUnit(this.getTeamStatic(), this.getTeamStatic().getPlayers()[i], xx, yy, unitName, observer);
            }
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    static public class Pauser{
        private boolean isPaused = false;
        public synchronized void pause(){
            isPaused = true;
        }
        public synchronized void resume(){
            isPaused=false;
            notifyAll();
        }
        public synchronized void look() throws InterruptedException {
            while (isPaused) {
                wait();
            }
        }
    }
    public class Timer extends Thread{
        private Pauser pauser;
        public Timer(Pauser pauser)
        {
            this.pauser=pauser;
        }
        public void run()
        {
          while(remainingTime>0)
          {
              remainingTime--;
              /** Pausing the Time **/
              try {
                  pauser.look();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
          System.out.println("Time Is Over\n"+GameState.DefenderWon);
          System.exit(0);
        }
    }

}

