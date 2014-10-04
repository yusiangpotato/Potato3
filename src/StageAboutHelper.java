import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class StageAboutHelper {

	public static void createStageAbout() {
		Stage s = new Stage();
		s.setTitle("About");
		BorderPane bp = new BorderPane();
		s.setScene(new Scene(bp));
		
		Text txtAbout = new Text("This program is created by the four geniuses from M14404\n" +
                "Ian Yong, Leong Yu Siang, Rachel Chan and Winsen Alfiano Hijani.\n" +
                "© All rights reserved.");
		txtAbout.setTextAlignment(TextAlignment.CENTER);
		
		bp.setCenter(txtAbout);
		s.setResizable(false);
		s.show();
	}

}
