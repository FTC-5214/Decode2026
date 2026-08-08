package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

import java.util.Set;

public class RunIntake implements Command {
    private final Intake intake;
    private final Gamepad driverController;
    private Telemetry telemetry;
    public RunIntake(Intake intake, Gamepad driverController, Telemetry telemetry) {
        this.intake = intake;
        this.driverController = driverController;
        this.telemetry = telemetry;
    }

    @Override
    public void start() {
        intake.stop();
    }

    @Override
    public void execute() {
        if (driverController.left_trigger > 0) {
            telemetry.addLine("Intake Running");
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
    public void end(EndCondition endCondition) {
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
