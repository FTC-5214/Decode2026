package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HelperClasses.PIDFController;
import org.firstinspires.ftc.teamcode.Subsystems.Catapult;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.Set;

public class RunCatapult implements Command {
    private final Catapult catapult;
    private final PIDFController launchPid;
    private final PIDFController returnPid;
    private final Gamepad driverController;

    private final Telemetry telemetry;
    private final ElapsedTime timer = new ElapsedTime();
    private double last_time = 0;
    public RunCatapult(Catapult catapult, Gamepad driverController, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.catapult = catapult;
        this.launchPid = new PIDFController(Constants.catapultLaunch_kp, Constants.catapultLaunch_ki, Constants.catapultLaunch_kd, Constants.catapultLaunch_kf);
        this.returnPid = new PIDFController(Constants.catapultReturn_kp, Constants.catapultReturn_ki, Constants.catapultReturn_kd, Constants.catapultReturn_kf);
        this.driverController = driverController;
    }

    @Override
    public void start() {
        launchPid.setTarget(Constants.catapultLaunchSetpoint);
        returnPid.setTarget(Constants.catapultReturnSetpoint);
    }

    @Override
    public void execute() {
        if (driverController.right_trigger > 0) {
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
                //catapult.setPower(returnPid.calculate(catapult.getPosition()));
            }
        }

        telemetry.addData("position:", catapult.getPosition());
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
