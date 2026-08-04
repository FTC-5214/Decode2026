package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;

import java.util.Set;

public class RunIntake implements Command {
    private final Intake intake;
    private final Gamepad driverController;
    public RunIntake(Intake intake, Gamepad driverController) {
        this.intake = intake;
        this.driverController = driverController;
    }

    @Override
    public void start() {
        intake.stop();
    }

    @Override
    public void execute() {
        if (driverController.left_trigger_pressed) {
            intake.intakeArtifact();
        }
        else if (driverController.left_bumper) {
            intake.reverse();
        }
        else {
            intake.stop();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public void end(EndCondition endCondtion) {
        intake.stop();
    }

    @Override
    public Set<Object> requirements() {
        return Set.of(intake);
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
