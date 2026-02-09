package frc.robot.motors;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.RPM;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;

public class TalonWrapper extends MotorWrapper {

    final TalonFX talon;
    final StatusSignal<Angle> pos;
    final StatusSignal<AngularVelocity> vel;
    double appliedVolts = 0.0;

    public TalonWrapper(int id) {
        talon = new TalonFX(id);
        pos = talon.getPosition(false);
        vel = talon.getVelocity(false);
        // probably dont need configs.
    }

    @Override
    public double getVoltage() {
        return appliedVolts;
    }

    @Override
    public void setVoltage(double voltage) {
        appliedVolts = voltage;
        talon.setVoltage(voltage);
    }

    @Override
    public double getEncderPos() {
        pos.refresh();
        return pos.getValue().in(Degrees);
    }

    @Override
    public double getEncoderVelocity() {
        vel.refresh();
        return vel.getValue().in(RPM);
    }

    public TalonFX getTalon() {
        return talon;
    }
}
