package store.constant;

public enum Files {
    PRODUCTS("/src/main/resources/products.md", "products.md", "products"),
    PROMOTIONS("/src/main/resources/promotions.md", "promotions.md", "promotions");

    private final String filePath;
    private final String fileNameWithExtension;
    private final String fileNameWithoutExtension;

    Files(String filePath, String fileNameWithExtension, String fileNameWithoutExtension) {
        this.filePath = filePath;
        this.fileNameWithExtension = fileNameWithExtension;
        this.fileNameWithoutExtension = fileNameWithoutExtension;
    }

    public String getFilePath(String fileName) {
        if(fileName.equalsIgnoreCase(fileNameWithExtension) || fileName.equalsIgnoreCase(fileNameWithoutExtension)){
            return filePath;
        }
        return "";
    }

    public String getFileNameWithExtension() {
        return fileNameWithExtension;
    }

    public String getFileNameWithoutExtension() {
        return fileNameWithoutExtension;
    }
}
