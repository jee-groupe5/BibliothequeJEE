package JeeGrp5.mediatech.web.v1.dtos;

import ai.djl.modality.Classifications.Classification;

import java.util.Arrays;
import java.util.Map;

public class ImageUploadDto {
    private Classification[] objects;

    public ImageUploadDto() {
    }

    public ImageUploadDto(Classification[] objects, Map<String, String> formats) {
        this.objects = objects;
    }

    public Classification[] getObjects() {
        return objects;
    }

    public void setObjects(Classification[] objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "ImageUploadDto{" +
                "objects=" + Arrays.toString(objects) +
                '}';
    }
}
