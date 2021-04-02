package JeeGrp5.mediatech.web.v1.dtos;

import ai.djl.modality.Classifications.Classification;

import java.util.Arrays;

public class ImageUploadDto {
    private Classification[] objects;
    private String id;

    public ImageUploadDto() {
    }

    public ImageUploadDto(Classification[] objects, String id) {
        this.objects = objects;
        this.id = id;
    }

    public Classification[] getObjects() {
        return objects;
    }

    public void setObjects(Classification[] objects) {
        this.objects = objects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ImageUploadDto{" +
                "objects=" + Arrays.toString(objects) +
                ", id='" + id + '\'' +
                '}';
    }
}
