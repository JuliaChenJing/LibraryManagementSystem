package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckoutController {

	public void openCheckout() {
		Stage libStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(
					"../view/CheckOut.fxml"));
			libStage.setTitle("Checkout");
			Scene scene = new Scene(root, 1000, 800);
			libStage.setScene(scene);
			libStage.show();

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
