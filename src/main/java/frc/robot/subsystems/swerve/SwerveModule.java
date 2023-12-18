package frc.robot.subsystems.swerve;

import frc.robot.RobotMap;
import frc.robot.motor.MySparkMax;
import frc.robot.sensors.MyCANCoder;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveModule {

    private final MySparkMax driveMotor;
    private final MySparkMax turningMotor;

    private final String name;

    private final RelativeEncoder driveEncoder;

    private final PIDController turningPidController;

    private final RelativeEncoder turningEncoder;

    private final MyCANCoder absoluteEncoder;

    public SwerveModule(String name, MySparkMax driveMotor, MySparkMax turningMotor,
            MyCANCoder absoluteEncoder) {

        this.name = name;

        this.driveMotor = driveMotor;
        this.turningMotor = turningMotor;
        this.absoluteEncoder = absoluteEncoder;

        driveEncoder = driveMotor.getEncoder();
        this.turningEncoder = turningMotor.getEncoder();

        driveEncoder.setPositionConversionFactor(RobotMap.SwerveConstants.kDriveEncoderRot2Meter);
        driveEncoder.setVelocityConversionFactor(RobotMap.SwerveConstants.kDriveEncoderRPM2MeterPerSec);
        turningEncoder.setPositionConversionFactor(RobotMap.SwerveConstants.kTurningEncoderRot2Rad);
        turningEncoder.setVelocityConversionFactor(RobotMap.SwerveConstants.kTurningEncoderRPM2RadPerSec);

        turningPidController = new PIDController(RobotMap.SwerveConstants.kPTurning, 0, RobotMap.SwerveConstants.kDTurning);
        turningPidController.enableContinuousInput(0, 2 * Math.PI);

        resetEncoders();
    }

    public double getAbsoluteTurningPosition() {
        return absoluteEncoder.getPosition();
    }

    public double getDrivePosition() {
        return driveEncoder.getPosition();
    }

    public double getTurningPosition() {
        return turningEncoder.getPosition();
    }

    public double getDriveVelocity() {
        return driveEncoder.getVelocity();
    }

    public double getTurningVelocity() {
        return turningEncoder.getVelocity();
    }

    public void resetEncoders() {
        driveEncoder.setPosition(0);
        turningEncoder.setPosition(absoluteEncoder.getPosition());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop();
            return;
        }
        state = SwerveModuleState.optimize(state, getState().angle);
        driveMotor.set(state.speedMetersPerSecond / RobotMap.DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        turningMotor.set(turningPidController.calculate(getTurningPosition(), state.angle.getRadians()));
        SmartDashboard.putString("Swerve[" + name + "] state", state.toString());
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(getDrivePosition(), new Rotation2d(getTurningPosition()));
    }

    public void stop() {
        driveMotor.set(0);
        turningMotor.set(0);
    }
}