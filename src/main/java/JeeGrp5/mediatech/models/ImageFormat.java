package JeeGrp5.mediatech.models;

public enum ImageFormat {
    FHD(1920, 1080),
    HD(1280, 720),
    DVD(720, 480),
    UHD(3840, 2160);

    private final int width;
    private final int height;

    ImageFormat(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
