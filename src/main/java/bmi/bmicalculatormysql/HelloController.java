package bmi.bmicalculatormysql;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import static bmi.bmicalculatormysql.TranslationResultService.saveResult;

public class HelloController {
    @FXML
    private ComboBox<String> languageSelector;
    @FXML
    private ListView<String> translationsList;
    @FXML
    private TextField keyNameField;
    @FXML
    private TextField translationField;
    @FXML
    private Button addTranslationButton;


    @FXML
    public void initialize() {
        languageSelector.getItems().addAll("English", "Finnish", "Chinese");
        languageSelector.setValue("English");
        languageSelector.setOnAction(event -> setLanguage());

        getLanguageCode("en");

        addTranslationButton.setOnAction(event -> {
            String key = keyNameField == null ? "" : keyNameField.getText();
            String translation = translationField == null ? "" : translationField.getText();
            String langCode = getLanguageCode(languageSelector == null ? "English" : languageSelector.getValue());
            saveResult(key, langCode, translation);
        });
    }

    @FXML
    private void setLanguage() {
        String languageCode = getLanguageCode(languageSelector.getValue());
        getLanguageCode(languageCode);
    }

    private String getLanguageCode(String language) {
        return switch (language) {
            case "Finnish" -> "fi";
            case "Chinese" -> "ch";
            default -> "en";
        };
    }

}
