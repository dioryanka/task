package hu.ponte.hr.controller;


import hu.ponte.hr.services.ImageStore;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
		return this.imageStore.getImageMetaList();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            Optional<ImageMeta> imageMetaOptional = imageStore.getImageMetaById(id);

            if (imageMetaOptional.isPresent()) {
                String extension = FilenameUtils.getExtension(imageMetaOptional.get().getName());
                ServletOutputStream sos = response.getOutputStream();
                response.setHeader("Content-Disposition", "attachment; filename="
                        + imageMetaOptional.get().getName() + "." + extension);
                response.setContentType(imageMetaOptional.get().getMimeType());
                sos.write(imageStore.getImage(imageMetaOptional.get().getId(), extension));
                sos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
