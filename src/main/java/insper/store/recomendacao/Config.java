package insper.store.recomendacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String FILE_NAME = ".env";
    private static final String OPENAI_API_KEY = "OPENAI_API_KEY";

    public static String getOpenaiApiKey() {
        String filePath = System.getProperty("user.dir") + File.separator + FILE_NAME;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
            return properties.getProperty(OPENAI_API_KEY);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo .env: " + e.getMessage());
            return null;
        }
    }

   
}
