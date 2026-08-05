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
public class TeleOpNoAssistance extends OpMode {
    //Drivetrain
    private Follower follower;
    public static Pose startingPose = Storage.currentPose;

    //Subsystems
    private Catapult catapultSubsystem;
    private Intake intakeSubsystem;

    //Motors
    private DcMotor leftCatapultMotor, rightCatapultMotor, intakeMotor;

    //Commands
    private Command runCatapult, runIntake;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();

        //Catapult
        leftCatapultMotor = hardwareMap.get(DcMotor.class, "catapultLeft");
        rightCatapultMotor = hardwareMap.get(DcMotor.class, "catapultRight");
        catapultSubsystem = new Catapult(leftCatapultMotor, rightCatapultMotor);

        //Intake
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeSubsystem = new Intake(intakeMotor);

        //Commands
        runCatapult = new RunCatapult(catapultSubsystem, gamepad1);
        runIntake = new RunIntake(intakeSubsystem, gamepad1);
    }

    @Override
    public void start() {
        follower.startTeleOpDrive(true);
        Scheduler.schedule(runCatapult, runIntake);
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
