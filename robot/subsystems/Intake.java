// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Intake extends SubsystemBase {
  private CANSparkMax spark1 = new CANSparkMax(6, MotorType.kBrushless);
  private WPI_TalonSRX intaker = new WPI_TalonSRX(5);
  private final RelativeEncoder m_intakeEncoder;
  private double intakeError;
  private double intakeSetpoint;
  public boolean intakeIn = true;

 
  
  final double wheelCircumference = 0.478778720406;

    public double getRotationsPerSecond(AbsoluteEncoder m_intakeEncoder) { // Meters per second
    return m_intakeEncoder.getVelocity()/409.6;
  }
      public double getNumofRotations(AbsoluteEncoder m_intakeEncoder) { // Meters per second
    return m_intakeEncoder.getPosition();
  }
  public double getAnglePosition(CANSparkMax spark1) {
    return m_intakeEncoder.getPosition()*10;
  }

  /** Creates a new ExampleSubsystem. */
  public void pull(boolean Ypressed, boolean Xpressed) {
    if (Ypressed) {
      intaker.set(.3);
    }
    else if (Xpressed) {
      intaker.set(-.3);
    }
    else {
      intaker.stopMotor();
    }
  }

  public void wrist(boolean Bpressed, Boolean Apressed) {
    if (Bpressed && intakeIn == true) {
      intakeSetpoint = -140;
      while(getAnglePosition(spark1) > -160) {
        intakeError = getAnglePosition(spark1) - intakeSetpoint;
        spark1.set(intakeError/360);
        SmartDashboard.putNumber("intake error", intakeError);
        SmartDashboard.putNumber("spark input (-1 to 1)", intakeError/360);
      }
      intakeIn = false;
    }
    else if (Apressed && intakeIn == false) {
      intakeSetpoint = -40;
      while(getAnglePosition(spark1) < -20) {
        intakeError = getAnglePosition(spark1) - intakeSetpoint;
        spark1.set(intakeError/360);
        SmartDashboard.putNumber("intake error", intakeError);
        SmartDashboard.putNumber("spark input (-1 to 1)", intakeError/360);
      }
      intakeIn = true;
    }
    else {
      spark1.stopMotor();
    }
    SmartDashboard.putNumber("Intake Encoder Position", getAnglePosition(spark1));
    SmartDashboard.putNumber("Intake Encoder Velocity", m_intakeEncoder.getVelocity());
  }
  
  public Intake() {
    m_intakeEncoder = spark1.getEncoder();
    m_intakeEncoder.setPosition(0);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
