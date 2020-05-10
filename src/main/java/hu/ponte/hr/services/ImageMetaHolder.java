package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;

import java.util.ArrayList;
import java.util.List;

public class ImageMetaHolder {

    private List<ImageMeta> imageMetas = new ArrayList<>();

    public List<ImageMeta> getImageMetas() {
        return this.imageMetas;
    }

    public void addImageMeta(ImageMeta imageMeta) {
        this.imageMetas.add(imageMeta);
    }
}
