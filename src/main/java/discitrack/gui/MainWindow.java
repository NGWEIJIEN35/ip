package discitrack.gui;

import discitrack.DisciTrack;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controls user interactions in the main DisciTrack window.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private DisciTrack disciTrack;

    /**
     * Connects the graphical interface to the DisciTrack application logic.
     *
     * @param disciTrack the application instance used to process commands.
     */
    public void setDisciTrack(DisciTrack disciTrack) {
        this.disciTrack = disciTrack;
        dialogContainer.getChildren().add(DialogBox.getDisciTrackDialog(disciTrack.getGreeting()));
        Platform.runLater(userInput::requestFocus);
    }

    @FXML
    private void initialize() {
        dialogContainer.heightProperty().addListener((observable, oldValue, newValue) ->
                scrollPane.setVvalue(1.0));
        sendButton.disableProperty().bind(userInput.textProperty().isEmpty());
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty() || disciTrack == null) {
            return;
        }

        String response = disciTrack.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getDisciTrackDialog(response));
        userInput.clear();
        scrollToLatestMessage();

        if (disciTrack.shouldExit()) {
            closeAfterFarewell();
        } else {
            userInput.requestFocus();
        }
    }

    @FXML
    private void handleHelp() {
        if (disciTrack == null) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getDisciTrackDialog(disciTrack.getResponse("help")));
        userInput.requestFocus();
        scrollToLatestMessage();
    }

    private void scrollToLatestMessage() {
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }

    private void closeAfterFarewell() {
        userInput.setDisable(true);
        PauseTransition exitDelay = new PauseTransition(Duration.seconds(2));
        exitDelay.setOnFinished(event -> Platform.exit());
        exitDelay.play();
    }
}
