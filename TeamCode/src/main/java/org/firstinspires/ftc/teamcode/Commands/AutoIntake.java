package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

import java.util.Set;

public class AutoIntake implements Command {
    private final Intake intake;
    public AutoIntake(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void start() {
        intake.intakeArtifact();
    }

    @Override
    public void execute() {

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
