package ru.autotestframework.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.autotestframework.core.FileLoader;
import ru.autotestframework.core.FileLoaderImpl;
import ru.autotestframework.util.Validator;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ClaimFileManager {

    private static final String FILE_NAME = "temp/used_claims.txt";

    private File file;

    @Autowired
    private FileLoaderImpl fileLoader;

    public void createClaimFile(@TempDir Path path) throws IOException {
        file = path.resolve("used_claims.txt").toFile();
        Validator.checkThat(!file.exists(), "File {} already exists", new Object[]{path});
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw Validator.exception("Error creating new directory.", new Object[0]);
        } else if (!file.createNewFile()) {
            throw Validator.exception("Error creating new file.", new Object[0]);
        }
    }

    public void writeClaimToFileIfNotExists(String claim) {
        if (!isClaimExists(claim)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(claim);
                writer.newLine();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            log.info("Заявка уже существует в файле: {}", claim);
        }
    }

    private boolean isClaimExists(String claim) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(claim)) {
                    return true; // Строка найдена
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public List<String> readClaimsFromFile() {
        List<String> claims = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                claims.add(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return claims;
    }

    public void clearClaimsFile() {
        file.delete();
    }
}
