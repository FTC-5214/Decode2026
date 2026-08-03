package org.firstinspires.ftc.teamcode.HelperClasses;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    private double kp, ki, kd, target, lastError = 0, integralSum = 0;
    private ElapsedTime timer;

    public PIDController(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double calculate(double current) {
        double dt = timer.seconds();
        timer.reset();
        if (dt <= 0) dt = 0.001; // Avoid division by zero

        double error = target - current;
        integralSum += error * dt;
        double derivative = (error - lastError) / dt;
        lastError = error;

        return (kp * error) + (ki * integralSum) + (kd * derivative);
    }
}
