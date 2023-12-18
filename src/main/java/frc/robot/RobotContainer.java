// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.SwerveTeleop;
import frc.robot.motor.MySparkMax;
import frc.robot.sensors.MyButton;
import frc.robot.sensors.MyCANCoder;
import frc.robot.subsystems.swerve.SwerveModule;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import edu.wpi.first.wpilibj.SPI;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // motors
  public MySparkMax leftFrontDrive, leftFrontTurn;
  public MySparkMax rightFrontDrive, rightFrontTurn;
  public MySparkMax leftBackDrive, leftBackTurn;
  public MySparkMax rightBackDrive, rightBackTurn;

  // sensors
  public AHRS gyro;
  public MyCANCoder leftFrontEncoder, rightFrontEncoder, leftBackEncoder, rightBackEncoder;

  // swerve
  public SwerveModule leftFrontModule, rightFrontModule, leftBackModule, rightBackModule;
  public SwerveSubsystem swerve;

  // commands
  public SwerveTeleop swerveTeleop;

  // OI n stuff
  public OI oi;
  public MyButton fieldButton;

  public RobotContainer() {

    // motor inits
    leftFrontDrive = new MySparkMax(
      RobotMap.CANConstants.leftFrontDriveID, true, RobotMap.DeviceConstants.leftFrontDriveReversed);
    leftFrontDrive.brake();
    leftFrontTurn = new MySparkMax(
      RobotMap.CANConstants.leftFrontTurningID, true, RobotMap.DeviceConstants.leftFrontTurningReversed);
    leftFrontTurn.brake();
    rightFrontDrive = new MySparkMax(
      RobotMap.CANConstants.rightFrontDriveID, true, RobotMap.DeviceConstants.rightFrontDriveReversed);
    rightFrontDrive.brake();
    rightFrontTurn = new MySparkMax(
      RobotMap.CANConstants.rightFrontTurningID, true, RobotMap.DeviceConstants.rightFrontTurningReversed);
    rightFrontTurn.brake();
    leftBackDrive = new MySparkMax(
      RobotMap.CANConstants.leftBackDriveID, true, RobotMap.DeviceConstants.leftBackDriveReversed);
    leftBackDrive.brake();
    leftBackTurn = new MySparkMax(
      RobotMap.CANConstants.leftBackTurningID, true, RobotMap.DeviceConstants.leftBackTurningReversed);
    leftBackTurn.brake();
    rightBackDrive = new MySparkMax(
      RobotMap.CANConstants.rightBackDriveID, true, RobotMap.DeviceConstants.rightBackDriveReversed);
    rightBackDrive.brake();
    rightBackTurn = new MySparkMax(
      RobotMap.CANConstants.rightBackTurningID, true, RobotMap.DeviceConstants.rightBackTurningReversed);
    rightBackTurn.brake();

    // sensor inits
    gyro = new AHRS(SPI.Port.kMXP);

    leftFrontEncoder = new MyCANCoder(RobotMap.CANConstants.leftFrontEncoderID, RobotMap.DeviceConstants.leftFrontEncoderOffset, RobotMap.DeviceConstants.leftFrontEncoderReversed);
    rightFrontEncoder = new MyCANCoder(RobotMap.CANConstants.rightFrontEncoderID, RobotMap.DeviceConstants.rightFrontEncoderOffset, RobotMap.DeviceConstants.rightFrontEncoderReversed);
    leftBackEncoder = new MyCANCoder(RobotMap.CANConstants.leftBackEncoderID, RobotMap.DeviceConstants.leftBackEncoderOffset, RobotMap.DeviceConstants.leftBackEncoderReversed);
    rightBackEncoder = new MyCANCoder(RobotMap.CANConstants.rightBackEncoderID, RobotMap.DeviceConstants.rightBackEncoderOffset, RobotMap.DeviceConstants.rightBackEncoderReversed);
    

    // swerve inits
    leftFrontModule = new SwerveModule(
      "leftFrontModule", 
      leftFrontDrive,
      leftFrontTurn,
      leftFrontEncoder
    );

    rightFrontModule = new SwerveModule(
      "rightFrontModule", 
      rightFrontDrive,
      rightFrontTurn,
      rightFrontEncoder
    );

    leftBackModule = new SwerveModule(
      "leftBackModule", 
      leftBackDrive,
      leftBackTurn,
      leftBackEncoder
    );

    rightBackModule = new SwerveModule(
      "rightBackModule", 
      rightBackDrive,
      rightBackTurn,
      rightBackEncoder
    );

    swerve = new SwerveSubsystem(leftFrontModule, rightFrontModule, leftBackModule, rightBackModule, gyro);

    // OI inits
    oi = new OI();
    fieldButton = new MyButton(oi.getDriverXbox(), OI.XBOX_A);

    // swerve command
    swerveTeleop = new SwerveTeleop(
      swerve, 
      oi::getDriverXboxLeftX, 
      oi::getDriverXboxLeftY, 
      oi::getDriverXboxRightX,
      fieldButton::isPressed
    );

  }

  public Command getAutonomousCommand() {
    return null;
  }

}
