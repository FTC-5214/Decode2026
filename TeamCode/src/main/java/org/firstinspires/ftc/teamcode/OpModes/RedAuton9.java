package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.*;

import org.firstinspires.ftc.teamcode.Commands.AutoIntake;
import org.firstinspires.ftc.teamcode.Commands.AutoLaunch;
import org.firstinspires.ftc.teamcode.Storage;
import org.firstinspires.ftc.teamcode.Subsystems.Catapult;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Poses;
import static com.pedropathing.ivy.commands.Commands.*;

@Autonomous
public class RedAuton9 extends OpMode {
    private Follower follower;
    public static Pose startingPose = Poses.startPoseGoal;
    //Subsystems
    private Catapult catapultSubsystem;
    private Intake intakeSubsystem;

    //Motors
    private DcMotor leftCatapultMotor, rightCatapultMotor, intakeMotor;

    //Paths
    private PathChain startToLaunch, launchToSpikeStrip2, intakeSpikeStrip2, spikeStrip2ToGateClear, gateClearToLaunch, launchToSpikeStrip1, intakeSpikeStrip1, spikeStrip1ToLaunch, launchToSpikeStrip3, intakeSpikeStrip3, spikeStrip3ToLaunch, launchToEnd;
    //Commands
    private Command autoLaunch, autoIntake, auto;
    private void buildPaths() {
        startToLaunch = follower.pathBuilder()
                .addPath(new BezierLine(Poses.startPoseGoal.mirror(), Poses.launchPose.mirror()))
                .setLinearHeadingInterpolation(Poses.startPoseGoal.mirror().getHeading(), Poses.launchPose.mirror().getHeading())
                .setTimeoutConstraint(100)
                .setTValueConstraint(0.95)
                .build();

        launchToSpikeStrip2 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        Poses.launchPose.mirror(),
                        new Pose(58.608, 100.399).mirror(),
                        Poses.intakeSpike2Pose1.mirror()))
                .setLinearHeadingInterpolation(Poses.launchPose.mirror().getHeading(), Poses.intakeSpike2Pose1.mirror().getHeading())
                .setTimeoutConstraint(100)
                .build();

        intakeSpikeStrip2 = follower.pathBuilder()
                .addPath(new BezierLine(Poses.intakeSpike2Pose1.mirror(), Poses.intakeSpike2Pose2.mirror()))
                .setTangentHeadingInterpolation()
                .setTimeoutConstraint(100)
                .build();

        spikeStrip2ToGateClear = follower.pathBuilder()
                .addPath(new BezierCurve(
                        Poses.intakeSpike2Pose2.mirror(),
                        new Pose(52.091, 45.573).mirror(),
                        Poses.gateState2.mirror()
                ))
                .setLinearHeadingInterpolation(Poses.intakeSpike2Pose2.mirror().getHeading(), Poses.gateState2.mirror().getHeading())
                .setTimeoutConstraint(100)
                .build();

        gateClearToLaunch = follower.pathBuilder()
                .addPath(new BezierCurve(Poses.gateState2.mirror(),
                        new Pose(98.363, 70.770).mirror(),
                        Poses.launchPose.mirror()))
                .setLinearHeadingInterpolation(Poses.gateState2.mirror().getHeading(), Poses.launchPose.mirror().getHeading())
                .setTimeoutConstraint(100)
                .build();

        launchToSpikeStrip1 = follower.pathBuilder()
                .addPath(new BezierCurve(Poses.launchPose.mirror(),
                        new Pose(53.344, 97.647).mirror(),
                        Poses.intakeSpike1Pose1.mirror()
                ))
                .setLinearHeadingInterpolation(Poses.launchPose.mirror().getHeading(), Poses.intakeSpike1Pose1.mirror().getHeading())
                .build();

        intakeSpikeStrip1 = follower.pathBuilder()
                .addPath(new BezierLine(Poses.intakeSpike1Pose1.mirror(), Poses.intakeSpike1Pose2.mirror()))
                .setTangentHeadingInterpolation()
                .build();

        spikeStrip1ToLaunch = follower.pathBuilder()
                .addPath(new BezierLine(Poses.intakeSpike1Pose2.mirror(), Poses.launchPose.mirror()))
                .setLinearHeadingInterpolation(Poses.intakeSpike1Pose2.mirror().getHeading(), Poses.launchPose.mirror().getHeading())
                .build();

        launchToSpikeStrip3 = follower.pathBuilder()
                .addPath(new BezierLine(Poses.launchPose.mirror(), Poses.intakeSpike3Pose1.mirror()))
                .setLinearHeadingInterpolation(Poses.launchPose.mirror().getHeading(), Poses.intakeSpike3Pose1.mirror().getHeading())
                .build();

        spikeStrip3ToLaunch = follower.pathBuilder()
                .addPath(new BezierLine(Poses.intakeSpike3Pose1.mirror(), Poses.intakeSpike3Pose2.mirror()))
                .setTangentHeadingInterpolation()
                .build();

        intakeSpikeStrip3 = follower.pathBuilder()
                .addPath(new BezierLine(Poses.intakeSpike3Pose1.mirror(), Poses.intakeSpike3Pose2.mirror()))
                .setTangentHeadingInterpolation()
                .build();

        spikeStrip3ToLaunch = follower.pathBuilder()
                .addPath(new BezierLine(Poses.intakeSpike3Pose2.mirror(), Poses.launchPose.mirror()))
                .setLinearHeadingInterpolation(Poses.intakeSpike3Pose2.mirror().getHeading(), Poses.launchPose.mirror().getHeading())
                .build();

        launchToEnd = follower.pathBuilder()
                .addPath(new BezierLine(Poses.launchPose.mirror(), Poses.endPose.mirror()))
                .setLinearHeadingInterpolation(Poses.launchPose.mirror().getHeading(), Poses.endPose.mirror().getHeading())
                .build();
    }

    @Override
    public void init() {
        //drivetrain
        follower = Constants.createFollower(hardwareMap);

        follower.setStartingPose(startingPose.mirror());
        follower.update();

        //catapult
        leftCatapultMotor = hardwareMap.get(DcMotor.class, "catapultLeft");
        rightCatapultMotor = hardwareMap.get(DcMotor.class, "catapultRight");
        catapultSubsystem = new Catapult(leftCatapultMotor, rightCatapultMotor);

        //intake
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeSubsystem = new Intake(intakeMotor);

        autoLaunch = new AutoLaunch(catapultSubsystem);
        autoIntake = new AutoIntake(intakeSubsystem);

        buildPaths();

        auto = sequential(
                race(
                        follow(follower, startToLaunch, false),
                        waitMs(700)
                ),
                autoLaunch,
                race(
                        follow(follower, launchToSpikeStrip2, false),
                        waitMs(4700)
                ),
                race(
                        follow(follower, intakeSpikeStrip2, false, 0.25),
                        autoIntake,
                        waitMs(2000)
                ),
                race(
                        follow(follower, spikeStrip2ToGateClear, false, 0.8),
                        waitMs(3500)
                ),
                waitMs(1500),
                race(
                        follow(follower, gateClearToLaunch, false, 1),
                        waitMs(4700)
                ),
                autoLaunch,
                race(
                        follow(follower, launchToSpikeStrip1),
                        waitMs(4700)
                ),
                race(
                        follow(follower, intakeSpikeStrip1, 0.25),
                        autoIntake
                ),
                race(
                        follow(follower, spikeStrip1ToLaunch,1 ),
                        waitMs(4700)
                ),
                autoLaunch,
                follow(follower, launchToEnd)
        );

        Storage.alliance = "Blue";
    }

    @Override
    public void start() {
        Scheduler.schedule(auto);
    }

    @Override
    public void loop() {
        Scheduler.execute();
        follower.update();
        telemetry.addData("is leave running:", Scheduler.isRunning(auto));
        telemetry.addData("follower busy", follower.isBusy());
        telemetry.addData("pose", follower.getPose());
        telemetry.addData("target", Poses.launchPose);
        telemetry.addData("heading error (rad)", follower.getHeadingError());
        telemetry.addData("velocity", follower.getVelocity().getMagnitude());
        telemetry.addData("tvalue", follower.getCurrentTValue());
        telemetry.addData("at parametric end", follower.atParametricEnd());
        telemetry.addData("TValueConstraint", follower.getCurrentPath().getPathEndTValueConstraint());

        telemetry.update();
        Storage.currentPose = follower.getPose();
    }

    @Override
    public void stop() {

    }
}
