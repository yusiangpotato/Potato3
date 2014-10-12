import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControlPanelHelper {
    SimX simX ;
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
        vb.getChildren().addAll(btnTemp1, btnTemp2, btnTemp3);
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

}
