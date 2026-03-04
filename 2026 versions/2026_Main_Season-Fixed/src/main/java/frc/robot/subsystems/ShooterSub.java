package frc.robot.subsystems;

import java.util.Optional;


import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;

public class ShooterSub {
    Optional<DriverStation.Alliance> alliance;
    int targetTagID;
    NetworkTable limelightTable;
    public ShooterSub(){
        alliance = DriverStation.getAlliance();
        if (alliance.isPresent() && alliance.get() == DriverStation.Alliance.Red) {
        targetTagID = Constants.ShooterConstants.LimeLightID_RED; // Example Red ID
    } else {
        targetTagID = Constants.ShooterConstants.LimeLightID_BLUE; // Example Blue ID
    }       

// Then tell Limelight to prioritize that specific ID
    limelightTable.getEntry("priorityid").setNumber(targetTagID);

    }
    /** @param velocity - the velocity of the shooter in m/s */
    public ControlRequest set_velocity(double velocity){
        
        ControlRequest req = new VelocityVoltage(velocity/(Constants.ShooterConstants.radius_of_wheels*2*Math.PI)); // converts it into RPS and then asks the motor to match it
        
        
        return req;
    }

    public ControlRequest set_angle(double angle){
        ControlRequest req = new PositionVoltage(angle); // asks the motor to match the angle required.
        return req;
    }

    /** @param P - Proportional
     * @param I - Integral
     * @param D - Derivative
     * @return TalonFXConfiguration with the specified PID values for the shooter
      */
    public TalonFXConfiguration config(double P, double I, double D){
        TalonFXConfiguration configs = new TalonFXConfiguration();
         // Set the PID slot to 0

        // Set the Slot 0 gains (for Velocity control) - has more values than just PID
        Slot0Configs slot0 = configs.Slot0; 

        slot0.kS = 0.25; // Voltage to overcome static friction (start with 0.1 - 0.2)

        slot0.kV = 0.12; // Velocity feedforward (Volts per RPS)
        
        slot0.kP = P; // Proportional gain (Volts per unit of error)
        
        slot0.kI = I;  // Integral gain
        
        slot0.kD = D;  // Derivative gain

        return configs;
    }

    public boolean HubActive() {
        String gameData = DriverStation.getGameSpecificMessage();
        double time = DriverStation.getMatchTime();
        if (gameData.isEmpty() || alliance.isEmpty()) return false;

        // 'R' or 'B' tells us who is INACTIVE first
        boolean weAreInactiveFirst = (gameData.charAt(0) == 'R' && alliance.get() == DriverStation.Alliance.Red) ||
                                    (gameData.charAt(0) == 'B' && alliance.get() == DriverStation.Alliance.Blue);

        // Shift 1 starts at 130s. If we are inactive first, we want to start at 110s (5s before Shift 2)
        // If we are active first (meaning the OTHER alliance was inactive first), we start at 135s
        if (weAreInactiveFirst) {
            // We activate at Shifts 2 (105s) and 4 (55s)
            return (time <= 110.0 && time > 105.0) || (time <= 60.0 && time > 55.0);
        } else {
            // We activate at Shifts 1 (130s) and 3 (80s)
            return (time <= 135.0 && time > 130.0) || (time <= 85.0 && time > 80.0);
        }
}
}
