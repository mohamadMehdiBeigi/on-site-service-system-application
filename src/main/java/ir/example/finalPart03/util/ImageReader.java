package ir.example.finalPart03.util;

import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class ImageReader {
    public static byte[] uploadProfilePicture(MultipartFile file, String firstname, String lastname, String email) throws IOException {


//        String filePath = FilenameUtils.getExtension(file.getName());
        String fileAbsolutePath = new File(Objects.requireNonNull(file.getOriginalFilename())).getAbsolutePath();

        if (!fileAbsolutePath.contains(".jpg")) {
            throw new BadRequestException("this image you choose must've a jpg file");
        }

        if (file.getBytes().length > 300 * 1024) {
            throw new BadRequestException("The size of the photo is more than 300 KB.");
        }

        return file.getInputStream().readAllBytes();
    }
}
