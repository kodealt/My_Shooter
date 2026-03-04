package frc.robot;



import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Shooter_math {
    double velocity;
    double distance;
    double angle;
    double gravity;
    double height_of_target;
    Constants conts;
    double radius_of_wheels;


    public Shooter_math(){
        velocity = 0; // the velocity of the shooter in m/s

        distance = 0; // convert inches to meters
        
        angle = 0; // the angle of the shooter in degrees
        
        gravity = 9.81; // gravity in m/s^2
        
        height_of_target = 1.6002; // the distance from the top of the robot to the target in the y direction in meters

        conts = new Constants();

        radius_of_wheels = 0.5;
    }

    public double distanceFromGoal(){ 
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = table.getEntry("ty");
        double targetOffsetAngle_Vertical = ty.getDouble(0.0);

        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = Constants.ShooterConstants.AngleOfLimeLight; 

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = Constants.ShooterConstants.heightOfLimeLight; 

        // distance from the target to the floor
        double goalHeightInches = Constants.ShooterConstants.heightOfTarget * 39.37; // convert meters to inches

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
        return distanceFromLimelightToGoalInches/39.37; // convert inches to meters
    }




    /** @return the minimum velocity needed to shoot the ball into the target
     */
    public double min_velocity(){
        distance = distanceFromGoal(); // Need to account for difference in the limelight's height and the shooter's height


        velocity = Math.sqrt(gravity*(height_of_target+Math.sqrt(Math.pow(height_of_target, 2)+Math.pow(distance, 2)))); // The above formula is used to calculate the minimum velocity need to shoot the projectile. It is from a physics textbook.
        
        
        return velocity;
    }

    public double min_angle(){
        distance = distanceFromGoal();
        
        angle = Math.atan((height_of_target+Math.sqrt(Math.pow(height_of_target, 2)+Math.pow(distance, 2)))/distance); // min angle required to get to the distance
        
        return angle*(180/Math.PI); // convert radians to degrees
    }

    
}
