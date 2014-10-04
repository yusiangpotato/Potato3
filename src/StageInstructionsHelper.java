import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class StageInstructionsHelper {

	public static void createStageInstructions() {
		Stage s = new Stage();
		s.setTitle("Instructions");
		BorderPane bp = new BorderPane();
		s.setScene(new Scene(bp));
		
		Text txtInstructions = new Text("Temp"); //TODO
		txtInstructions.setTextAlignment(TextAlignment.CENTER);
		
		bp.setCenter(txtInstructions);
		s.setResizable(false);
		s.show();
	}

}
