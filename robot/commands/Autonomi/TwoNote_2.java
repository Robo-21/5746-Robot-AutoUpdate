// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomi;

import frc.robot.commands.DriveAutoCommand;
import frc.robot.commands.IntakeAutoCommand;
import frc.robot.commands.LaunchAutoCommand;
import frc.robot.commands.ReverseWristAutoCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** An example command that uses an example subsystem. */
public class TwoNote_2 extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Drivetrain m_drivetrain;
  private Launcher m_launcher;
  private Intake m_intake;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TwoNote_2(Launcher launcher, Intake intake, Drivetrain drivetrain) {
    m_launcher = launcher;
    m_intake = intake;
    m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(launcher, intake, drivetrain);
    new SequentialCommandGroup(
      new ParallelCommandGroup(
        new LaunchAutoCommand(m_launcher, 1),
        new IntakeAutoCommand(m_intake, 1, .5, false)
      ),
      new ParallelCommandGroup(
        new DriveAutoCommand(m_drivetrain, 1, 0.7),
        new ReverseWristAutoCommand(m_intake),
        new IntakeAutoCommand(m_intake, 3, 0, true)
      ),
      new ParallelCommandGroup(
        new DriveAutoCommand(m_drivetrain, -1, -.7),
        new ReverseWristAutoCommand(m_intake)
      ),
      new ParallelCommandGroup(
        new LaunchAutoCommand(m_launcher, 1),
        new IntakeAutoCommand(m_intake, 1, .5, false)
      )
    );
  }

}
