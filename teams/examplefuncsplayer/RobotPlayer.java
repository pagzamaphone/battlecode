package examplefuncsplayer;

import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.MapLocation;
 
/** The example funcs player is a player meant to demonstrate basic usage of the most common commands.
 * Robots will move around randomly, occasionally mining and writing useless messages.
 * The HQ will spawn soldiers continuously.
 */
public class RobotPlayer {
    public static void run(RobotController rc) {
        while (true) {
            try {
                if (rc.getType() == RobotType.HQ) {
                    if (rc.isActive()) {
                        // Spawn a soldier
                        Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
                        if (rc.canMove(dir))
                            rc.spawn(dir);
                    }
                } else if (rc.getType() == RobotType.SOLDIER) {
                    if (rc.isActive()) {
                        // Choose a random direction, and move that way if possible
                        Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
                        MapLocation loc = rc.getLocation().add(dir);
                        if(rc.senseMine(loc)!=null) {
                            rc.defuseMine(loc);
                        } else if(rc.canMove(dir)) {
                            rc.move(dir);
                            rc.setIndicatorString(0, "Last direction moved: "+dir.toString());
                        }
                    }
                }
 
                // End turn
                rc.yield();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
