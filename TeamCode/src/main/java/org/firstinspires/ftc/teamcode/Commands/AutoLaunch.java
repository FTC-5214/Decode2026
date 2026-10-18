package org.firstinspires.ftc.teamcode.Commands;


import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Catapult;

import java.util.Set;

public class AutoLaunch implements Command {
    private final Catapult catapult;
    private final ElapsedTime timer = new ElapsedTime();
    private double last_time = 0, launch_last_time;
    private boolean done = false;
    public AutoLaunch(Catapult catapult) {
        this.catapult = catapult;
    }

    @Override
    public void start() {
        done = false;
        timer.reset();
        launch_last_time = timer.seconds();
    }

    @Override
    public void execute() {
        if ((timer.seconds() - launch_last_time) < 1.0) {
            catapult.setPower(1);
            last_time = timer.seconds();
        }
        else {
            if (catapult.getPosition() > 10) {
                if (timer.seconds() - last_time > 5) {
                    //prevents motor from stalling and consuming all our power if it can't pull down the catapult
                    catapult.setPower(0);
                }
                else {
                    catapult.setPower(-1);
                }
            }
            else {
                catapult.setPower(0);
                done = true;
            }
        }
    }

    @Override
    public boolean done() {
        return done;
    }

    @Override
    public void end(EndCondition endCondition) {
        catapult.setPower(0);
    }

    @Override
    public Set<Object> requirements() {
        return Set.of(catapult);
    }
    @Override
    public int priority() {
        return 0;
    }
    @Override
    public InterruptedBehavior interruptedBehavior() {
        return InterruptedBehavior.SUSPEND;
    }
    @Override
    public BlockedBehavior blockedBehavior() {
        return BlockedBehavior.CANCEL;
    }
    @Override
    public ConflictBehavior conflictBehavior() {
        return ConflictBehavior.OVERRIDE;
    }
}
