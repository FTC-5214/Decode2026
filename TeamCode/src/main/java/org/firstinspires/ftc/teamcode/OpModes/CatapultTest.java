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
public class CatapultTest extends OpMode {
    //Subsystems
    private Catapult catapultSubsystem;
    private Intake intakeSubsystem;

    //Motors
    private DcMotor leftCatapultMotor, rightCatapultMotor;

    //Commands
    private Command runCatapult;

    @Override
    public void init() {
        //Catapult
        leftCatapultMotor = hardwareMap.get(DcMotor.class, "catapultLeft");
        rightCatapultMotor = hardwareMap.get(DcMotor.class, "catapultRight");
        catapultSubsystem = new Catapult(leftCatapultMotor, rightCatapultMotor);

        //Commands
        runCatapult = new RunCatapult(catapultSubsystem, gamepad1, telemetry);
    }

    @Override
    public void start() {
        Scheduler.schedule(runCatapult);
    }

    @Override
    public void loop() {
        telemetry.clear();
        Scheduler.execute();
        telemetry.addData("catapult value:", catapultSubsystem.getEncoderValue());
        telemetry.addData("catapult command is running:", Scheduler.isScheduled(runCatapult));
    }
}
