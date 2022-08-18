package sample;

import Game.*;
import Teams.AttackerTeam;
import Teams.DefenderTeam;
import Teams.StaticTeam;
import Teams.Team;
import Units.Unit;
import Units.UnitDestroyObserver;
import Units.UnitType;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* Notes:
1. Line 68: m what's stands for?
2.

 */

public class Controller implements Initializable {
    /**************** 5:10 AM *************************/

    DorD_Game.Pauser pauser = new DorD_Game.Pauser();
    Schedule scheduleExecutor=new Schedule(1000);



    /************** 12/12   NEW ALI   ***********************/

    int numStatic = 2;

    @FXML
    Team teamAttack = new AttackerTeam(1), teamDefend = new DefenderTeam(1), teamStatic = new StaticTeam(1);
    @FXML
    UnitDestroyObserver observer = DorD_Game.getInstance(teamAttack, teamDefend, teamStatic,pauser);

    /*****************************************************************/


    @FXML
    int totalCoins = 3000, tankCount = 0, soldierCount = 0, sniperCount = 0,
            grizzlycount = 0, navycount = 0, miragecount = 0, destroyercount = 0,
            prismcount = 0, pillboxcount = 0, prismtowercount = 0, planeCount = 0,
            grandcamnnoncount = 0, patriotcount = 0, planeCost = 100, grizzlycost =50,
            grandcamnnoncost = 200, patriotcost = 175, prismcost = 60, pillboxcost = 150,
            prismtowercost = 150, navycost = 10, miragecost = 70, destroyercost = 50,
            tankCost = 50, infantryCost =3, sniperCost = 5, countUniteAttack = 0;

    @FXML
    static int s = 0, h = 0, m = 1000, teamType = 0;

    @FXML
    static int pa = 1, pd = 1; // pa/pd: Counters for display playersID for each team

    @FXML
    static String name;

    @FXML
    DorD_Game game = DorD_Game.getInstance(teamAttack, teamDefend, teamStatic,pauser);


    @FXML
    static int numDefend = 1, numAttack = 1;

    @FXML
    TextField attackerTeamName, defenderTeamName, xBase, yBase, xtext, ytext;

    @FXML
    static String nameAttackerTeam, nameDefenderTeam;


    /*********** Defender Units Images ***********/
    @FXML
    Circle MainBase, GrandCannon, Pillbox, PrismTower, PatriotMissileSystem;

    // Shared Attacker/Defender
    @FXML
    Circle MirageTank, TankDestroyer, TeslaTank, PrismTank,
            GrizzlyTank, BlackEagle, Infantry, Sniper, NavySEAL;


    @FXML
    Label coins, warning, numTanks, numSoldiers, nam, playerID, numSniper, numPlane,
            numDestroyer, numgrizzly, numNavy, numMirage, numPrism,
            numPillbox, numPrismTower, numGrandCamnnon, numPatriot, teamName;//nam: namePlayerLabel

    @FXML
    ComboBox<Integer> defenderComboBox, attackerComboBox;

    @FXML
    public void handleButton1(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("SecondScene.fxml"));
            Stage secondaryStage = Main.mainWindow;
            secondaryStage.setTitle("Destroy or Defend");
            secondaryStage.setScene(new Scene(secondView, 790, 450));
            secondaryStage.setResizable(false);
            secondaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void AttackTeamType(ActionEvent event) {
        teamType = 1;
    }

    @FXML
    void DefendTeamType(ActionEvent event) {
        teamType = 2;
    }

    @FXML
    public void handleButton2(ActionEvent event) {

        if (!attackerTeamName.getText().isEmpty() &&
                !defenderTeamName.getText().isEmpty() &&
                !defenderComboBox.getSelectionModel().isEmpty() &&
                !attackerComboBox.getSelectionModel().isEmpty()) {

            s = 4;
            h = 1;

            numAttack = attackerComboBox.getValue();
            numDefend = defenderComboBox.getValue();


            //teamAttack.setNumPlayers(numAttack);

            //teamDefend.setNumPlayers(numDefend);


            nameAttackerTeam = attackerTeamName.getText();
            nameDefenderTeam = defenderTeamName.getText();

            warning.setText("");

            dynamicOrStaticScene(event);

        } else {
            warning.setText("you should fill these data first..!!");
        }

    }

    @FXML
    void dynamicOrStaticScene(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("ThirdScene.fxml"));
            Stage secondaryStage = Main.mainWindow;
            secondaryStage.setTitle("Destroy or Defend");
            secondaryStage.setScene(new Scene(secondView, 600, 400));
            secondaryStage.setResizable(false);
            secondaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void DisplayAttackUnits1(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("AttackerUnitsNext.fxml"));
            Stage staticBuyingStage = Main.mainWindow;
            staticBuyingStage.setTitle("Destroy or Defend");
            staticBuyingStage.setScene(new Scene(secondView, 660, 450));
            staticBuyingStage.setResizable(false);
            staticBuyingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void DisplayAttackUnits(ActionEvent event) {
        try {

            Parent secondView = FXMLLoader.load(getClass().getResource("AttackerUnitsOK.fxml"));
            Stage staticBuyingStage = Main.mainWindow;
            staticBuyingStage.setTitle("Destroy or Defend");
            staticBuyingStage.setScene(new Scene(secondView, 660, 450));
            staticBuyingStage.setResizable(false);
            staticBuyingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void DisplayDefenderUnitsNext(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("DefenderUnitsNext.fxml"));
            Stage staticBuyingStage = Main.mainWindow;
            staticBuyingStage.setTitle("Destroy or Defend");
            staticBuyingStage.setScene(new Scene(secondView, 660, 450));
            staticBuyingStage.setResizable(false);
            staticBuyingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /************* new ali fin  ************************/
    @FXML
    public void DisplayDefenderUnitsOK(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("DefenderUnitsOK.fxml"));
            Stage staticBuyingStage = Main.mainWindow;
            staticBuyingStage.setTitle("Destroy or Defend");
            staticBuyingStage.setScene(new Scene(secondView, 660, 450));
            staticBuyingStage.setResizable(false);
            staticBuyingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            s = 55;
        }
    }

    /************ new 12 /12   ALI  SH   **********************************/

    @FXML
    public void handleButtonStatic(ActionEvent event) {

        if (s == 4) {
            try {
                s = 0;
                Parent secondView = FXMLLoader.load(getClass().getResource("BaseCoordinates.fxml"));
                Stage staticBuyingStage = Main.mainWindow;
                staticBuyingStage.setTitle("Destroy or Defend");
                staticBuyingStage.setScene(new Scene(secondView, 660, 450));
                staticBuyingStage.setResizable(false);
                staticBuyingStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (s == 2) {
            h = 11;

            toChooseUnitesAttackStatic(event);
        } else {
            h = 2;
            dynamicOrStaticScene(event);
            s = 2;


        }

        /*
        if (teamType == 1) {
            DisplayAttackUnits(event);
        } else {
            DisplayDefenderUnits(event);
        }*/

    }

    class SplashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            play();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        DisplayGrid();


                    }
                });

            } catch (InterruptedException e) {
                System.out.println("Loading Grid Exception! (2)");
            }
        }
    }
    /************* new 12/12     OMG *******************************/

    @FXML
    boolean buy(String typUnit, int x, int y, int z) {
        int i = 0;


        if (h == 10) {
            i = pd - 1;

            System.out.println("Team Defender# :");
            if (DorD_Game.getInstance(game.getTeamAttack(), game.getTeamDefend(), game.getTeamStatic(),pauser)
                    .BuyUnit(game.getTeamDefend(), game.getTeamDefend().getPlayers()[0], x, y, typUnit, observer)) {
                return true;

            } else {
                return false;
            }


        } else if (h == 11) {
            i = pa - 1;
            System.out.println("Team Attacker# :");
            if (DorD_Game.getInstance(game.getTeamAttack(), game.getTeamDefend(), game.getTeamStatic(),pauser)
                    .BuyUnit(game.getTeamAttack(), game.getTeamAttack().getPlayers()[0], x, y, typUnit, observer))
                return true;

            return false;


        }

        return false;

        /** team Static!!    **** player *****/


    }


    @FXML
    public void toChooseBase(ActionEvent event) {

        if (!xBase.getText().equals("") && !yBase.getText().equals("")){


            int xB = Integer.parseInt(xBase.getText());
            int yB = Integer.parseInt(yBase.getText());

            if(!game.setMainBase(xB, yB, observer))
            {
                 warning.setText("The place is not valid  ");
                xBase.setText(" ");
                yBase.setText(" ");

            }
            else {
            h = 10;
            toChooseUnitesStatic(event);}


        }
        else {warning.setText("Enter the coordinates");}


    }

    @FXML
    public void toChooseUnitesStatic(ActionEvent event) {

        if (pd == numDefend) {
            s = 3;
            DisplayDefenderUnitsOK(event);

        } else {
            s = 3;
            DisplayDefenderUnitsNext(event);
            pd = pd + 1;
        }

    }


    @FXML
    public void toChooseUnitesAttackStatic(ActionEvent event) {
        if (pa == numAttack) {
            s = 2;
            h = 11;
            DisplayAttackUnits(event);
            s = 0;
        } else {
            // countUniteAttack=destroyercount+miragecount+sniperCount+soldierCount+tankCount+grizzlycount+navycount+prismcount;

            s = 2;
            h = 11;
            DisplayAttackUnits1(event);
            pa = pa + 1;
        }
    }


    @FXML
    public void fourthScene(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("Loading.fxml"));
            Stage secondaryStage = Main.mainWindow;
            secondaryStage.setTitle("Destroy or Defend");

            secondaryStage.setScene(new Scene(secondView, 600, 450));
            secondaryStage.show();

            new SplashScreen().start();

        } catch (IOException e) {
            e.printStackTrace();
        }


        /****************12/12   ALI*****************************/
    }

    @FXML
    public void starGame(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("starGame.fxml"));
            Stage secondaryStage = Main.mainWindow;
            secondaryStage.setTitle("Destroy or Defend");

            secondaryStage.setScene(new Scene(secondView, 600, 450));
            secondaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /********************** 12/12 ALI *************************/
    @FXML
    public void handleButtonDynamic(ActionEvent event) {
        s = 0;

        try {

            if (h == 1) {
                game.setMainBase(1000, 1000, observer);
                chooseDynamicDefend(event);
                h = 2;
                handleButtonStatic(event);
                //  s=2;

            } else if (h == 2) {
                chooseDynamicAttack(event);
                starGame(event);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // fourthScene(event);

        //Backtrack bt = new Backtrack();
        //bt.fun(0,totalCoins);
    }

    @FXML
    public void handleButtonDynamicFile(ActionEvent event) {
        // call fun
    }

    @FXML
    public void displayDefendMainBase() throws FileNotFoundException {
        MainBase.setStroke(Color.SEAGREEN);
        Image image1 = new Image(new FileInputStream("@..\\..\\Images\\militaryBase.png"));
        MainBase.setFill(new ImagePattern(image1));
        MainBase.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    }

    @FXML
    public void DisplayUnits() throws FileNotFoundException {

        if (s == 3) {
            //******* Grand Cannon Image *******//
            GrandCannon.setStroke(Color.SEAGREEN);
            Image image1 = new Image(new FileInputStream("@..\\..\\Images\\RA2_Grand_Cannon.png"));
            GrandCannon.setFill(new ImagePattern(image1));
            GrandCannon.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

            //******* Pillbox Image *******//
            Pillbox.setStroke(Color.SEAGREEN);
            Image image2 = new Image(new FileInputStream("@..\\..\\Images\\Pillbox_icon.png"));
            Pillbox.setFill(new ImagePattern(image2));
            Pillbox.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

            //******* PrismTower Image *******//
            PrismTower.setStroke(Color.SEAGREEN);
            Image image3 = new Image(new FileInputStream("@..\\..\\Images\\PrismTower.png"));
            PrismTower.setFill(new ImagePattern(image3));
            PrismTower.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

            //******* Patriot Missile System Image *******//
            PatriotMissileSystem.setStroke(Color.SEAGREEN);
            Image image4 = new Image(new FileInputStream("@..\\..\\Images\\EMP_Patriot.jpg"));
            PatriotMissileSystem.setFill(new ImagePattern(image4));
            PatriotMissileSystem.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        }

        //******* Tank Image *******//
        //MirageTank
        MirageTank.setStroke(Color.SEAGREEN);
        Image image1 = new Image(new FileInputStream("@..\\..\\Images\\mirageTank.jpg"));
        MirageTank.setFill(new ImagePattern(image1));
        MirageTank.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        // TankDestroyer
        TankDestroyer.setStroke(Color.SEAGREEN);
        Image image2 = new Image(new FileInputStream("@..\\..\\Images\\TankDestroyer.png"));
        TankDestroyer.setFill(new ImagePattern(image2));
        TankDestroyer.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        // TeslaTank
        TeslaTank.setStroke(Color.SEAGREEN);
        Image image3 = new Image(new FileInputStream("@..\\..\\Images\\TeslaTank.jpg"));
        TeslaTank.setFill(new ImagePattern(image3));
        TeslaTank.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        // PrismTank
        PrismTank.setStroke(Color.SEAGREEN);
        Image image4 = new Image(new FileInputStream("@..\\..\\Images\\PrismTank.png"));
        PrismTank.setFill(new ImagePattern(image4));
        PrismTank.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));


        // GrizzlyTank
        GrizzlyTank.setStroke(Color.SEAGREEN);
        Image image5 = new Image(new FileInputStream("@..\\..\\Images\\GrizzlyTank.jpg"));
        GrizzlyTank.setFill(new ImagePattern(image5));
        GrizzlyTank.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));


        // BlackEagle
        if (s == 2) {
            BlackEagle.setStroke(Color.SEAGREEN);
            Image image6 = new Image(new FileInputStream("@..\\..\\Images\\BlackEagle.png"));
            BlackEagle.setFill(new ImagePattern(image6));
            BlackEagle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        }

        //******* Soldier Image *******//
        // Infantry
        Infantry.setStroke(Color.SEAGREEN);
        Image image7 = new Image(new FileInputStream("@..\\..\\Images\\soldier6.png"));
        Infantry.setFill(new ImagePattern(image7));
        Infantry.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        // Sniper
        Sniper.setStroke(Color.SEAGREEN);
        Image image8 = new Image(new FileInputStream("@..\\..\\Images\\sniper.png"));
        Sniper.setFill(new ImagePattern(image8));
        Sniper.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));


        // NavySEAL
        NavySEAL.setStroke(Color.SEAGREEN);
        Image image9 = new Image(new FileInputStream("@..\\..\\Images\\NavySEAL.jpg"));
        NavySEAL.setFill(new ImagePattern(image9));
        NavySEAL.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if (s == 3) { // Defender
                DisplayUnits();
                coins.setText(totalCoins + "  $");
                nam.setText(nameDefenderTeam);
                playerID.setText(pd + "");
                //  playerID.setText(String.valueOf(getPlayerID(0, pd++)));
            }
            if (s == 2) { // Attacker
                DisplayUnits();
                coins.setText(totalCoins + "  $");
                nam.setText(nameAttackerTeam);

                playerID.setText(pa + "");

                //  s++;
            }
            if (s == 4) {
                teamName.setText("Defender  Unite");
//                if(h==2)
//                    teamName.setText("Attack  Unite");

            }
        } catch (Exception e) {
            System.out.println("Initialize Exception thrown..!");
        }
    }

    @FXML
    public void teslaTankChooseEvent(ActionEvent e) {


        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {
            if (totalCoins >= tankCost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());


                if(!buy("TeslaTank", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {
                numTanks.setText(++tankCount + "");
                totalCoins = totalCoins - tankCost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}

            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");
    }

    @FXML
    public void secondView3(ActionEvent event) {

        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("ThirdScene.fxml"));
            Stage secondaryStage = Main.mainWindow;
            secondaryStage.setTitle("Destroy or Defend");
            secondaryStage.setScene(new Scene(secondView, 600, 400));
            secondaryStage.setResizable(false);
            secondaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void mirageChooseEvent(ActionEvent e) {

        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {

            if (totalCoins >= miragecost) {
                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());


                if(!buy("MirageTank", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {


                numMirage.setText(++miragecount + "");
                totalCoins = totalCoins - miragecost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}
            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");


    }

    @FXML
    public void infantryChooseEvent(ActionEvent e) {

        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {
            if (totalCoins >= infantryCost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());


                if(!buy("Infantry", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {

                numSoldiers.setText(++soldierCount + "");
                totalCoins = totalCoins - infantryCost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}


            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");

    }

    @FXML
    public void navyChooseEvent(ActionEvent e) {

        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {
            if (totalCoins >= navycost) {
            int xtank = Integer.parseInt(xtext.getText());
            int ytank = Integer.parseInt(ytext.getText());
            if(!buy("NavySEAL", xtank, ytank, 0))
            {
                xtext.setText("");
                ytext.setText("");

                warning.setText(" The place is not valid  ");

            }
            else {
                numNavy.setText(++navycount + "");
                totalCoins = totalCoins - navycost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}

            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");


    }

    @FXML
    public void DestroyerChooseEvent(ActionEvent e) {

        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {
            if (totalCoins >= destroyercost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("TankDestroyer", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {



                numDestroyer.setText(++destroyercount + "");
                totalCoins = totalCoins - destroyercost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}
            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");

    }

    @FXML
    public void prismChooseEvent(ActionEvent e) {

        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {

            if (totalCoins >= prismcost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("PrismTank", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {


                numPrism.setText(++prismcount + "");
                totalCoins = totalCoins - prismcost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}
            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");

    }

    @FXML
    public void sniperChooseEvent(ActionEvent e) {
        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {

            if (totalCoins >= sniperCost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("Sniper", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {

                    numSniper.setText(++sniperCount + "");
                    totalCoins = totalCoins - sniperCost;
                    coins.setText(totalCoins + "  $");
                    warning.setText("");
                    xtext.setText("");
                    ytext.setText("");}

            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");


    }

    @FXML
    public void pillboxChooseEvent(ActionEvent e) {
        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {


            if (totalCoins >= pillboxcost) {
                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("Pillbox", xtank, ytank, 0))

                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {

                numPillbox.setText(++pillboxcount + "");
                totalCoins = totalCoins - pillboxcost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}


            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");


    }

    @FXML
    public void prismtowerChooseEvent(ActionEvent e) {
        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {

            if (totalCoins >= prismtowercost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("PrismTower", xtank, ytank, 0))

                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {



                numPrismTower.setText(++prismtowercount + "");
                totalCoins = totalCoins - prismtowercost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}


            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");

    }

    @FXML
    public void patriotChooseEvent(ActionEvent e) {
        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {

            if (totalCoins >= patriotcost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
              if(!buy("PatriotMissileSystem", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {


                numPatriot.setText(++patriotcount + "");
                totalCoins = totalCoins - patriotcost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}


            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");


    }

    @FXML
    public void grandcamnnonChooseEvent(ActionEvent e) {

        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {

            if (totalCoins >= grandcamnnoncost) {


                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("GrandCannon", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {



                numGrandCamnnon.setText(++grandcamnnoncount + "");
                totalCoins = totalCoins - grandcamnnoncost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}
            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");
    }

    @FXML
    public void grizzlyChooseEvent(ActionEvent e) {
        if (!xtext.getText().equals("") && !ytext.getText().equals("")) {


            if (totalCoins >= grizzlycost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("GrizzlyTank", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {


                numgrizzly.setText(++grizzlycount + "");
                totalCoins = totalCoins - grizzlycost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}

            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");

    }

    @FXML
    public void planeChooseEvent(ActionEvent e) {

        if (!xtext.getText().equals(" ") && !ytext.getText().equals(" ")) {

            if (totalCoins >= planeCost) {

                int xtank = Integer.parseInt(xtext.getText());
                int ytank = Integer.parseInt(ytext.getText());
                if(!buy("BlackEagle", xtank, ytank, 0))
                {
                    xtext.setText("");
                    ytext.setText("");

                    warning.setText(" The place is not valid  ");

                }
                else {

                numPlane.setText(++planeCount + "");
                totalCoins = totalCoins - planeCost;
                coins.setText(totalCoins + "  $");
                warning.setText("");
                xtext.setText("");
                ytext.setText("");}

            } else {
                warning.setText("There is not enough money");
            }
        } else warning.setText("Enter the coordinates");


    }

    @FXML
    public void reset(ActionEvent event) {
        handleButtonStatic(event);
    }

    @FXML
    public void back(ActionEvent event) {
        s = 0;
        secondView3(event);

    }

    @FXML
    public void start(ActionEvent event) {

            if (tankCount != 0) {

                // buynumUnit(tankCount, unitType.Tank);
            }
            if (soldierCount != 0) {
                // call buynumUnit(soldierCount, unitType.Soldier)
            }

            starGame(event);

        } 


    /********************** new 12/12 *************************/

    @FXML
    public void chooseDynamicDefend(ActionEvent event) throws FileNotFoundException {
        System.out.println("\nDefend Team");
            game.FileInput(observer, game.getTeamDefend(), 0);


    }

    @FXML
    public void chooseDynamicAttack(ActionEvent event) throws FileNotFoundException {


        System.out.println("\nAttack Team");
        game.FileInput(observer, game.getTeamAttack(), 0);


    }


    /************************** Sedra Controller **************************/
//    BlockingQueue<Runnable> list = new LinkedBlockingQueue<>();
//    PausableThreadPoolExecutor executor = new PausableThreadPoolExecutor(100, 100, 10, TimeUnit.SECONDS, list);



//    /********************* new 12/12*********************/
//    @FXML
//    void play() throws FileNotFoundException {
//
//
//        //game.setMainBase(1000, 1000, observer);
//
//
//        game.FileInput(observer, game.getTeamStatic(), 0);
//
//        MainThread play = new MainThread(game, pauser, executor);
//
//        play.start();
//
//    }
    @FXML
    void play() throws FileNotFoundException {

        game.FileInput(observer, game.getTeamStatic(), 0);

        game.setMainBase(1000, 1000, observer);

        //game.FileInput( observer,teamStatic,0);




        MainThread play = new MainThread(game,scheduleExecutor);
        play.start();


    }


    public void pause() {
        Unit.myState = false;
       scheduleExecutor.pause();
        pauser.pause();
        Unit.gameInPauseCheckedOnce = false;
        System.out.println("pause");
        Scanner scanner=new Scanner(System.in);
        int x=1000,y=1000;
        System.out.println("Enter x && y:");
        x=scanner.nextInt();
        y=scanner.nextInt();
        ArrayList<Unit> units = new ArrayList<>(game.getGrid().getGrid());
        System.out.println("Showing All Units in this Range:");
        boolean b = false;
        for (int i = 0; i < units.size(); i++) {
            Unit TempUnit = units.get(i);
            int[] a = {TempUnit.getPosition().getCenterX(), TempUnit.getPosition().getCenterY()};
            int r1 = TempUnit.getPosition().getRadius();
            int distance = (int) Math.sqrt(Math.pow(x - a[0], 2) + Math.pow(y - a[1], 2));
            if (distance < (100 + r1)) {
                b = true;
                if (TempUnit.getUnitProperties()[4].getPropertyValue() == 0)
                    System.out.println("Unit " + TempUnit.getUnitName() + "#" + TempUnit.getID() + " Team(Attacker) is in: (" + TempUnit.getPosition().getCenterX() + "," + TempUnit.getPosition().getCenterY() + ").");
                else
                    System.out.println("Unit " + TempUnit.getUnitName() + "#" + TempUnit.getID() + " Team(Defender) is in: (" + TempUnit.getPosition().getCenterX() + "," + TempUnit.getPosition().getCenterY() + ").");
            }
        }
        if (!b)
            System.out.println("Empty");

        /*show.setOnAction(e -> {
            String xx = xField.getText();
            x = Integer.parseInt(xx);
            String yy = yField.getText();
            y = Integer.parseInt(yy);
            ArrayList<Unit> units = new ArrayList<>(game.getGrid().getGrid());
            System.out.println("Showing All Units in this Range:");
            boolean b = false;
            for (int i = 0; i < units.size(); i++) {
                Unit TempUnit = units.get(i);
                int[] a = {TempUnit.getPosition().getCenterX(), TempUnit.getPosition().getCenterY()};
                int r1 = TempUnit.getPosition().getRadius();
                int distance = (int) Math.sqrt(Math.pow(x - a[0], 2) + Math.pow(y - a[1], 2));
                if (distance < (100 + r1)) {
                    b = true;
                    if (TempUnit.getUnitProperties()[4].getPropertyValue() == 0)
                        System.out.println("Unit " + TempUnit.getUnitName() + "#" + TempUnit.getID() + " Team(Attacker) is in: (" + TempUnit.getPosition().getCenterX() + "," + TempUnit.getPosition().getCenterY() + ").");
                    else
                        System.out.println("Unit " + TempUnit.getUnitName() + "#" + TempUnit.getID() + " Team(Defender) is in: (" + TempUnit.getPosition().getCenterX() + "," + TempUnit.getPosition().getCenterY() + ").");
                }
            }
            if (!b)
                System.out.println("Empty");
        });*/

    }

    void resume() {
        Unit.myState = true;
        pauser.resume();
        scheduleExecutor.resume();
        Unit.gameInPauseCheckedOnce = true;
        System.out.println("resume");
    }

    /************ new Func ***********/
    private void restart() {
        scheduleExecutor.shutdownNow();
        game.getGrid().grid.clear();
        Main.mainWindow.close();
        Main m = new Main();
        try {
            m.start(new Stage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /******************* Get Players IDs *******************/
    public int getPlayerID(int teamType, int playerNum) {
        if (teamType == 0)
            return game.getTeamDefend().getPlayers()[playerNum].getPlayerID();
        else if (teamType == 1)
            return game.getTeamAttack().getPlayers()[playerNum].getPlayerID();
        else return -1; // wrong type
    }

    /******************* Display Grid *******************/
    private GridPane gpane;
    private BorderPane borderPane;
    private Scene scene;
    private Affine affine = new Affine();
    private ScrollPane scrollPane;
    private Label timer;

    static int t = 0;
    int x = 0, y = 0;

    @FXML
    public void DisplayGrid() {

        Stage stage = Main.mainWindow;

        gpane = new GridPane();

//        gpane.setGridLinesVisible(true);
//        gpane.setHgap(0);
//        gpane.setVgap(0);

        borderPane = new BorderPane();

        // Adding to Top:
        VBox vBoxLayout = new VBox();
        vBoxLayout.setFillWidth(true);
        // Timer:
        timer = new Label("~ Time of Game ~");
        timer.setAlignment(Pos.CENTER);
        timer.setMinWidth(800);
        timer.setMinHeight(15);
        timer.setStyle("-fx-font-weight: bold");

        //borderPane.setTop(timer);

        Menu menu1 = new Menu("Options");
        Menu menu2 = new Menu("Help");

        // Menu 1 Items:
        MenuItem mi1 = new MenuItem("Pause");
        mi1.setOnAction(e -> pause());
        MenuItem mi2 = new MenuItem("Resume");
        mi2.setOnAction(e -> resume());

        // Menu 2 Items:
        MenuItem mi3 = new MenuItem("About");
        mi3.setOnAction(e -> {
            System.out.println(" ----------------------------");
            System.out.println("|3rd Year Project | DorD Game|");
            System.out.println(" ----------------------------");
        });


        menu1.getItems().add(mi1);
        menu1.getItems().add(mi2);

        menu2.getItems().add(mi3);

        MenuBar mb = new MenuBar();

        mb.getMenus().addAll(menu1, menu2);

        vBoxLayout.getChildren().addAll(mb, timer);

        borderPane.setTop(vBoxLayout);

        scrollPane = new ScrollPane(new Group(gpane));
        scrollPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
        scrollPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
        borderPane.setCenter(scrollPane);

        Circle c = null;
        try {
            for (Unit u : game.getGrid().grid) {

                int r = u.getPosition().getRadius();

                if (u.getUnitName().equals("TeslaTank")) {
                    u.getC().setFill(new ImagePattern(
                            new Image(new FileInputStream("@..\\..\\Images\\TeslaTank.jpg"))));
                }

                if (u.getUnitType() == UnitType.River) {
                    u.getC().setFill(Color.BLUE);

                }

                if (u.getUnitType() == UnitType.Airplane) {
                    u.getC().setFill(new ImagePattern(
                            new Image(new FileInputStream("@..\\..\\Images\\war_plane.jpg"))));
                }

                if (u.getUnitName().contentEquals("PatriotMissileSystem")) {
                    u.getC().setFill(new ImagePattern(
                            new Image(new FileInputStream("@..\\..\\Images\\EMP_Patriot.jpg"))));
                }

                if (u.getUnitName().contentEquals("Infantry")) {
                    u.getC().setFill(new ImagePattern(
                            new Image(new FileInputStream("@..\\..\\Images\\soldier6.png"))));
                }

                if (u.getUnitName().contentEquals("Sniper")) {
                    u.getC().setFill(new ImagePattern(
                            new Image(new FileInputStream("@..\\..\\Images\\sniper2.png"))));
                }

                if (u.getUnitName().contentEquals("NavySEAL")) {
                    u.getC().setFill(new ImagePattern(
                            new Image(new FileInputStream("@..\\..\\Images\\NavySEAL.jpg"))));
                }


                if (u.getUnitName().equals("MainBase")) {
                    u.getC().setFill(new ImagePattern(
                            new Image(new FileInputStream("@..\\..\\Images\\militaryBase.png"))));
                }


                gpane.add(u.getC(), u.getPosition().getCenterX(), u.getPosition().getCenterY());
            }
        } catch (Exception e) {
            System.out.println("hsgshsghssghs11");
        } finally {

            for (int i = 0; i < 10; i++) {
                gpane.addColumn(gpane.getColumnCount(), new Text());
                gpane.addRow(gpane.getRowCount(), new Text());
            }
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (game.getState() == GameState.Running) {
                        for (Unit u : game.getGrid().grid) {


                            if (!u.isAlive()) { // Destroyed Units
                                u.getC().setFill(new ImagePattern(
                                        new Image(new FileInputStream("@..\\..\\Images\\ExplosionEffect.png"))));
                            }
                            u.getC().setTranslateX(u.getPosition().getCenterX());
                            u.getC().setTranslateY(u.getPosition().getCenterY());

                        }


                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();


        gpane.getTransforms().add(affine);


//        BackgroundImage background = null;
//        try {
//            background = new BackgroundImage(
//                    new Image(new FileInputStream("@..\\..\\Images\\grass.jpg")),
//                    BackgroundRepeat.REPEAT,
//                    BackgroundRepeat.REPEAT,
//                    BackgroundPosition.DEFAULT,
//                    BackgroundSize.DEFAULT);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        //gpane.setBackground(new Background(background));

        scene = new Scene(borderPane, 800, 600);
        zoom(gpane);
        stage.setScene(scene);
        stage.show();

        // Game Timer:
        new TimerUpdate().start();
    }

    class TimerUpdate extends Thread {
        @Override
        public void run() {
            PauseTransition wait = new PauseTransition(Duration.seconds(1));
            String time = String.valueOf(game.getRemainingTime() / 600);
            wait.setOnFinished((e) -> {
                timer.setText("Time left :    " +
                        String.valueOf(game.getRemainingTime() / 600).charAt(0) + ":" +
                        String.valueOf(game.getRemainingTime() / 600).charAt(2) +
                        String.valueOf(game.getRemainingTime() / 600 - 1).charAt(3));
                wait.playFromStart();
            });
            wait.play();
        }
    }


    /*
    public void move(int teamType) {

        MainThread mt = new MainThread(game, pauser, executor);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 100; j++) {
                    for (int i = 0; i < game.getGrid().grid.size() - 1; i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        gpane.getChildren().get(i).setTranslateX(t);
                        gpane.getChildren().get(i).setTranslateY(t);
                        t = t + 5;

                    }
                }
            }
        };

        Thread th = new Thread(r);
        th.start();

    }*/

    public void zoom(Pane pane) {
        gpane.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        double zoomFactor = 1.05;
                        double deltaY = event.getDeltaY();

                        if (deltaY < 0) {
                            zoomFactor = 0.95;
                        }
                        gpane.setScaleX(gpane.getScaleX() * zoomFactor);
                        gpane.setScaleY(gpane.getScaleY() * zoomFactor);
                        event.consume();
                    }
                });

    }


}


/**
 * Sniper 947 947
 * Sniper 940 940
 * Sniper 920 930
 * Sniper 900 910
 * MirageTank 1150 930
 * MirageTank 700 850
 * Infantry 700 750
 * Infantry 750 800
 * Infantry 800 800
 * Infantry 820 790
 * Infantry 850 720
 * Infantry 830 830
 * NavySEAL 900 920
 * NavySEAL 910 890
 * <p>
 * Sniper 3 3
 * Sniper 9 9
 * Sniper 21 21
 * Sniper 15 15
 * Sniper 30 30
 * MirageTank 60 60
 * Infantry 120 120
 * Infantry 9 3
 * Infantry 15 30
 * Infantry 100 100
 * Infantry 80 80
 * Infantry 90 90
 * NavySEAL 150 150
 * BlackEagle 200 200
 * <p>
 * <p>
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 * <p>
 * <p>
 * Infantry 15 30
 * BlackEagle 500 500
 * GrizzlyTank 750 750
 * GrizzlyTank 100 100
 * GrizzlyTank 900 950
 * NavySEAL 5 20
 * NavySEAL 200 790
 * <p>
 * <p>
 * PatriotMissileSystem  1100 1100
 * MirageTank 60 60
 * Sniper 1055 1055
 **/

/**

 Infantry 15 30
 BlackEagle 500 500
 GrizzlyTank 750 750
 GrizzlyTank 100 100
 GrizzlyTank 900 950
 NavySEAL 5 20
 NavySEAL 200 790


 PatriotMissileSystem  1100 1100
 MirageTank 60 60
 Sniper 1055 1055
 **/