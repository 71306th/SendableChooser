// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Forward1m;
import frc.robot.commands.Forward2m;
import frc.robot.commands.Turn;
import frc.robot.commands.modes.Staircasing;
import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Chassis m_drive = new Chassis();

  private final Forward1m forward1m = new Forward1m(m_drive);
  private final Forward2m forward2m = new Forward2m(m_drive);
  private final Turn turnRight = new Turn(m_drive, -90);
  private final Turn turnLeft = new Turn(m_drive, 90);
  private final Staircasing staircasing = new Staircasing();

  private final XboxController js1 = new XboxController(Constants.JoystickConstants.kDriverControllerPort);

  SendableChooser<Command> m_Chooser = new SendableChooser<>();

  public RobotContainer() {
    m_drive.setDefaultCommand(new RunCommand(()->{
      m_drive.arcadeDrive(js1.getLeftY()*0.8, js1.getRightX()*0.8);
      if(js1.getLeftY()>0.3 || js1.getLeftY()<-0.3){
        js1.setRumble(RumbleType.kBothRumble, 1);
      }else{
        js1.setRumble(RumbleType.kBothRumble, 0);
      }
      if(js1.getRightX()>0.3 || js1.getRightX()<-0.3){
        js1.setRumble(RumbleType.kBothRumble, 1);
      }else{
        js1.setRumble(RumbleType.kBothRumble, 0);
      }
    }, m_drive));
    configureBindings();

    m_Chooser.setDefaultOption("Forward 1 meter", forward1m);
    m_Chooser.addOption("forward 2 meters", forward2m);
    m_Chooser.addOption("turn left", turnLeft);
    m_Chooser.addOption("turn right", turnRight);
    m_Chooser.addOption("staricasing", staircasing);

    SmartDashboard.putData("Choosing", m_Chooser);
  }
  

  private void configureBindings() {

  }

  public Command getAutonomousCommand() {
    return m_Chooser.getSelected();
  }
}