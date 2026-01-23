// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

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
  final ArrayList<Long> usedCanIDs = new ArrayList<>();

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
    // Avoids a ConcurrentModificationException (it turns out something else
    // was throwing it, but this can't hurt.)
    synchronized (usedCanIDs) {
    if (!usedCanIDs.contains((long) queuedCanID)) {
      switch (queuedMotorModel) {
        case SparkMax:
          SparkMaxWrapper wrapper = new SparkMaxWrapper(queuedCanID);
          // NOTE: This avoid a common ConcurrentModificationException
          // due to updating the HashMap of NT table paths to Sendable objects
          // during the updateValues() loop.
          SmartDashboard.postListenerTask(() -> 
          SmartDashboard.putData("Motors/" + queuedCanID, wrapper));
            usedCanIDs.add((long) queuedCanID);
          break;
        default:
          break;
      }
    }
  }
  }

  public long[] getUsedCanIds() {
    // IMPORTANT NOTE: Thie synchronized block prevents a
    // ConcurrentModificationException.
    synchronized (usedCanIDs) {
      long[] out = new long[usedCanIDs.size()];
      for (int i = 0; i < out.length; i++) {
        out[i] = usedCanIDs.get(i);
      }
      return out;
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

    builder.addIntegerArrayProperty("Used CAN IDs", this::getUsedCanIds, null);
  }
}
