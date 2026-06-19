package com.mydemoapp.automation.models;

/** Immutable carrier for the "Enter a payment method" checkout form. */
public record PaymentCard(String cardHolderName, String cardNumber, String expirationDate, String securityCode) {

    public static PaymentCard valid() {
        return new PaymentCard("Jane Doe", "4111111111111111", "12/29", "123");
    }
}
