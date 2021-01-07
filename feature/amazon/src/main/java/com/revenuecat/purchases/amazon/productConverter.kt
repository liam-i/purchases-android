package com.revenuecat.purchases.amazon

import com.amazon.device.iap.internal.model.ProductBuilder
import com.amazon.device.iap.model.Product
import com.revenuecat.purchases.models.ProductDetails
import org.json.JSONObject
import java.text.NumberFormat
import java.text.ParseException
import java.util.Currency
import java.util.Locale
import com.amazon.device.iap.model.ProductType as AmazonProductType

val ProductDetails.amazonProduct: Product
    get() = ProductBuilder()
        .setSku(originalJson.getString("sku"))
        .setProductType(originalJson.getProductType("productType"))
        .setDescription(originalJson.getString("description"))
        .setPrice(originalJson.getString("price"))
        .setSmallIconUrl(originalJson.getString("smallIconUrl"))
        .setTitle(originalJson.getString("title"))
        .setCoinsRewardAmount(originalJson.getInt("coinsRewardAmount")).build()

fun Product.toProductDetails(marketplace: String): ProductDetails {
    val numberFormat = NumberFormat.getInstance()

    val currency = Currency.getInstance(Locale("EN", marketplace))
    val currencySymbol = currency.symbol

    val priceDouble: Double =
        if (price.startsWith(currencySymbol)) {
            // Looks like Amazon always prefixes the price with the currency, no matter the Locale
            val formattedPriceWithoutSymbol = price.removePrefix(currencySymbol)
            try {
                numberFormat.parse(formattedPriceWithoutSymbol).toDouble()
            } catch (e: ParseException) {
                0.0
            }
        } else {
            0.0
        }

    return ProductDetails(
        sku,
        productType.toRevenueCatProductType(),
        price,
        priceAmountMicros = priceDouble.times(1000000).toLong(),
        priceCurrencyCode = currency.currencyCode,
        originalPrice = null,
        originalPriceAmountMicros = 0,
        title,
        description,
        subscriptionPeriod = null,
        freeTrialPeriod = null,
        introductoryPrice = null,
        introductoryPriceAmountMicros = 0,
        introductoryPricePeriod = null,
        introductoryPriceCycles = 0,
        iconUrl = smallIconUrl,
        toJSON()
    )
}

private fun JSONObject.getProductType(productType: String) =
    AmazonProductType.values().firstOrNull { it.name == productType }
