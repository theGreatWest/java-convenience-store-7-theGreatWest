package store.core.constant;

public enum FileInfo {
    PRODUCTS("src/main/resources/products.md", "products","products.md",18),
    PROMOTIONS("src/main/resources/promotions.md", "promotions","promotions.md",3);

    private final String filePath;
    private final String fileName;
    private final String fileNameWithExtension;
    private final int numberItems;

    FileInfo(String filePath, String fileName, String fileNameWithExtension, int numberItems) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileNameWithExtension = fileNameWithExtension;
        this.numberItems = numberItems;
    }

    public static String findFilePath(String fileName){
        FileInfo targetObj = findTargetObj(fileName);

        if(targetObj==null) return null;

        return targetObj.filePath;
    }

    public static int findNumberTargetItems(String fileName){
        FileInfo targetObj = findTargetObj(fileName);

        if(targetObj==null) return -1;

        return targetObj.numberItems;
    }

    public static FileInfo findTargetObj(String fileName){
        if(fileName.equalsIgnoreCase(PRODUCTS.fileName) || fileName.equalsIgnoreCase(PRODUCTS.fileNameWithExtension)){
            return PRODUCTS;
        }

        if(fileName.equalsIgnoreCase(PROMOTIONS.fileName) || fileName.equalsIgnoreCase(PROMOTIONS.fileNameWithExtension)){
            return PROMOTIONS;
        }

        return null;
    }
}
