package bmi.bmicalculatormysql;

import java.sql.*;

public class TranslationResultService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ui_and_db_inclass";
    private static final String DB_USER = "victoria";
    private static final String DB_PASSWORD = "victoria";

    public static void saveResult(String keyName, String languageCode, String translation) {
        String query = "INSERT INTO translations (Key_name, Language_code, translation_text) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, keyName);
            stmt.setString(2, languageCode);
            stmt.setString(3, translation);
            stmt.executeUpdate();
            System.out.println("Translation result saved to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}