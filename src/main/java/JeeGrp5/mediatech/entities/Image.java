package JeeGrp5.mediatech.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

@Document(collection = "images")
public class Image implements Serializable {
    @Id
    private String id;

    private String category;
    private String name;
    private Person[] persons;
    private String[] objects;
    private boolean published;
    private String description;
    private String[] tags;
    private Map<String, String> urls;
    private boolean archived;

    public Image() {
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String[] getObjects() {
        return objects;
    }

    public void setObjects(String[] objects) {
        this.objects = objects;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", persons=" + Arrays.toString(persons) +
                ", objects=" + Arrays.toString(objects) +
                ", published=" + published +
                ", description='" + description + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", urls=" + urls +
                ", archived=" + archived +
                '}';
    }
}
