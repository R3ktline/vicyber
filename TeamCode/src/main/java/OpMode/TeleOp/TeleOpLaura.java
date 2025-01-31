package OpMode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class TeleOpLaura extends LinearOpMode {

    double multiplayer = 1.6;

    Boolean GameMode = true;

    boolean everythingDoneVertical = true;
    boolean everythingDoneHorizontal = true;

    boolean alliancerung = true;
    boolean ascentStarted = false;

    boolean everythingDoneVerticalRetraction = true;
    boolean everythingDoneHorizontalRetraction = true;
    boolean drop_to_intake = false;
    ElapsedTime timervertical = new ElapsedTime();
    ElapsedTime timeralliance = new ElapsedTime();
    ElapsedTime timerhorizontal = new ElapsedTime();

    DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
    DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
    DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
    DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");

    //slider motors
    DcMotor VSLMotor = hardwareMap.dcMotor.get("verticalLeft");
    DcMotor VSRMotor = hardwareMap.dcMotor.get("verticalRight");
    DcMotor HSldMotor = hardwareMap.dcMotor.get("horizontalSlide");


    Servo OutClaw = hardwareMap.servo.get("outClaw");
    Servo OutAngle = hardwareMap.servo.get("outAngle");
    Servo OutLeftPivot = hardwareMap.servo.get("outLeftPivot");
    Servo OutRightPivot = hardwareMap.servo.get("outRightPivot");
    Servo InClaw = hardwareMap.servo.get("inClaw");
    Servo InTwist = hardwareMap.servo.get("inTwist");
    Servo InAngle = hardwareMap.servo.get("inAngle");
    Servo InLeftPivot = hardwareMap.servo.get("inLeftPivot");
    Servo InRightPivot = hardwareMap.servo.get("inRightPivot");



    boolean init = false;
    boolean isHorizontalIn = true;
    boolean isIntakeLowered = false;
    boolean to_bara_ready=false;
    boolean to_Transfer = false;
    boolean Start_Basket = false;
    boolean StartIntake=false;
    boolean Start_Transfer=false;
    boolean is_Outake=false;
    int HorizontalTarget = 0;
    boolean VerticalSliderIn=true;
    boolean isOutakeReady = false;
    boolean isIntakeReady = true;
    boolean isBasketReady = false;
    boolean start_specimen=false;
    boolean isAgatatReady=false;
    boolean to_Drop=false;
    boolean AgatatStart=false;
    int VerticalSpecimenTarget=0;///De scris val - poz glisiera max cand agata specimen
    int outHorizontal=0; ///De scris val - pozitia glisierei orizontale pt extins
    int inVerticalPosition=0; ///De scris val - poz vertical init
    int VerticalTarget = 0; ///De scris val - poz init slider vertical
    int BasketPositon = 0; ///De scris val - poz glisiera verticala basket
    int HorizontalRetras=0; ///De scris val
    int inHorizontal=0; ///De scris val
    ElapsedTime Intake_timer=new ElapsedTime();
    ElapsedTime Transfer_timer=new ElapsedTime();
    ElapsedTime Human_timer=new ElapsedTime();
    ElapsedTime Basket_timer=new ElapsedTime();
    ElapsedTime Basket_drop_timer=new ElapsedTime();
    ElapsedTime Specimen_Timer=new ElapsedTime();





    @Override
    public void runOpMode() throws InterruptedException {


        //mecanum motors

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

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //TODO:declare servos


        //reverse servos

        OutRightPivot.setDirection(Servo.Direction.REVERSE);
        InRightPivot.setDirection(Servo.Direction.REVERSE);
        InAngle.setDirection(Servo.Direction.REVERSE);
        OutClaw.setDirection(Servo.Direction.REVERSE);
        //InTwist.setDirection(Servo.Direction.REVERSE);


        //TODO: AICI START
        waitForStart();
        if (isStopRequested()) return;
        to_Init();
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




            horizontalSliders();
            verticalSliders();

            ///Sistem = Intake pentru cos
            ///left_bumper = extindere glisiera
            ///left_trigger= coboara si prinde
            ///left_trigger+left_bumper = ia element si executa secventa pana la transfer

            ///Sistem = Transfer
            ///Automat, no buttons needed

            ///Sistem = Outake Cos
            ///Right_bumper = se ridica si gheara se "infinge" in basket
            ///Right_bumper + right_trigger = da drop la element si se trage and ready pt intake


            ///Sistem = Intake pt Human Player
            ///left_bumper = se intinde bratul sa ia
            ///left_trigger = agata specimen si se ridica + deschide(poate face asta epileptic)
            ///left_trigger+left_bumper = ia element
            ///left bumper = drop (Outake la Human Player)

            ///Sistem = Intake de la Human Player ( de pe perete)
            ///right bumper - positie luat de pe perete ->cand e hold deschis, cand release->inchis
            ///right_trigger - luat de pe perete
            ///right_bumper - brat in pozitie de agatat

            ///Sistem = Agatat specimen
            ///left_bumper = agata (=se ridica glisiera brusc, da drumul la specim, se retrage in pozitie de init)

            if(GameMode==true && gamepad1.left_bumper && isHorizontalIn==true && isIntakeReady==true){
                HorizontalTarget= outHorizontal ;
                isHorizontalIn=false;
                isIntakeReady=false;
                to_Transfer=false;
                To_Intake_1();
            }
            else if(GameMode==true && gamepad1.left_trigger>0.3 && isHorizontalIn==false){
                To_Intake_2();
                isIntakeLowered=true;
            }
            else if(GameMode==true && isHorizontalIn==false) {
                To_Intake_1();
                isIntakeLowered=false;
            }
            else if(GameMode==true && gamepad1.right_bumper && isIntakeLowered==true && StartIntake==false){
                To_Intake_3();
                StartIntake=true;
                Intake_timer=new ElapsedTime();
            }
            else if(GameMode==true && isIntakeLowered==true && StartIntake==true){
                if(Intake_timer.time()>1000 && Intake_timer.time()<2000){
                    To_Intake_4();
                }
                else if(Intake_timer.time()>2000){
                    HorizontalTarget=inHorizontal;
                    isHorizontalIn=true;
                    isIntakeLowered=false;
                    StartIntake=false;
                    to_Transfer=true;
                }
            }
            else if(GameMode==true && isHorizontalIn==true && to_Transfer==true && Start_Transfer==false){
                Transfer_timer=new ElapsedTime();
                Start_Transfer=true;
            }
            else if(GameMode==true && to_Transfer==true && Start_Transfer==true){
                if(Transfer_timer.time()>1000 && Transfer_timer.time()<2000){
                    To_Transfer_1(); ///Ambele inchise
                }
                else if(Transfer_timer.time()>2000 && Transfer_timer.time()<3000){
                    To_Transfer_2(); /// 1 Inchs, 1 Deschs
                }
                else if(Transfer_timer.time()>3000 && Transfer_timer.time()<4000){
                    HorizontalTarget=HorizontalRetras;
                }
                else if(Transfer_timer.time()>4000 && Transfer_timer.time()<5000){
                    Outake_Out();
                    is_Outake=true;
                }
                else if(Transfer_timer.time()>5000 &&  Transfer_timer.time()<6000){
                    HorizontalTarget=inHorizontal;
                    to_Init();
                    to_Transfer=false;
                    Start_Transfer=false;
                    isOutakeReady=false;
                }
            }
            else  if(GameMode==true && isOutakeReady==true && VerticalSliderIn==true && gamepad1.right_bumper && Start_Basket==false) {
                Start_Basket=true;
                Basket_timer.reset();
            }
            else if(GameMode==true && isOutakeReady==true && VerticalSliderIn==true && Start_Basket==true) {
                if(Basket_timer.time()>500 && Basket_timer.time()<2000) {
                    VerticalTarget=BasketPositon;
                }
                else if(Basket_timer.time()>2000){
                    To_BasketDrop();
                    VerticalSliderIn=false;
                    Start_Basket=false;
                    isOutakeReady=false;
                    isBasketReady=true;
                }
            }
            else if(GameMode==true && VerticalSliderIn==false && isBasketReady==true && gamepad1.right_bumper && gamepad1.right_trigger>0.3) {
                Basket_drop_timer.reset();
                if(Basket_drop_timer.time()>500 && Basket_drop_timer.time()<1000){
                    to_Basket_Release(); ///drop
                }
                else if(Basket_drop_timer.time()>1000 && Basket_drop_timer.time()<1500){
                    to_Inside(); ///servo inside
                }
                else if(Basket_drop_timer.time()>1500 && Basket_drop_timer.time()<2000){
                    VerticalTarget=inVerticalPosition;
                    VerticalSliderIn=true;
                    to_Init();
                    isIntakeReady=true;
                    isBasketReady=false;
                }
            }
            ///De aici incepe human player drop
            if(GameMode==false && gamepad1.left_bumper && isHorizontalIn==true && isIntakeReady==true){
                HorizontalTarget= outHorizontal ;
                isHorizontalIn=false;
                isIntakeReady=false;
                To_Intake_1();
            }
            else if(GameMode==false && gamepad1.left_trigger>0.3 && isHorizontalIn==false){
                To_Intake_2();
                isIntakeLowered=true;
            }
            else if(GameMode==false && isHorizontalIn==false) {
                To_Intake_1();
                isIntakeLowered=false;
            }
            else if(GameMode==false && gamepad1.left_bumper && isIntakeLowered==true && StartIntake==false){
                To_Intake_3();
                StartIntake=true;
                Intake_timer=new ElapsedTime();
            }
            else if(GameMode==false && isIntakeLowered==true && StartIntake==true){
                if(Intake_timer.time()>1000 && Intake_timer.time()<2000){
                    To_Intake_Human_Player();
                }
                else if(Intake_timer.time()>2000){
                    HorizontalTarget=inHorizontal;
                    isHorizontalIn=true;
                    isIntakeLowered=false;
                    StartIntake=false;
                    to_Drop=true;
                }
            }
            else if(GameMode==false && to_Drop==true && drop_to_intake==false && gamepad1.left_bumper && isHorizontalIn==true){
                to_Drop_Deschis();
                drop_to_intake=true;
            }
            else if(GameMode==false && to_Drop==true && drop_to_intake==true && isHorizontalIn==true){
                Human_timer.reset();
                if(Human_timer.time()>500){
                    to_Init();
                    drop_to_intake=false;
                    to_Drop=false;
                    isIntakeReady=true;
                }
            }
            ///Intake de la human
            else if(GameMode==false && isIntakeReady==true && gamepad1.right_bumper){
                start_specimen=true;
                isIntakeReady=false;
                to_Intake_specimen_inchis();
            }
            else if(GameMode==false && start_specimen==true && gamepad1.right_bumper){
                to_Intake_specimen_deschis();
            }
            else if(GameMode==false && start_specimen==true && gamepad1.right_trigger>0.3){
                to_luat_de_pe_perete();
                start_specimen=false;
                to_bara_ready=true;
            }
            else if(GameMode==false && start_specimen==true){
                to_Intake_specimen_inchis();
            }
            else if(GameMode==false && to_bara_ready==true && gamepad1.right_bumper){
                Human_timer.reset();
                if(Human_timer.time()>500){
                    to_Bara();
                    to_bara_ready=false;
                    isIntakeReady=true;
                    isAgatatReady=true;
                }
            }
            ///Agatat specimen
            else if(GameMode==false && isAgatatReady==true && gamepad1.left_bumper){
                isAgatatReady=false;
                AgatatStart=true;
            }
            else if(GameMode==false && AgatatStart==true){
                Specimen_Timer.reset();
                if(Specimen_Timer.time()>500 && Specimen_Timer.time()<1500){
                    VerticalTarget=VerticalSpecimenTarget;
                    VerticalSliderIn=false;
                }
                else if(Specimen_Timer.time()>1500 && Specimen_Timer.time()<2000){
                    to_Bara_Release();
                }
                else if(Specimen_Timer.time()>2000 && Specimen_Timer.time()<2500){
                    VerticalTarget=inVerticalPosition;
                    VerticalSliderIn=true;
                    isIntakeReady=true;
                    AgatatStart=false;
                    to_Init();
                }
            }


            // Change Game mode (Basket sau Bara) // cos = true ; bara = false;
            if (gamepad1.dpad_left) {
                GameMode = true;
            }

            if (gamepad1.dpad_right) {
                GameMode = false;
            }


            // all telemetry
            telemetry.addData("mod", GameMode);
            telemetry.addData("horizontalValue", HSld_value);
            telemetry.addData("Horizontal Destination", HSld_dest_value);
            telemetry.addData("verticalRightValue", VSR_value);
            telemetry.addData("verticalLeftValue", VSL_value);
            telemetry.addData("verticalLeftValue", OutClaw.getPosition());
            telemetry.addData("multi", multiplayer);
            telemetry.update();
        }
    }
    private void to_Init(){
        ///Init - state initial
    }
    private void To_Intake_1() {
        /// Aici glisiera s-a exstins si gheara e indreptata spre sol (gheara deschisa)
    }
    private void To_Intake_2() {
        /// Gheara e lowered (gheara deschisa)
    }
    private void to_Basket_Release(){
        ///drop la element
    }
    private void to_Inside(){
        ///Servo de susa inside
    }
    private void To_Intake_3() {
        /// gheara de intake e lowered si se inchide
    }
    private void To_Intake_4() {
        /// gheara se intoarce/ridica cu elementul(gheara inchisa)
        ///P.S. urmeaza etapa de transfer
    }
    private void to_Bara_Release(){
        ///cand agatam un specimen si servoul ii da drumul.
    }
    private void To_Intake_Human_Player() {
        /// Gheruta sta in pozitia spre exterior doar sa deschida sa dea drop.
    }
    private void to_Intake_specimen_deschis(){
        ///Bratul se pozitioneaza sa ia de pe perete.
        /// !!!! GHEARA E DESCHISA
    }
    private void to_Intake_specimen_inchis(){
        ///Bratul se pozitioneaza sa ia de pe perete.
        /// !!!! GHEARA E INCHISA
    }
    private void to_Drop_Deschis() {
        /// Gheruta se deschide si doar da drop la human player
    }
    private void to_luat_de_pe_perete() {
        /// Gheara ia specimenul si se retrage un pic de la perete
    }
    private void to_Bara() {
        /// bratul se intoarce 180 grade or whatever si ajunge in pozitia de agata(mai trebuie doar sa se ridice)
    }
    private void To_Transfer_1() {
        ///servourile sunt in pozitie de preluat elementul
        /// AMBELE inchise
    }
    private void To_Transfer_2() {
        ///servourile sunt in pozitie de preluat elementul
        /// Gheara outake INCHISA, gheara Intake DESCHISA
    }
    private void Outake_Out() {
        ///da drop la element in basket (gheara deschisa)
    }
    private void To_BasketDrop() {
        /// Gheara in pozitie pentru a da drop(NU e deschisa)
    }
    private void horizontalSliders(){
        HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        HSldMotor.setTargetPosition(HorizontalTarget);
        if(HSldMotor.getCurrentPosition()+50 > HorizontalTarget || HSldMotor.getCurrentPosition()-50< HorizontalTarget){
            HSldMotor.setPower(0.3);
        }else{
            HSldMotor.setPower(1);
        }
    }
    private void verticalSliders(){
        VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        VSLMotor.setTargetPosition(VerticalTarget);
        VSRMotor.setTargetPosition(VerticalTarget);
        if(VSLMotor.getCurrentPosition()+50 > VerticalTarget || VSLMotor.getCurrentPosition()-50< VerticalTarget){
            VSLMotor.setPower(0.3);
        }else{
            VSLMotor.setPower(1);
        }
        if(VSRMotor.getCurrentPosition()+50 > VerticalTarget || VSRMotor.getCurrentPosition()-50< VerticalTarget){
            VSRMotor.setPower(0.3);
        }else{
            VSRMotor.setPower(1);
        }
    }

}