// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.motors.SparkMaxWrapper;

public class RobotContainer implements Sendable {
  // List of the different types of motors that are supported
  public enum MotorModels {
    SparkMax,
    // TODO: add more... ?
  }

  int queuedCanID = 1;
  MotorModels queuedMotorModel = MotorModels.SparkMax;

  public RobotContainer() {
    SmartDashboard.putData("Add Motors", this);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }

  public void createNewMotor() {
    switch (queuedMotorModel) {
      case SparkMax:
        SparkMaxWrapper wrapper = new SparkMaxWrapper(queuedCanID);
        SmartDashboard.putData("Motors/" + queuedCanID, wrapper);
        break;
      default:
        break;
    }
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    // Interface for adding motors of different types at certain CAN IDs
    // does java have a map function?
    MotorModels[] models = MotorModels.values();
    String[] motorTypeNames = new String[models.length];
    for (int i = 0; i < models.length; i++) {
      motorTypeNames[i] = models[i].name();
    }
    builder.publishConstStringArray("Motor Types", motorTypeNames);

    builder.addIntegerProperty("CAN ID", () -> queuedCanID, (v) -> {
      if (v > 0 && v < 60) {
        queuedCanID = (int) v;
      }
    });

    builder.addStringProperty("Motor Type", () -> queuedMotorModel.name(), (v) -> {
      try {
        queuedMotorModel = MotorModels.valueOf(v);
      } catch (Exception e) {
        // set nothing
      }
    });

    // When this flips to true it will create a new motor in NT
    builder.addBooleanProperty("publish", () -> false, (v) -> {
      if (v) {
        createNewMotor();
      }
    });

  }
}
