package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.swerve.SwerveSubsystem;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import edu.wpi.first.math.filter.SlewRateLimiter;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import frc.robot.RobotMap;

public class SwerveTeleop extends CommandBase {

    private DoubleSupplier xSpdFunction, ySpdFunction, turningSpdFunction;
    private BooleanSupplier fieldOrientedFunction;
    private SlewRateLimiter xLimiter, yLimiter, turningLimiter;

    private final SwerveSubsystem swerve;

    public SwerveTeleop(
            SwerveSubsystem swerve,
            DoubleSupplier xSpdFunction,
            DoubleSupplier ySpdFunction,
            DoubleSupplier turningSpdFunction,
            BooleanSupplier fieldOrientedFunction
    ) {

        this.swerve = swerve;

        this.xSpdFunction = xSpdFunction;
        this.ySpdFunction = ySpdFunction;
        this.turningSpdFunction = turningSpdFunction;
        this.fieldOrientedFunction = fieldOrientedFunction;
        this.xLimiter = new SlewRateLimiter(RobotMap.DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
        this.yLimiter = new SlewRateLimiter(RobotMap.DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
        this.turningLimiter = new SlewRateLimiter(RobotMap.DriveConstants.kTeleDriveMaxAngularAccelerationUnitsPerSecond);

        addRequirements(swerve);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        // 1. Get real-time joystick inputs
        double xSpeed = xSpdFunction.getAsDouble();
        double ySpeed = ySpdFunction.getAsDouble();
        double turningSpeed = turningSpdFunction.getAsDouble();

        // 2. Apply deadband
        xSpeed = Math.abs(xSpeed) > RobotMap.OIConstants.kDeadband ? xSpeed : 0.0;
        ySpeed = Math.abs(ySpeed) > RobotMap.OIConstants.kDeadband ? ySpeed : 0.0;
        turningSpeed = Math.abs(turningSpeed) > RobotMap.OIConstants.kDeadband ? turningSpeed : 0.0;

        // 3. Make the driving smoother
        xSpeed = xLimiter.calculate(xSpeed) * RobotMap.DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
        ySpeed = yLimiter.calculate(ySpeed) * RobotMap.DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
        turningSpeed = turningLimiter.calculate(turningSpeed)
                * RobotMap.DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;

        // 4. Construct desired chassis speeds
        ChassisSpeeds chassisSpeeds;
        if (fieldOrientedFunction.getAsBoolean()) {
            // Relative to field
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                    xSpeed, ySpeed, turningSpeed, swerve.getRotation2d());
        } else {
            // Relative to robot
            chassisSpeeds = new ChassisSpeeds(xSpeed, ySpeed, turningSpeed);
        }

        // 5. Convert chassis speeds to individual module states
        SwerveModuleState[] moduleStates = RobotMap.SwerveConstants.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);

        // 6. Output each module states to wheels
        swerve.setModuleStates(moduleStates);

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.swerve.stopModules();
    }


}