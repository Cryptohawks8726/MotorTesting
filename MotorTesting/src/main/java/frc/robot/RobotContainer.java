// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer implements Sendable {
  final SparkMax motor1 = new SparkMax(1, MotorType.kBrushless);
  double m1v = 0.0;
  final SparkMax motor2 = new SparkMax(2, MotorType.kBrushless);
  double m2v = 0.0;

  public RobotContainer() {
    SmartDashboard.putData("Motors", this);
    // Config motors
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    // ADD MOTOR SETUFF HERE
    builder.addDoubleProperty("Motor 1 Volts", () -> m1v, (val) -> {
      m1v = val; 
      motor1.setVoltage(val);
    });
    builder.addDoubleProperty("Motor 2 Volts", () -> m2v, (val) -> {m2v = val; motor2.setVoltage(val);});
  }
}
