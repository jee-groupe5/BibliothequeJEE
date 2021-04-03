package JeeGrp5.mediatech.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "persons")
public class Person {
    private String fullname;

    public Person() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullname='" + fullname + '\'' +
                '}';
    }
}
