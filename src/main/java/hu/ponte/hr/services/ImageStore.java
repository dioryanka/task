package hu.ponte.hr.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.ponte.hr.controller.ImageMeta;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageStore {

    private static String IMAGE_HOLDER_FILE_PATH = "src/test/resources/imageHolder.json";

    private static String IMAGE_FOLDER = "C:\\senior-java-spring-web-master\\image";

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static ImageMetaHolder imageMetaHolder = new ImageMetaHolder();

    @Autowired
    private SignService signService;

    public ImageStore() {
        File file = new File(IMAGE_HOLDER_FILE_PATH);
        if (file.exists()) {
            try {
                imageMetaHolder = objectMapper.readValue(file, ImageMetaHolder.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ImageMeta> getImageMetaList() {
        return imageMetaHolder.getImageMetas();
    }

    public Optional<ImageMeta> getImageMetaById(String uuid) {
        return imageMetaHolder.getImageMetas().stream()
                .filter(imageMeta -> imageMeta.getId().equals(uuid)).findFirst();
    }

    public byte[] getImage(String uuid, String extension) {
        byte[] array = new byte[0];
        try {
            FileInputStream fileInputStream = new FileInputStream(IMAGE_FOLDER + "\\" + uuid + "." + extension);
            array = IOUtils.toByteArray(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
    }

    public boolean uploadNewImage(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        ImageMeta imageMeta = new ImageMeta();
        imageMeta.setId(uuid);
        imageMeta.setMimeType(file.getContentType());
        imageMeta.setName(FilenameUtils.getName(file.getOriginalFilename()));
        imageMeta.setSize(file.getSize());
        imageMeta.setDigitalSign(signService.generateSignature(uuid));

        imageMetaHolder.addImageMeta(imageMeta);

        return uploadImageToFileSystem(file, uuid) && persistImageHolderIntoFile();
    }

    private boolean uploadImageToFileSystem(MultipartFile file, String uuid) {
        Path filepath = Paths.get(IMAGE_FOLDER, uuid + "."
                + FilenameUtils.getExtension(file.getOriginalFilename()));
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private boolean persistImageHolderIntoFile() {
        try (FileWriter fileWriter = new FileWriter(IMAGE_HOLDER_FILE_PATH, false)) {
            objectMapper.writeValue(fileWriter, imageMetaHolder);
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }
}
