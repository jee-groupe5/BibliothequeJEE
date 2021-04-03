package JeeGrp5.mediatech.web.v1.dtos;

import ai.djl.modality.Classifications.Classification;

import java.util.Arrays;

public class ImageUploadDto {
    private String[] objects;
    private String id;

    public ImageUploadDto() {
    }

    public ImageUploadDto(String[] objects, String id) {
        this.objects = objects;
        this.id = id;
    }

    public String[] getObjects() {
        return objects;
    }

    public void setObjects(String[] objects) {
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
