package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class ProductListModel {
    @SerializedName("productName")
    private String productNameCombo;
    @SerializedName("productCategory")
    private String productCategoryCombo;
    @SerializedName("productVariation")
    private String productVariationCombo;
    @SerializedName("productImage")
    private String imageCombo;
    @SerializedName("price")
    private int priceCombo;
    @SerializedName("code")
    private String codeCombo;
    @SerializedName("stocks")
    private String stocks;
    @SerializedName("preparationTime")
    private String preparationTime;
    @SerializedName("mainIngredients")
    private String mainIngredients;

    public ProductListModel(String productNameCombo, String productCategoryCombo, String productVariationCombo, String imageCombo, int priceCombo, String codeCombo, String stocks, String preparationTime,String mainIngredients) {
        this.productNameCombo = productNameCombo;
        this.productCategoryCombo = productCategoryCombo;
        this.productVariationCombo = productVariationCombo;
        this.imageCombo = imageCombo;
        this.priceCombo = priceCombo;
        this.codeCombo = codeCombo;
        this.stocks = stocks;
        this.preparationTime = preparationTime;
        this.mainIngredients = mainIngredients;
    }
    public String getProductName() {
        return productNameCombo;
    }
    public String getProductCategoryCombo() {
        return productCategoryCombo;
    }
    public String getProductVariation() {
        return productVariationCombo;
    }
    public String getImage() {
        return imageCombo;
    }
    public int getPrice() {
        return priceCombo;
    }
    public String getCodeCombo() {
        return codeCombo;
    }
    public String getStocks() {
        return stocks;
    }
    public String getPreparationTime() {
        return preparationTime;
    }
    public String getMainIngredients() {
        return mainIngredients;
    }
}
