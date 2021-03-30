package JeeGrp5.mediatech.entities;

public class Person {
    private String fullname;
    private boolean authorization;

    public Person() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullname='" + fullname + '\'' +
                ", authorization=" + authorization +
                '}';
    }
}
