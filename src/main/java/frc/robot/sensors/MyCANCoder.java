package frc.robot.sensors;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorTimeBase;

public class MyCANCoder extends CANCoder{

    boolean isReversed;

    public MyCANCoder(int canID, double offset, boolean isReversed) {
        super(canID);
        this.isReversed = isReversed;

        CANCoderConfiguration config = new CANCoderConfiguration();
        config.sensorCoefficient = 2 * Math.PI / 4096.0;
        config.unitString = "rad";
        config.sensorTimeBase = SensorTimeBase.PerSecond;
        config.magnetOffsetDegrees = offset;
        super.configAllSettings(config);
    }

    @Override
    public double getPosition() {
        double angle = super.getPosition();
        return angle * (this.isReversed ? -1.0 : 1.0);
    }
    
}
