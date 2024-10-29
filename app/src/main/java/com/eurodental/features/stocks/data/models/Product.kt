package com.eurodental.features.stocks.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("product_name") val productName: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("stock_quantity") val stockQuantity: Int,
    @SerializedName("has_warranty") val hasWarranty: Boolean,
    @SerializedName("warranty_duration_months") val warrantyDurationMonths: Int,
    @SerializedName("image_id") val imageId: Int,
    @SerializedName("id_category") val idCategory: Int,
    @SerializedName("id_sub_category") val idSubCategory: Int,
    @SerializedName("id_brand") val idBrand: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("reference") val reference: String,
    @SerializedName("image_path") val imagePath: String,
    @SerializedName("category_name") val categoryName: String,
    @SerializedName("sub_category_name") val subCategoryName: String,
    @SerializedName("brand_name") val brandName: String
) : Serializable
