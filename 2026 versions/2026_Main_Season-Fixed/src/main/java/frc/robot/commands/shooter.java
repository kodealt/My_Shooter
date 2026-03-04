// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Shooter_math;
import frc.robot.subsystems.ShooterSub;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class shooter extends Command {
  private Shooter_math Math_Shooter;
  private ShooterSub SubShoot;
  /** Creates a new Set_Velocity. */
  private TalonFX left_shooter, right_shooter;

  public shooter() {

    this.Math_Shooter = new Shooter_math();
    this.SubShoot = new ShooterSub();
    
    left_shooter = new TalonFX(Constants.ShooterConstants.left_shooter_id);
    right_shooter = new TalonFX(Constants.ShooterConstants.right_shooter_id);
    
    left_shooter.getConfigurator().apply(SubShoot.config(Constants.ShooterConstants.SHOOTER_KP, Constants.ShooterConstants.SHOOTER_KI, Constants.ShooterConstants.SHOOTER_KD));
      
    right_shooter.getConfigurator().apply(SubShoot.config(Constants.ShooterConstants.SHOOTER_KP, Constants.ShooterConstants.SHOOTER_KI, Constants.ShooterConstants.SHOOTER_KD));
    
    addRequirements(getRequirements());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    left_shooter.stopMotor();
    right_shooter.stopMotor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    left_shooter.setControl(SubShoot.set_velocity(Math_Shooter.min_velocity()));
    right_shooter.setControl(SubShoot.set_velocity(Math_Shooter.min_velocity()));  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    left_shooter.stopMotor();
    right_shooter.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void startup(){
    left_shooter.set(0.5);
    right_shooter.set(0.5);
  }
}
