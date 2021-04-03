package JeeGrp5.mediatech.web.v1.dtos;

import JeeGrp5.mediatech.entities.Person;

public class ImageCreateDto {
    private String category;
    private String name;
    private Person[] persons;
    private boolean published;
    private String description;
    private String[] tags;

    public ImageCreateDto() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }
}
