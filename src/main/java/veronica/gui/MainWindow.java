package veronica.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import veronica.main.Veronica;
import veronica.ui.Ui;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Veronica veronica;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser1.png"));
    private final Image veronicaImage = new Image(this.getClass().getResourceAsStream("/images/DaVeronica.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.setPromptText("Type here...");
    }

    /** Injects the Veronica instance */
    public void setVeronica(Veronica v) {
        veronica = v;
        greetUser();
    }

    public void greetUser() {
        dialogContainer.getChildren().add(
                DialogBox.getVeronicaDialog(Ui.showGreetMessage(), veronicaImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Veronica's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = Veronica.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getVeronicaDialog(response, veronicaImage)
        );
        userInput.clear();
    }
}
