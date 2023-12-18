// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed = 0.0; to reduce verbosity.
 */
public final class RobotMap {

  public static final class SwerveConstants {

    public static final double kWheelDiameterMeters = Units.inchesToMeters(4); // number of inches of wheel diameter, converted to meters
    public static final double kDriveMotorGearRatio = 1 / 5.8462;
    public static final double kTurningMotorGearRatio = 1 / 18.0;
    public static final double kDriveEncoderRot2Meter = kDriveMotorGearRatio * Math.PI * kWheelDiameterMeters;
    public static final double kTurningEncoderRot2Rad = kTurningMotorGearRatio * 2 * Math.PI;
    public static final double kDriveEncoderRPM2MeterPerSec = kDriveEncoderRot2Meter / 60;
    public static final double kTurningEncoderRPM2RadPerSec = kTurningEncoderRot2Rad / 60;
    public static final double kPTurning = 0.5; // TODO need to adjust this
    public static final double kDTurning = 0.0; // TODO need to adjust this

    public static final double kTrackWidth = Units.inchesToMeters(21);
    // Distance between right and left wheels
    public static final double kWheelBase = Units.inchesToMeters(25.5);
    // Distance between front and back wheels
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
      new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
      new Translation2d(kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, kTrackWidth / 2)
    );
  }

  public static final class DriveConstants {

    public static final double kPhysicalMaxSpeedMetersPerSecond = 5.0; // TODO I think we need to figure this out as well
    public static final double kPhysicalMaxAngularSpeedRadiansPerSecond = 2 * 2 * Math.PI;

    public static final double kTeleDriveMaxAccelerationUnitsPerSecond = 3.0; // TODO I think we need to figure this out as well
    public static final double kTeleDriveMaxAngularAccelerationUnitsPerSecond = 3.0; // TODO I think we need to figure this out as well
    
    public static final double kTeleDriveMaxSpeedMetersPerSecond = kPhysicalMaxSpeedMetersPerSecond / 4; // TODO I think we need to figure this out as well
    public static final double kTeleDriveMaxAngularSpeedRadiansPerSecond = kPhysicalMaxAngularSpeedRadiansPerSecond / 4; // TODO I think we need to figure this out as well
  }
  
  public static final class OIConstants {

    public static final int driverID = 0;
    public static final int operatorID = 1;

    public static final double kDeadband = 0.05;
  }

  public static final class CANConstants {
    
    public static final int leftFrontDriveID = 1;
    public static final int leftFrontTurningID = 5;
    public static final int rightFrontDriveID = 2;
    public static final int rightFrontTurningID = 6;
    public static final int leftBackDriveID = 4;
    public static final int leftBackTurningID = 8;
    public static final int rightBackDriveID = 3;
    public static final int rightBackTurningID = 7;

    public static final int leftFrontEncoderID = 9;
    public static final int rightFrontEncoderID = 10;
    public static final int leftBackEncoderID = 12;
    public static final int rightBackEncoderID = 11;

  }

  public static final class DeviceConstants {

    public static final boolean leftFrontDriveReversed = false;
    public static final boolean leftFrontTurningReversed = false;
    public static final boolean rightFrontDriveReversed = false;
    public static final boolean rightFrontTurningReversed = false;
    public static final boolean leftBackDriveReversed = false;
    public static final boolean leftBackTurningReversed = false;
    public static final boolean rightBackDriveReversed = false;
    public static final boolean rightBackTurningReversed = false;

    public static final boolean leftFrontEncoderReversed = false;
    public static final boolean rightFrontEncoderReversed = false;
    public static final boolean leftBackEncoderReversed = false;
    public static final boolean rightBackEncoderReversed = false;

    public static final double leftFrontEncoderOffset = 2.9003696547;
    public static final double rightFrontEncoderOffset = -23.994097361;
    public static final double leftBackEncoderOffset = 158.81805664;
    public static final double rightBackEncoderOffset = -18.369084208;

  }

}
