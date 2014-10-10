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

        Text txtInstructions = new Text("" +
                "One might look at the buttons on the screen and wonder, what do they do? \n" +
                "Yet another might try clicking on them, and see the effect they have.\n" +
                "Perhaps one should try to do the latter and not appear to be a total retard.");
        txtInstructions.setTextAlignment(TextAlignment.CENTER);

        bp.setCenter(txtInstructions);
        s.setResizable(false);
        s.show();
    }

}
