package OpMode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class AlaBunRau extends LinearOpMode {

    double multiplayer = 1.6;

    Boolean GameMode = true;



    @Override
    public void runOpMode() throws InterruptedException {

        //values

        int VSPosUp = 1150; //2495 is max  // pozitia bara
        int hangPos =1440;
        int VBasUp = 2500; // pozitie basket sus
        //int HSPosExt = 100; ///1210 is max
        int extended_position = 1200;
        int retracted_position = 0;

        //mecanum motors

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");

        //slider motors
        DcMotor VSLMotor = hardwareMap.dcMotor.get("verticalLeft");
        DcMotor VSRMotor = hardwareMap.dcMotor.get("verticalRight");
        DcMotor HSldMotor = hardwareMap.dcMotor.get("horizontalSlide");

        //mecanum reverse
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //slider reverse
        VSRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        HSldMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //sliders reset/start encoder
        VSLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VSRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HSldMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        VSRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        VSLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        HSldMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        VSLMotor.setTargetPosition(0);
        VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        VSRMotor.setTargetPosition(0);
        VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //HSldMotor.setTargetPosition(0);
        //HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //declare servos

        Servo OutClaw = hardwareMap.servo.get("outClaw");
        Servo OutAngle = hardwareMap.servo.get("outAngle");
        Servo OutLeftPivot = hardwareMap.servo.get("outLeftPivot");
        Servo OutRightPivot = hardwareMap.servo.get("outRightPivot");
        Servo InClaw = hardwareMap.servo.get("inClaw");
        Servo InTwist = hardwareMap.servo.get("inTwist");
        Servo InAngle = hardwareMap.servo.get("inAngle");
        Servo InLeftPivot = hardwareMap.servo.get("inLeftPivot");
        Servo InRightPivot = hardwareMap.servo.get("inRightPivot");

        //reverse servos

        OutRightPivot.setDirection(Servo.Direction.REVERSE);
        InRightPivot.setDirection(Servo.Direction.REVERSE);
        InAngle.setDirection(Servo.Direction.REVERSE);
        OutClaw.setDirection(Servo.Direction.REVERSE);
        //InTwist.setDirection(Servo.Direction.REVERSE);


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //mecanum functions
            double y = -gamepad1.left_stick_y * multiplayer;
            double x = gamepad1.left_stick_x * multiplayer;
            double rx = -gamepad1.right_stick_x * multiplayer;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            //sliders function
            int VSliderPos = VSLMotor.getCurrentPosition();
            int HSld_value = HSldMotor.getCurrentPosition();
            int HSld_dest_value = HSldMotor.getTargetPosition();
            int VSR_value = VSRMotor.getCurrentPosition();
            int VSL_value = VSLMotor.getCurrentPosition();

            //SPEEEED:)
            if(gamepad1.share){
                multiplayer = 0.7;
            }

            if(gamepad1.options){
                multiplayer =1.6;
            }
            // Change Game mode (Cos sau bara) // cos = true ; bara = false;
            if(gamepad1.dpad_left){
                GameMode = true;
            }

            if(gamepad1.dpad_right){
                GameMode = false;
            }

            if(gamepad1.triangle && GameMode == true){

            }

            if(gamepad1.triangle && GameMode == false){

            }

            if(gamepad1.cross && GameMode == true){

            }

            if(gamepad1.cross && GameMode == false){

            }

            if(gamepad1.triangle && GameMode == true){

            }

            if(gamepad1.triangle && GameMode == false){

            }

            if(gamepad1.cross && GameMode == true){

            }

            if(gamepad1.cross && GameMode == false){

            }

        }
    }
}
