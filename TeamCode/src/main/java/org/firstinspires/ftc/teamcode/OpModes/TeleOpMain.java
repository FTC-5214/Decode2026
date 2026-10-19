package org.firstinspires.ftc.teamcode.OpModes;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
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
import org.firstinspires.ftc.teamcode.pedroPathing.Poses;
import static com.pedropathing.ivy.pedro.PedroCommands.*;

import java.util.function.Supplier;

@TeleOp
public class TeleOpMain extends OpMode {
    //Drivetrain
    private Follower follower;
    public static Pose startingPose ;

    //Subsystems
    private Catapult catapultSubsystem;
    private Intake intakeSubsystem;

    private boolean automatedDrive = false;
    private PathChain toLaunch, toHumanStation, toGate;
    //Motors
    private DcMotor leftCatapultMotor, rightCatapultMotor, intakeMotor;

    //Commands
    private Command runCatapult, runIntake;
    private boolean isRobotCentric = false;

    private Command goToLaunchPosition, goToHumanPlayerStation, openGate;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        startingPose = Storage.currentPose;
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
        runCatapult = new RunCatapult(catapultSubsystem, gamepad1, telemetry);
        runIntake = new RunIntake(intakeSubsystem, gamepad1, telemetry);
    }

    @Override
    public void start() {
        follower.startTeleOpDrive(true);
        runCatapult.schedule();
        runIntake.schedule();
    }

    @Override
    public void loop() {
        follower.update();
        Storage.currentPose = follower.getPose();
        Scheduler.execute();
        telemetry.addData("Is robot centric?", isRobotCentric);
        if (gamepad1.aWasPressed()) {
            isRobotCentric = !isRobotCentric;
        }

        //auto-align for launch
        if (gamepad1.bWasPressed()) {
            automatedDrive = true;
            if (Storage.alliance.equals("Red")) {
                toLaunch = follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), Poses.launchPose.mirror()))
                        .setHeadingInterpolation(HeadingInterpolator.linear(follower.getHeading(), Poses.launchPose.mirror().getHeading()))
                        .build();
            }
            else {
                toLaunch = follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), Poses.launchPose))
                        .setHeadingInterpolation(HeadingInterpolator.linear(follower.getHeading(), Poses.launchPose.getHeading()))
                        .build();
            }
            goToLaunchPosition = follow(follower, toLaunch);
            goToLaunchPosition.schedule();
        }
        else if (gamepad1.bWasReleased()) {
            automatedDrive = false;
            Scheduler.cancel(goToLaunchPosition);
            follower.startTeleOpDrive(true);
        }

        //auto human player intake
        if (gamepad1.xWasPressed()) {
            automatedDrive = true;
            if (Storage.alliance.equals("Red")) {
                toHumanStation = follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), Poses.humanPlayerStation.mirror()))
                        .setHeadingInterpolation(HeadingInterpolator.linear(follower.getHeading(), Poses.humanPlayerStation.mirror().getHeading()))
                        .build();
            }
            else {
                toHumanStation = follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), Poses.humanPlayerStation))
                        .setHeadingInterpolation(HeadingInterpolator.linear(follower.getHeading(), Poses.humanPlayerStation.getHeading()))
                        .build();
            }
            goToHumanPlayerStation = follow(follower, toHumanStation);
            goToHumanPlayerStation.schedule();
        }
        else if (gamepad1.xWasReleased()) {
            automatedDrive = false;
            Scheduler.cancel(goToHumanPlayerStation);
            follower.startTeleOpDrive(true);
        }

        //auto release ramp
        if (gamepad1.rightBumperWasPressed()) {
            automatedDrive = true;
            if (Storage.alliance.equals("Red")) {
                toGate = follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), Poses.gateState1.mirror()))
                        .setHeadingInterpolation(HeadingInterpolator.linear(follower.getHeading(), Poses.gateState1.mirror().getHeading()))
                        .addPath(new BezierLine(Poses.gateState1.mirror(), Poses.gateState2.mirror()))
                        .setTangentHeadingInterpolation()
                        .addPath(new BezierLine(Poses.gateState2.mirror(), Poses.gateState1.mirror()))
                        .setTangentHeadingInterpolation()
                        .build();
            }
            else {
                toGate = follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), Poses.gateState1))
                        .setHeadingInterpolation(HeadingInterpolator.linear(follower.getHeading(), Poses.gateState1.getHeading()))
                        .addPath(new BezierLine(Poses.gateState1, Poses.gateState2))
                        .setTangentHeadingInterpolation()
                        .addPath(new BezierLine(Poses.gateState2, Poses.gateState1))
                        .setTangentHeadingInterpolation()
                        .build();
            }
            openGate = follow(follower, toGate, 0.8);
            openGate.schedule();
        }
        else if (gamepad1.rightBumperWasReleased()) {
            automatedDrive = false;
            Scheduler.cancel(openGate);
            follower.setMaxPower(1);
            follower.startTeleOpDrive(true);
        }

        if (!automatedDrive) {
            if (Storage.alliance.equals("Blue")) {
                if (!isRobotCentric) {
                    follower.setTeleOpDrive(
                            gamepad1.left_stick_y,
                            gamepad1.left_stick_x,
                            -gamepad1.right_stick_x,
                            isRobotCentric
                    );
                }
                else {
                    follower.setTeleOpDrive(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x,
                            isRobotCentric
                    );
                }
            }
            else {
                if (!isRobotCentric) {
                    follower.setTeleOpDrive(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x,
                            isRobotCentric
                    );
                }
                else {
                    // Same as robot-centric for now; kept separate to mirror the Blue branch
                    follower.setTeleOpDrive(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x,
                            isRobotCentric
                    );
                }
            }

            if (gamepad1.yWasPressed()) {
                if (Storage.alliance.equals("Blue")) {
                    follower.setPose(Poses.resetPose);
                }
                else {
                    follower.setPose(Poses.resetPose.mirror());
                }
            }
        }
    }
}
