package frc.robot.motors;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public abstract class MotorWrapper implements Sendable {
    public abstract double getVoltage();

    public abstract void setVoltage(double voltage);

    public abstract double getEncderPos();

    public abstract double getEncoderVelocity();

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Voltage", this::getVoltage, this::setVoltage);
        builder.addDoubleProperty("Position", this::getEncderPos, null);
        builder.addDoubleProperty("Velocity", this::getEncoderVelocity, null);
    }

}
