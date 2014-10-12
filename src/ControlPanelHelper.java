import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.security.Key;

public class ControlPanelHelper {
    SimX simX;
    Slider sdrCollision, sdrExplosion, sdrSimXspeed;
    Label lblCollision, lblExplosion, lblpHCounter;
    TextField txfCmd;

    public VBox createControlPanel(final SimX simX) {
        VBox vb = new VBox(10); //spacing between elements
        vb.setAlignment(Pos.CENTER);
        this.simX = simX;
        vb.setMinSize(200f, 650f);
        vb.setPrefSize(200f, 650f);
        vb.setMaxSize(200f, 650f);
        Button btnTemp1 = new Button("FF 1k");
        Button btnTemp2 = new Button("FF 10k");
        Button btnTemp3 = new Button("Add H+");
        Button btnTemp4 = new Button("Add A-");
        Button btnTemp5 = new Button("Add HA");
        Button btnTemp6 = new Button("Add OH-");
        Button btnTemp7 = new Button("Clear");
        sdrCollision = new Slider(0, 1, simX.getCollisionChance());
        sdrCollision.setShowTickMarks(true);
        sdrCollision.setShowTickLabels(true);
        sdrCollision.setMajorTickUnit(0.25f);
        sdrCollision.setMinorTickCount(4);
        sdrCollision.setBlockIncrement(0.05f);
        lblCollision = new Label("Collision probability: " + String.format("%.2f", sdrCollision.getValue()));
        sdrExplosion = new Slider(0, 1, simX.getExplosionChance());
        sdrExplosion.setShowTickMarks(true);
        sdrExplosion.setShowTickLabels(true);
        sdrExplosion.setMajorTickUnit(0.25f);
        sdrExplosion.setMinorTickCount(4);
        sdrExplosion.setBlockIncrement(0.05f);
        lblExplosion = new Label("Explosion probability: " + String.format("%.2f", sdrExplosion.getValue()));
        lblpHCounter = new Label("pH: " + simX.getpH());
        txfCmd = new TextField();
        Button btnExecCmd = new Button("!");
        btnExecCmd.setMinSize(25, 25);
        HBox hbCmd = new HBox(txfCmd, btnExecCmd);
        vb.getChildren().addAll(/*btnTemp1, btnTemp2,*/ btnTemp3, btnTemp4, btnTemp5, btnTemp6, btnTemp7, sdrCollision, lblCollision, sdrExplosion, lblExplosion, lblpHCounter, hbCmd);
        sdrCollision.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                simX.setCollisionChance(newValue.floatValue());
                lblCollision.setText("Collision probability: " + String.format("%.2f", newValue));
            }
        });
        sdrExplosion.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                simX.setExplosionChance(newValue.floatValue());
                lblExplosion.setText("Explosion probability: " + String.format("%.2f", newValue));
            }
        });
        btnTemp1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < 1000; i++) simX.run();
            }
        });
        btnTemp2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < 10000; i++) simX.run();
            }
        });
        btnTemp3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                simX.addHplus();
            }
        });
        btnTemp4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                simX.addAminus();
            }
        });
        btnTemp5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                simX.addHA();
            }
        });
        btnTemp6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                simX.addOHminus();
            }
        });
        btnTemp7.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                simX.clear();
            }
        });
        txfCmd.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER))
                    if (simX.execCmd(txfCmd.getText())) if (!keyEvent.isControlDown()) txfCmd.setText("");
                    else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                        txfCmd.setText("");


            }
        });

        btnExecCmd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (simX.execCmd(txfCmd.getText())) txfCmd.setText("");
            }
        });
        return vb;
    }

    public void updateSliders() { //call this function when collision and explosion probabilities are changed by the program to update the sliders
        sdrCollision.setValue(simX.getCollisionChance());
        lblCollision.setText("Collision probability: " + String.format("%.2f", sdrCollision.getValue()));
        sdrExplosion.setValue(simX.getExplosionChance());
        lblExplosion.setText("Explosion probability: " + String.format("%.2f", sdrExplosion.getValue()));
    }

    public void updatepH() {
        String text = "pH: " + String.format("%.2f", simX.getpH());
        if (simX.getpH() >= 6.99f) {
            text += "\nThis sim cannot calculate\nthe pH of basic solutions.";
            lblpHCounter.setTextFill(Color.RED);
        } else
            lblpHCounter.setTextFill(Color.BLACK);
        lblpHCounter.setText(text);
    }

}
