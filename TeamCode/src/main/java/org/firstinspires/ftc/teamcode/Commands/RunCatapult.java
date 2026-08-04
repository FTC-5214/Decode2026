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

public class RunCatapult implements Command {
    private final Catapult catapult;
    private final PIDController pid;
    private final Gamepad driverController;
    public RunCatapult(Catapult catapult, Gamepad driverController) {
        this.catapult = catapult;
        this.pid = new PIDController(Constants.catapult_kp, Constants.catapult_ki, Constants.catapult_kd);
        this.driverController = driverController;
    }

    @Override
    public void start() {
        pid.setTarget(Constants.catapultReturnSetpoint);
    }

    @Override
    public void execute() {
        if (driverController.right_trigger_pressed) {
            pid.setTarget(Constants.catapultLaunchSetpoint);
        }
        else {
            pid.setTarget(Constants.catapultReturnSetpoint);
        }

        catapult.setPower(pid.calculate(catapult.getPosition()));
    }

    @Override
    public boolean done() {
        return false;
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
