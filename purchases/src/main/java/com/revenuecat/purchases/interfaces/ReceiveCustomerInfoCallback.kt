//  Purchases
//
//  Copyright © 2019 RevenueCat, Inc. All rights reserved.
//
package com.revenuecat.purchases.interfaces

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.PurchaserInfo
import com.revenuecat.purchases.PurchasesError

/**
 * Interface to be implemented when making calls that return a [CustomerInfo]
 */
interface ReceiveCustomerInfoCallback {
    /**
     * Will be called after the call has completed.
     * @param customerInfo [CustomerInfo] class sent back when the call has completed
     */
    fun onReceived(customerInfo: CustomerInfo)

    /**
     * Will be called after the call has completed with an error.
     * @param error A [PurchasesError] containing the reason for the failure of the call
     */
    fun onError(error: PurchasesError)
}

@Deprecated("Deprecated in favor of ReceiveCustomerInfoCallback. This helper will be removed in a future release.")
fun ReceivePurchaserInfoListener.toReceiveCustomerInfoCallback(): ReceiveCustomerInfoCallback {
    return object : ReceiveCustomerInfoCallback {
        override fun onReceived(customerInfo: CustomerInfo) {
            this@toReceiveCustomerInfoCallback.onReceived(PurchaserInfo(customerInfo))
        }

        override fun onError(error: PurchasesError) {
            this@toReceiveCustomerInfoCallback.onError(error)
        }
    }
}
