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

        this.leftCatapultMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightCatapultMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) {
        leftCatapultMotor.setPower(power);
        rightCatapultMotor.setPower(-power);
    }

    public double getPosition() {
        return leftCatapultMotor.getCurrentPosition();
    }
}
