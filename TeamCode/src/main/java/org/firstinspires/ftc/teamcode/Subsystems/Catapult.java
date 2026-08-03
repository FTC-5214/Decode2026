package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Catapult {
    private final DcMotor leftCatapultMotor;
    private final DcMotor rightCatapultMotor;
    public Catapult(DcMotor leftCatapultMotor, DcMotor rightCatapultMotor) {
        this.leftCatapultMotor = leftCatapultMotor;
        this.rightCatapultMotor = rightCatapultMotor;
    }

    public void setPower(double power) {
        leftCatapultMotor.setPower(power);
        rightCatapultMotor.setPower(-power);
    }

    public double getPostiion() {
        return leftCatapultMotor.getCurrentPosition();
    }
}
