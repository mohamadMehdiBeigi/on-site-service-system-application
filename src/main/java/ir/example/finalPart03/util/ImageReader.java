package ir.example.finalPart03.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;


public class ImageReader {
    public static byte[] uploadProfilePicture(String filePath, String firstname, String lastname, String email) throws IOException {

        if (filePath.contains(".jpg")) {
            File file = new File(filePath);
            Random random = new Random();
            int randomNumber = random.nextInt(9) + 1;
            long fileSize = file.length();
            if (fileSize > 300 * 1024) {
                throw new IllegalArgumentException("The size of the photo is more than 300 KB.");
            }

            byte[] imageData = Files.readAllBytes(file.toPath());

            String fileName = firstname.concat("_").concat(lastname).concat(email).concat(".jpg");
            String destPath = "src/main/resources/savedImages/" + fileName;
            File dest = new File(destPath);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return imageData;
        } else {
            throw new IllegalArgumentException("file format must be jpg");
        }
    }
}
