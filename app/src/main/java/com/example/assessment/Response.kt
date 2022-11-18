package com.example.assessment

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("products") val products: List<ProductsItem?>? = null
)

data class ProductsItem(

    @field:SerializedName("isInTrolley") val isInTrolley: Boolean? = null,

    @field:SerializedName("isInWishlist") val isInWishlist: Boolean? = null,

    @field:SerializedName("purchaseTypes") val purchaseTypes: List<PurchaseTypesItem?>? = null,

    @field:SerializedName("isDeliveryOnly") val isDeliveryOnly: Boolean? = null,

    @field:SerializedName("saleUnitPrice") val saleUnitPrice: Double? = null,

    @field:SerializedName("title") val title: String? = null,

    @field:SerializedName("ratingCount") val ratingCount: Double? = null,

    @field:SerializedName("totalReviewCount") val totalReviewCount: Int? = null,

    @field:SerializedName("badges") val badges: List<String?>? = null,

    @field:SerializedName("isAddToCartEnable") val isAddToCartEnable: Boolean? = null,

    @field:SerializedName("isDirectFromSupplier") val isDirectFromSupplier: Boolean? = null,

    @field:SerializedName("price") val price: List<PriceItem?>? = null,

    @field:SerializedName("isFindMeEnable") val isFindMeEnable: Boolean? = null,

    @field:SerializedName("imageURL") val imageURL: String? = null,

    @field:SerializedName("messages") val messages: Messages? = null,

    @field:SerializedName("id") val id: String? = null,

    @field:SerializedName("brand") val brand: String? = null,

    @field:SerializedName("addToCartButtonText") val addToCartButtonText: String? = null,

    @field:SerializedName("citrusId") val citrusId: String? = null,

    @field:SerializedName("isChecked") var isChecked: Boolean? = false,


    )

data class PriceItem(

    @field:SerializedName("message") val message: String? = null,

    @field:SerializedName("value") val value: Double? = null,

    @field:SerializedName("isOfferPrice") val isOfferPrice: Boolean? = null
)

data class Sash(
    val any: Any? = null
)

data class PurchaseTypesItem(

    @field:SerializedName("unitPrice") val unitPrice: Double? = null,

    @field:SerializedName("minQtyLimit") val minQtyLimit: Int? = null,

    @field:SerializedName("displayName") val displayName: String? = null,

    @field:SerializedName("maxQtyLimit") val maxQtyLimit: Int? = null,

    @field:SerializedName("cartQty") val cartQty: Int? = null,

    @field:SerializedName("purchaseType") val purchaseType: String? = null
)

data class Messages(

    @field:SerializedName("sash") val sash: Sash? = null,

    @field:SerializedName("promotionalMessage") val promotionalMessage: String? = null,

    @field:SerializedName("secondaryMessage") val secondaryMessage: String? = null
)
