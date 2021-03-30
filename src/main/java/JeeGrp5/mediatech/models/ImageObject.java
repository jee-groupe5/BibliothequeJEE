package JeeGrp5.mediatech.models;

public class ImageObject {
    private String objectName;
    private float probability;

    public ImageObject() {
    }

    public ImageObject(String objectName, float probability) {
        this.objectName = objectName;
        this.probability = probability;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "ImageObject{" +
                "objectName='" + objectName + '\'' +
                ", probability=" + probability +
                '}';
    }
}
