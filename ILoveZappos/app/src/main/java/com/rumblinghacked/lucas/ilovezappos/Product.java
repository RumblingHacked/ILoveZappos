package com.rumblinghacked.lucas.ilovezappos;

import java.io.StringReader;

/**
 * Created by Lucas on 2/1/2017.
 */

public class Product {
    public String getBrandName() {
        return brandName;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getProductId() {
        return productId;
    }

    public String getOriginalPrie() {
        return originalPrie;
    }

    public String getStyleID() {
        return styleID;
    }

    public String getColorID() {
        return colorID;
    }

    public String getPrice() {
        return price;
    }

    public String getPercentOff() {
        return percentOff;
    }

    public String getProductURL() {
        return productURL;
    }

    public String getProductName() {
        return productName;
    }

    private String brandName;
    private String thumbnailURL;
    private String productId;
    private String originalPrie;
    private String styleID;
    private String colorID;
    private String price;
    private String percentOff;
    private String productURL;
    private String productName;

    public Product(String brandName, String thumbnailURL, String productId, String originalPrie, String styleID, String colorID, String price, String percentOff, String productURL, String productName) {
        this.brandName = brandName;
        this.thumbnailURL = thumbnailURL;
        this.productId = productId;
        this.originalPrie = originalPrie;
        this.styleID = styleID;
        this.colorID = colorID;
        this.price = price;
        this.percentOff = percentOff;
        this.productURL = productURL;
        this.productName = productName;
    }
}
