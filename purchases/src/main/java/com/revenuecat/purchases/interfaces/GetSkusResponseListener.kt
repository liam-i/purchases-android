//  Purchases
//
//  Copyright © 2019 RevenueCat, Inc. All rights reserved.
//
package com.revenuecat.purchases.interfaces

import com.android.billingclient.api.SkuDetails
import com.revenuecat.purchases.PurchasesError

/**
 * Interface to be implemented when making calls to fetch [SkuDetails]
 */
@Deprecated(
    """
       Replace with GetStoreProductsCallback, which returns a list of StoreProducts instead of a list of SkuDetails. 
       You can use `GetSkusResponseListener.toGetStoreProductsCallback()` in Kotlin for an easy migration 
    """,
    replaceWith = ReplaceWith("GetStoreProductsCallback")
)
interface GetSkusResponseListener {
    /**
     * Will be called after SkuDetails have been fetched successfully
     *
     * @param skus List of [SkuDetails] retrieved after a successful call to fetch [SkuDetails]
     */
    fun onReceived(skus: List<SkuDetails>)

    /**
     * Will be called after the purchase has completed with error
     *
     * @param error A [PurchasesError] containing the reason for the failure when fetching the [SkuDetails]
     */
    fun onError(error: PurchasesError)
}
