package frc.robot.motors;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class SparkMaxWrapper extends MotorWrapper {

    final SparkMax motor;
    final RelativeEncoder encoder;
    double appliedVolts = 0.0;

    public SparkMaxWrapper(int canID) {
        motor = new SparkMax(canID, MotorType.kBrushless);
        encoder = motor.getAlternateEncoder();
    }

    @Override
    public double getVoltage() {
        return appliedVolts;
    }

    @Override
    public void setVoltage(double voltage) {
        appliedVolts = voltage;
        motor.setVoltage(voltage);
    }

    @Override
    public double getEncderPos() {
        return encoder.getPosition();
    }

    @Override
    public double getEncoderVelocity() {
        return encoder.getVelocity();
    }

}
