package org.frcteam5066.mk3;

public class Constants {

    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 13;
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 14;
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 12;
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 17;

    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 11;
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 18;
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 16;
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 15;

    public static final int DRIVETRAIN_FRONT_LEFT_ENCODER_PORT = 42;
    public static final int DRIVETRAIN_FRONT_RIGHT_ENCODER_PORT = 41;
    public static final int DRIVETRAIN_BACK_LEFT_ENCODER_PORT = 44;
    public static final int DRIVETRAIN_BACK_RIGHT_ENCODER_PORT = 43;

    //public static final int PIGEON_PORT = 51;

    // In degrees
    public static final double DRIVETRAIN_FRONT_LEFT_ENCODER_OFFSET = Math.toRadians(230.010);
    public static final double DRIVETRAIN_FRONT_RIGHT_ENCODER_OFFSET = Math.toRadians(225.967 - 180);
    public static final double DRIVETRAIN_BACK_LEFT_ENCODER_OFFSET = Math.toRadians(99.316);
    public static final double DRIVETRAIN_BACK_RIGHT_ENCODER_OFFSET = Math.toRadians(84.375 + 180 );

    public static final int PRIMARY_CONTROLLER_PORT = 0;

    public static final int CANdleID = 1;

}
