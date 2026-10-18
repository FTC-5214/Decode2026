package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.geometry.Pose;

public class Poses {
    public static Pose startPoseGoal = new Pose(34.749077490774916, 132.269, Math.toRadians(90));
    public static Pose startPoseFar = new Pose(56.21678966789668, 9, Math.toRadians(90));
    public static Pose launchPose = new Pose(28, 121.75, Math.toRadians(143));
    public static Pose humanPlayerStation = new Pose(128.38376383763836, 9.476014760147594, Math.toRadians(180));

    public static Pose gateState1 = new Pose(25, 65, 0);
    public static Pose gateState2 = new Pose(18, 65, 0);


    //Auto spikes
    public static Pose intakeSpike2Pose1 = new Pose(42.783210332103316, 58.75, Math.toRadians(180));
    public static Pose intakeSpike2Pose2 = new Pose(19, 58.75, Math.toRadians(180));

    public static Pose intakeSpike1Pose1 = new Pose(42.783210332103316, 82.5, Math.toRadians(180));
    public static Pose intakeSpike1Pose2 = new Pose(19, 82.5, Math.toRadians(180));

    public static Pose intakeSpike3Pose1 = new Pose(42.783210332103316, 35, Math.toRadians(180));
    public static Pose intakeSpike3Pose2 = new Pose(19, 35, Math.toRadians(180));

    public static Pose resetPose = new Pose(132.38929889298893, 9.305350553505548);
    public static Pose endPose = new Pose(18.301642066420673, 91.69188191881919, Math.toRadians(90));
}
