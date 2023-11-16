// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {
  private final WPI_TalonSRX leftfront;
  private final WPI_TalonSRX leftrear;
  private final WPI_TalonSRX rightfront;
  private final WPI_TalonSRX rightrear;

  private final MotorControllerGroup left;
  private final MotorControllerGroup right;

  private final DifferentialDrive drive;

  private final CANCoder cancoder;

  private final AHRS gyro;

  /** Creates a new ExampleSubsystem. */
  public Chassis() {
    leftfront = new WPI_TalonSRX(Constants.Chassis.LFMotor);
    leftrear = new WPI_TalonSRX(Constants.Chassis.LRMotor);
    rightfront = new WPI_TalonSRX(Constants.Chassis.RFMotor);
    rightrear = new WPI_TalonSRX(Constants.Chassis.RRMotor);

    left = new MotorControllerGroup(leftfront, leftrear);
    right = new MotorControllerGroup(rightfront, rightrear);

    drive = new DifferentialDrive(left, right);

    cancoder = new CANCoder(Constants.Chassis.Cancoder);

    gyro = new AHRS(SPI.Port.kMXP);

    left.setInverted(false);
    right.setInverted(true);
    cancoder.setPosition(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Position", cancoder.getPosition()*(Constants.Chassis.wheelMeters*Math.PI/360));
    SmartDashboard.putNumber("Angle", getRotation2d().getDegrees());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void arcadeDrive(double x, double z) {
    drive.arcadeDrive(-x, -z);
  }

  public double getPosition() {
    return cancoder.getPosition()*(Constants.Chassis.wheelMeters*Math.PI/360);
  }

  public void setPosToZero() {
    cancoder.setPosition(0);
  }

  public Rotation2d getRotation2d() {
    return gyro.getRotation2d();
  }

  public void resetGyro() {
    gyro.reset();
  }
}
