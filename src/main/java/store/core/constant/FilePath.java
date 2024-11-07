package store.core.constant;

public enum FilePath {
    PRODUCTS("/src/main/resources/products.md", "products"),
    PROMOTIONS("/src/main/resources/promotions.md", "promotions");

    private final String filePath;
    private final String fileNameWithoutExtension;

    FilePath(String filePath, String fileNameWithoutExtension) {
        this.filePath = filePath;
        this.fileNameWithoutExtension = fileNameWithoutExtension;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileNameWithoutExtension() {
        return fileNameWithoutExtension;
    }
}
