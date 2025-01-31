package OpMode.TeleOp;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.*;
public class Drive {

        //mecanum motors
    public static DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
    public static DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
    public static DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
    public static DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");
        //mecanum reverse
    public static double multiplayer = 1.6;
    public static void drive(){
        //mecanum functions
        double y = -gamepad1.left_stick_y * multiplayer;
        double x = gamepad1.left_stick_x * multiplayer ;
        double rx = gamepad1.right_stick_x * multiplayer;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;
        frontRightMotor.setPower(frontRightPower);
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);


	}
}


