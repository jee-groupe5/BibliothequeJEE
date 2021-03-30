package JeeGrp5.mediatech.web.v1.dtos;

import JeeGrp5.mediatech.entities.Person;
import JeeGrp5.mediatech.models.ImageObject;

import java.util.Arrays;
import java.util.Map;

public class ImageMetadataDto {
    private Person[] persons;
    private ImageObject[] objects;
    private Map<String, String> formats;

    public ImageMetadataDto() {
    }

    public ImageMetadataDto(Person[] persons, ImageObject[] objects, Map<String, String> formats) {
        this.persons = persons;
        this.objects = objects;
        this.formats = formats;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public ImageObject[] getObjects() {
        return objects;
    }

    public void setObjects(ImageObject[] objects) {
        this.objects = objects;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }

    @Override
    public String toString() {
        return "ImageMetadataDto{" +
                "persons=" + Arrays.toString(persons) +
                ", objects=" + Arrays.toString(objects) +
                ", formats=" + formats +
                '}';
    }
}
