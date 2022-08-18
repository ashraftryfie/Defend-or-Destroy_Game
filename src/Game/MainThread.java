package Game;

import Units.Unit;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MainThread extends Thread{
    DorD_Game DorD;
    Schedule scheduleExecutor;
    Vector<Unit> AttackAllUnits;
    Vector<Unit> DefenderAllUnits;
    Vector<Unit.ActionThread> AttackAllUnitsThread;
    Vector<Unit.ActionThread> DefenderAllUnitsThread;
    /** MainThread Constructor **/
    public MainThread(DorD_Game D, Schedule S) {
        this.DorD = D;
        this.scheduleExecutor=S;
        this.AttackAllUnits = new Vector<>(DorD.getTeamAttack().getAllUnitsInGame());
        this.DefenderAllUnits = new Vector<>(DorD.getTeamDefend().getAllUnitsInGame());
        this.AttackAllUnitsThread=new Vector<>();
        this.DefenderAllUnitsThread=new Vector<>();
        for (Unit attackAllUnit : AttackAllUnits) {
            AttackAllUnitsThread.add(attackAllUnit.new ActionThread(this.DorD));
        }
        for (Unit defendAllUnit :DefenderAllUnits)
            DefenderAllUnitsThread.add(defendAllUnit.new ActionThread(this.DorD));
    }

    /** Run MainThread Method **/
    public void run() {
        DorD.setRemainingTime(100);
        DorD.getTimer().start();
        /** Constructing all attack executors**/
        for (Unit attackAllUnit : AttackAllUnits) {
            if (DorD.getState() != GameState.Running)
                break;
            if (attackAllUnit.isAlive()) {
                scheduleExecutor.scheduleWithFixedDelay(attackAllUnit.new ActionThread(DorD), 0,1, TimeUnit.MILLISECONDS);

            }
        }
        /** Constructing all defend executors **/
        for (Unit defenderAllUnit : DefenderAllUnits) {
            if (DorD.getState() != GameState.Running)
                break;
            if (defenderAllUnit.isAlive()) {
                scheduleExecutor.scheduleWithFixedDelay(defenderAllUnit.new ActionThread(DorD), 0, 1, TimeUnit.MILLISECONDS);
            }
        }
    }


}
