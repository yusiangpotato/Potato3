import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class StageCommandHelper {

    public static void createStageCommands() {
        Stage s = new Stage();
        s.setTitle("Commands");
        BorderPane bp = new BorderPane();
        s.setScene(new Scene(bp));

        Text txtInstructions = new Text("" +
                "Available commands: \n" +
                "FREQ f : Sets execution frequency to f Hz\n" +
                "CLR : Clears simulation particles\n" +
                "FF n : Quickly executes n cycles into the future\n" +
                "CP/EP f : Sets collision/explosion frequency to f\n" +
                "H+/HA/A-/OH- n : Adds n number of specified particle\n" +
                "");
        txtInstructions.setTextAlignment(TextAlignment.CENTER);

        bp.setCenter(txtInstructions);
        s.setResizable(false);
        s.show();
    }

}
