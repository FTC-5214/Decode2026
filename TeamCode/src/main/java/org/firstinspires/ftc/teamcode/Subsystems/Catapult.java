package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

public class Catapult {
    private final DcMotor leftCatapultMotor;
    private final DcMotor rightCatapultMotor;
    public Catapult(DcMotor leftCatapultMotor, DcMotor rightCatapultMotor) {
        this.leftCatapultMotor = leftCatapultMotor;
        this.rightCatapultMotor = rightCatapultMotor;

        if (Constants.catapultInverted) {
            this.leftCatapultMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            this.rightCatapultMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        this.leftCatapultMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightCatapultMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.leftCatapultMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightCatapultMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.leftCatapultMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightCatapultMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPower(double power) {
        rightCatapultMotor.setPower(power);
        leftCatapultMotor.setPower(-power);

    }

    public double getPosition() {
        return rightCatapultMotor.getCurrentPosition();
    }

    public double getEncoderValue() {
        return rightCatapultMotor.getCurrentPosition();
    }
}
