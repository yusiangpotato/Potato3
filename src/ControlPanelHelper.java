import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControlPanelHelper {
    SimX simX ;
    Slider sdrCollision, sdrExplosion;
    Label lblCollision, lblExplosion;
    public VBox createControlPanel(final SimX simX) {
        VBox vb = new VBox(10); //spacing between elements
        vb.setAlignment(Pos.CENTER);
        this.simX=simX;
        vb.setMinSize(300f, 650f);
        vb.setPrefSize(300f, 650f);
        vb.setMaxSize(300f, 650f);
        Button btnTemp1 = new Button("1");
        Button btnTemp2 = new Button("2");
        Button btnTemp3 = new Button("3");
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
        vb.getChildren().addAll(btnTemp1, btnTemp2, btnTemp3, sdrCollision, lblCollision, sdrExplosion, lblExplosion);
        sdrCollision.valueProperty().addListener(new ChangeListener<Number>(){
        	public void changed(ObservableValue<? extends Number> observable,
        			Number oldValue, Number newValue){
        		simX.setCollisionChance(newValue.floatValue());
        		lblCollision.setText("Collision probability: " + String.format("%.2f", newValue));
        	}
        });
        sdrExplosion.valueProperty().addListener(new ChangeListener<Number>(){
        	public void changed(ObservableValue<? extends Number> observable,
        			Number oldValue, Number newValue){
        		simX.setExplosionChance(newValue.floatValue());
        		lblExplosion.setText("Explosion probability: " + String.format("%.2f", newValue));
        	}
        });
        btnTemp1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i=0;i<1000;i++) simX.run();
            }
        });
        btnTemp2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i=0;i<10000;i++) simX.run();
            }
        });
        return vb;
    }
    
    public void updateSliders(){ //call this function when collision and explosion probabilities are changed by the program to update the sliders
    	sdrCollision.setValue(simX.getCollisionChance());
    	lblCollision.setText("Collision probability: " + String.format("%.2f", sdrCollision.getValue()));
    	sdrExplosion.setValue(simX.getExplosionChance());
    	lblExplosion.setText("Explosion probability: " + String.format("%.2f", sdrExplosion.getValue()));
    }

}
