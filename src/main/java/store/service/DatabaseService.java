package store.service;

import store.core.constant.Constant;
import store.core.constant.FileInfo;
import store.model.Product;
import store.model.Promotion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private final String PRODUCTS_FILE_NAME = "products.md";
    private final String PROMOTIONS_FILE_NAME = "promotions";
    private final String PRODUCTS_HEAD_LINE = "name,price,quantity,promotion";
    private final int PROMOTION_IDX = 0;
    private final int NONPROMOTION_IDX = 1;

    private Parser parser = new Parser();

    public List<Product> readAllProducts() throws IOException {
        String filePath = FileInfo.findFilePath(PRODUCTS_FILE_NAME);
        if (filePath != null) {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            return parser.productsInfo(reader);
        }
        return null;
    }

    public List<Promotion> readAllPromotions() throws IOException {
        String filePath = FileInfo.findFilePath(PROMOTIONS_FILE_NAME);
        if (filePath != null) {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            return parser.promotionsInfo(reader);
        }
        return null;
    }

    public Product readTargetProduct(String name, boolean promotionExist) throws IOException {
        List<Product> products = readAllProducts();
        int[] targetIndexes = findTargetProductIdx(products, name);
        try {
            if (promotionExist) {
                return products.get(targetIndexes[PROMOTION_IDX]);
            }
            return products.get(targetIndexes[NONPROMOTION_IDX]);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Promotion readTargetPromotion(String name) throws IOException {
        List<Promotion> promotions = readAllPromotions();
        for (Promotion promotion : promotions) {
            if (promotion.getName().equalsIgnoreCase(name)) return promotion;
        }
        return null;
    }

    public int totalQuantity(String productName){
        try{
            return readTargetProduct(productName, false).getQuantity() + readTargetProduct(productName, true).getQuantity();
        } catch (IOException e) {
            return -1;
        }
    }

    public List<String> readAllProductsName() {
        try {
            List<String> allProductsName = new ArrayList<>();
            for (Product product : readAllProducts()) {
                if (!allProductsName.contains(product.getName())) allProductsName.add(product.getName());
            }
            return allProductsName;
        } catch (IOException e) {
            return null;
        }
    }

    public void updateProductQuantity(String name, boolean operator, int changedQuantities) throws IOException {
        List<Product> products = readAllProducts();
        int[] targetIndexes = findTargetProductIdx(products, name);
        updateProductQuantity(products, targetIndexes, operator, changedQuantities);

        BufferedWriter writer = new BufferedWriter(new FileWriter(FileInfo.findFilePath(PRODUCTS_FILE_NAME)));
        writer.write(PRODUCTS_HEAD_LINE + System.lineSeparator());
        for (Product product : products) {
            writer.write(product.getFileLine() + System.lineSeparator());
        }
        writer.close();
    }

    private int[] findTargetProductIdx(List<Product> products, String name) {
        int[] targetIndexes = new int[]{-1, -1};
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(name)) {
                if (!products.get(i).getPromotion().equals(Constant.NULL)) targetIndexes[PROMOTION_IDX] = i;
                else targetIndexes[NONPROMOTION_IDX] = i;
            }
        }
        return targetIndexes;
    }

    private void updateProductQuantity(List<Product> products, int[] targetIndexes, boolean operator, int changedQuantities) {
        int beforeQuantity = 0;
        if (targetIndexes[PROMOTION_IDX] != -1) {
            beforeQuantity = products.get(targetIndexes[PROMOTION_IDX]).getQuantity();
            if (beforeQuantity >= changedQuantities) {
                products.get(targetIndexes[PROMOTION_IDX]).updateStock(operator, changedQuantities);
                return;
            }
            products.get(targetIndexes[PROMOTION_IDX]).updateStock(operator, beforeQuantity);
        }
        products.get(targetIndexes[NONPROMOTION_IDX]).updateStock(operator, changedQuantities - beforeQuantity);
    }

    private class Parser {
        private final String DELIMITER = ",";

        public List<Product> productsInfo(BufferedReader reader) throws IOException {
            List<Product> fileInfo = new ArrayList<>();
            String headLine = reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                fileInfo.add(createProductObj(line));
            }
            reader.close();
            return fileInfo;
        }

        public List<Promotion> promotionsInfo(BufferedReader reader) throws IOException {
            List<Promotion> fileInfo = new ArrayList<>();
            String headLine = reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                fileInfo.add(createPromotionObj(line));
            }
            reader.close();
            return fileInfo;
        }

        private Product createProductObj(String line) {
            String[] info = line.split(DELIMITER);

            return new Product(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]);
        }

        private Promotion createPromotionObj(String line) {
            String[] info = line.split(DELIMITER);

            return new Promotion(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3], info[4]);
        }
    }
}
