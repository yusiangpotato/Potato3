import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControlPanelHelper {

	public VBox createControlPanel() {
		VBox vb = new VBox(10); //spacing between elements
		vb.setAlignment(Pos.CENTER);

        vb.setMinSize(300f,650f);
        vb.setPrefSize(300f,650f);
        vb.setMaxSize(300f,650f);
		Button btnTemp1 = new Button("1");
		Button btnTemp2 = new Button("2");
		Button btnTemp3 = new Button("3");
		vb.getChildren().addAll(btnTemp1, btnTemp2, btnTemp3);
		
		return vb;
	}

}
