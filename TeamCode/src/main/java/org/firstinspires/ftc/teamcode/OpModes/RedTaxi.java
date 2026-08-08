package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
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
public class RedTaxi extends OpMode {
    private Follower follower;

    //Paths
    private PathChain startToHumanPlayerStation;
    //Commands
    private Command auto;

    private Pose startingPose = new Pose(85, 9, Math.toRadians(180));
    private Pose endingPose = new Pose(109, 9, Math.toRadians(180));
    private void buildPaths() {
        startToHumanPlayerStation = follower.pathBuilder()
                .addPath(new BezierLine(startingPose, endingPose))
                .setLinearHeadingInterpolation(startingPose.getHeading(), endingPose.getHeading())
                .build();
    }

    @Override
    public void init() {
        //drivetrain
        follower = Constants.createFollower(hardwareMap);

        follower.setStartingPose(startingPose);
        follower.update();

        buildPaths();

        auto = follow(follower, startToHumanPlayerStation);

        Storage.alliance = "Red";
        Storage.currentPose = follower.getPose();
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
