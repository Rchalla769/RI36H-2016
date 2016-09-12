package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by rohanchalla on 9/10/16.
 */

@TeleOp(name="Teleop", group="Final")
public class Teleop extends OpMode
{
    private static final int SERVO_ON_OFF_TIME = 500;
    private static final double CLOSE_SHOOTER_GATE = 0.9;
    private static final double OPEN_SHOOTER_GATE = 0.3;

    private DcMotor rightDrive, leftDrive, intake, shooter1, shooter2, shooter3;
    private Servo shooterGate;
    private ElapsedTime servoTime = new ElapsedTime();

    @Override
    public void init()
    {
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        shooter3 = hardwareMap.dcMotor.get("intake");
        intake = hardwareMap.dcMotor.get("shooter1");
        shooter2 = hardwareMap.dcMotor.get("shooter2");
        shooter1 = hardwareMap.dcMotor.get("shooter3");
        shooterGate = hardwareMap.servo.get("shooterGate");

        shooter3.setDirection(DcMotor.Direction.REVERSE);
        shooter2.setDirection(DcMotor.Direction.REVERSE);
        shooter1.setPowerFloat();
        shooter2.setPowerFloat();
        shooter3.setPowerFloat();

        rightDrive.setPower(0.0);
        leftDrive.setPower(0.0);
        intake.setPower(0.0);
        shooter1.setPower(0.0);
        shooter2.setPower(0.0);
        shooter3.setPower(0.0);
        shooterGate.setPosition(CLOSE_SHOOTER_GATE);
    }

    @Override
    public void loop()
    {
        drive();
        checkButtons();
    }

    public void drive()
    {
        float direction = -gamepad1.left_stick_y;
        float throttle = gamepad1.right_stick_x;
        float right = throttle - direction;
        float left = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        if (Math.abs(right) < 0.1)
        {
            right = 0;
        }

        if (Math.abs(left) < 0.1)
        {
            left = 0;
        }

        // write the values to the motors
        rightDrive.setPower(right);
        leftDrive.setPower(left);
    }

    public void checkButtons()
    {
        if(gamepad1.left_trigger > 0.1)
        {
            shooter1.setPower(1.0);
            shooter2.setPower(1.0);
            shooter3.setPower(1.0);
        }
        else if(gamepad1.left_bumper)
        {
            shooter1.setPower(0.0);
            shooter2.setPower(0.0);
            shooter3.setPower(0.0);
        }

        if(gamepad1.right_trigger > 0.1)
        {
            shooterGate.setPosition(OPEN_SHOOTER_GATE);
            servoTime.reset();
        }
        else
        {
            shooterGate.setPosition(CLOSE_SHOOTER_GATE);
        }

        if(gamepad1.a)
        {
            intake.setPower(1.0);
        }
        else if(gamepad1.b)
        {
            intake.setPower(0.0);
        }
        else if(gamepad1.y)
        {
            intake.setPower(-1.0);
        }
    }
}
