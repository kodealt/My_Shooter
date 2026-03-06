package frc.robot;

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
        
        gravity = 9.81; // gravity in m/s^2 (thers no need for 2 decimal places)
        
        height_of_target = 1.6002; // the distance from the top of the robot to the target in the y direction in meters

        conts = new Constants();

        radius_of_wheels = 0.5;
    }

    // holy chatgpt code </3

    public double distanceFromGoal(){ 
       if (!LimelightHelpers.getTV("limelight")) {
        return distance; // Return the last calculated distance if target is lost
    }

        //Get the vertical offset (ty) from LimelightHelpers
        double targetOffsetAngle_Vertical = LimelightHelpers.getTY("limelight");

        //Get your constants from the Constants file
        double limelightMountAngleDegrees = Constants.ShooterConstants.AngleOfLimeLight; 
        double limelightLensHeightInches = Constants.ShooterConstants.heightOfLimeLight; 
        double goalHeightInches = Constants.ShooterConstants.heightofAprilTag * 39.37; // convert meters to inches

        //Calculate the total angle to the target
        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = Math.toRadians(angleToGoalDegrees);

        //Calculate horizontal distance (X)
        // Formula: (TargetHeight - CameraHeight) / tan(TotalAngle)
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

        //Update the global distance variable (converted to meters)
        distance = distanceFromLimelightToGoalInches / 39.37; 
        
        // only one comment... why the hell is the names so long..

        return distance;
    }

	public double vel_to_rpm(double v){
		const double pi = Math.pi;
		const double radius = 3.0f; // radius in cm, TODO change this to actual radius
		double r = radius * 0.01f; // divide by 100cm -> 1m	
		// tangentional velocity is given by r * w, where w is the angular velocity, convert it to angluar vel:
		// w = v/r, to convert rad to deg(?) div by 2pi, multiply by 60 sec -> min
		// w = 30*v/(pi*r)
		double rpm = (v*30)/(pi*r);
		return rpm;

	}


    /** @return the minimum velocity needed to shoot the ball into the target
     */
    public double min_velocity(){
        distance = distanceFromGoal(); // Need to account for difference in the limelight's height and the shooter's height
        // distance  =  LimelightHelpers.getBotPose_TargetSpace("limelight")[0]; (can be used but is less reliable)
        // again, changed for readability
        g = gravity;
        h = height_of_target;
        d = distance; // who named this 
        dist = Math.sqrt(h * h + d * d);
        velocity = Math.sqrt(g*(h+dist)); // The above formula is used to calculate the minimum velocity need to shoot the projectile. It is from a physics textbook.
        // oh... didnt see this... whoops!
        
        return velocity;
    }

    public double min_angle(){
        distance = distanceFromGoal();
	// fixed for readability
    // double h = height_of_target;
	double d = distance;    
	double dist = Math.sqrt(h * h + d * d);
    angle = Math.atan((h + dist)/d); // min angle required to get to the distance --> actually terrible comment
        // return angle
        return Math.toDegrees(angle); // convert radians to degrees
    }
}
