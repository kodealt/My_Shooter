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
public class pivot extends Command {
  private TalonFX m_pivot;
  private ShooterSub SubShoot;
  private Shooter_math Math_Shooter;
  /** Creates a new Set_Angle. */
  public pivot() {
    
    SubShoot = new ShooterSub();
    Math_Shooter = new Shooter_math();
    m_pivot = new TalonFX(Constants.ShooterConstants.pivot_id);
    m_pivot.getConfigurator().apply(SubShoot.config(Constants.ShooterConstants.AIM_KP, Constants.ShooterConstants.AIM_KI, Constants.ShooterConstants.AIM_KD));
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(getRequirements());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_pivot.stopMotor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_pivot.setControl(SubShoot.set_angle(Math_Shooter.min_angle()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_pivot.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void startup(){
    m_pivot.setControl(SubShoot.set_angle(Math_Shooter.min_angle()));
  }
}
