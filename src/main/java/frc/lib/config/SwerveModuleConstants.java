package frc.lib.config;

import edu.wpi.first.math.geometry.Rotation2d;

public class SwerveModuleConstants {
  public final int driveMotorID;
  public final boolean driveInvert;
  public final int angleMotorID;
  public final int cancoderID;
  public final Rotation2d angleOffset;

  /**
   * Swerve Module Constants to be used when creating swerve modules.
   *
   * @param driveMotorID
   * @param driveInverted
   * @param angleMotorID
   * @param canCoderID
   * @param angleOffset
   */
  public SwerveModuleConstants(
      int driveMotorID, boolean driveInvert, int angleMotorID, int canCoderID, Rotation2d angleOffset) {
    this.driveMotorID = driveMotorID;
    this.driveInvert = driveInvert;
    this.angleMotorID = angleMotorID;
    this.cancoderID = canCoderID;
    this.angleOffset = angleOffset;
  }
}
