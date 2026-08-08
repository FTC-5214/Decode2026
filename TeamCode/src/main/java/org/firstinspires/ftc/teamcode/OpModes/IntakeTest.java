package org.firstinspires.ftc.teamcode.OpModes;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Commands.RunCatapult;
import org.firstinspires.ftc.teamcode.Commands.RunIntake;
import org.firstinspires.ftc.teamcode.Storage;
import org.firstinspires.ftc.teamcode.Subsystems.Catapult;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.Supplier;

@TeleOp
public class IntakeTest extends OpMode {
    //Subsystems
    private Intake intakeSubsystem;

    //Motors
    private DcMotor intakeMotor;

    //Commands
    private Command runIntake;

    @Override
    public void init() {
        //Catapult
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        //intakeSubsystem = new Intake(intakeMotor);

        //Commands
        //runIntake = new RunIntake(intakeSubsystem, gamepad1, telemetry);
    }

    @Override
    public void start() {
        //Scheduler.schedule(runIntake);
        intakeMotor.setPower(1);
    }

    @Override
    public void loop() {
        intakeMotor.setPower(1);
        //telemetry.clear();
        //Scheduler.execute();
        //telemetry.addData("Intake command is running:", Scheduler.isScheduled(runIntake));
    }
}
