// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class Turn extends CommandBase {
  private final Chassis drive;

  double output, goalAngle, currentAngle, error, kP=0.0124;

  public Turn(Chassis subsystem, int goal) {
    drive = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
    goalAngle = goal;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentAngle = drive.getRotation2d().getDegrees();
    error = -goalAngle + currentAngle;
    output = kP*error;

    drive.arcadeDrive(0, output);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(error)<5) {
      drive.arcadeDrive(0, 0);
      return true;
    }else return false;
  }
}
