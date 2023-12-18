import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    private TextArea inputTextArea;
    private TextArea resultTextArea;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Word Counter");

        inputTextArea = new TextArea();
        inputTextArea.setPromptText("Enter text or choose a file...");

        Button browseButton = new Button("Browse");
        browseButton.setOnAction(e -> browseFile());

        Button countButton = new Button("Count Words");
        countButton.setOnAction(e -> countWords());

        resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(inputTextArea, browseButton, countButton, resultTextArea);

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void browseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Text File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                String fileContent = new String(Files.readAllBytes(selectedFile.toPath()));
                inputTextArea.setText(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void countWords() {
        String text = inputTextArea.getText();
        String[] words = text.split("[\\s.,;:!?()\\[\\]{}'\"]+");

        int wordCount = 0;
        Map<String, Integer> wordFrequency = new HashMap<>();

        for (String word : words) {
            if (!isCommonWord(word)) {
                wordCount++;
                wordFrequency.put(word.toLowerCase(), wordFrequency.getOrDefault(word.toLowerCase(), 0) + 1);
            }
        }

        resultTextArea.setText("Total words: " + wordCount + "\n\nWord Statistics:\n");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            resultTextArea.appendText(entry.getKey() + ": " + entry.getValue() + " times\n");
        }
    }

    private static boolean isCommonWord(String word) {
        String[] commonWords = { "the", "and", "is", "in", "it", "you", "that", "to", "of", "for" };
        for (String common : commonWords) {
            if (word.equalsIgnoreCase(common)) {
                return true;
            }
        }
        return false;
    }
}
