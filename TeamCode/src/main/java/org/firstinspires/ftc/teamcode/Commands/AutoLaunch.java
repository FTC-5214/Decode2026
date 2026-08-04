package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HelperClasses.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.Catapult;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.Set;

public class AutoLaunch implements Command {
    private final Catapult catapult;
    private final PIDController pid;

    private boolean launching = false;
    private boolean launched = false;
    private boolean returned = false;
    public AutoLaunch(Catapult catapult) {
        this.catapult = catapult;
        this.pid = new PIDController(Constants.catapult_kp, Constants.catapult_ki, Constants.catapult_kd);
    }

    @Override
    public void start() {
        pid.setTarget(Constants.catapultReturnSetpoint);
    }

    @Override
    public void execute() {
        double target = launching ? Constants.catapultLaunchSetpoint : Constants.catapultReturnSetpoint;
        boolean atTarget = Math.abs(catapult.getPosition() - target) < Constants.catapultSetpointTolerance;
        returned = Math.abs(catapult.getPosition() - Constants.catapultReturnSetpoint) < Constants.catapultSetpointTolerance;

        if (atTarget) {
            if (launching) {
                launched = true;
                launching = false;

            }
            else {
                launching = true;
            }
        }

        pid.setTarget(target);
        catapult.setPower(pid.calculate(catapult.getPosition()));
    }

    @Override
    public boolean done() {
        return (launched && returned);
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
        return InterruptedBehavior.END;
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
