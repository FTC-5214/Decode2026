package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake {
    private final DcMotor intakeMotor;
    public Intake(DcMotor intakeMotor) {
        this.intakeMotor = intakeMotor;
        if (Constants.intakeInverted) {
            this.intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        this.intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void intakeArtifact() {
        intakeMotor.setPower(1);
    }
    public void stop() {
        intakeMotor.setPower(0);
    }
    public void reverse() {
        intakeMotor.setPower(-1);
    }
}
