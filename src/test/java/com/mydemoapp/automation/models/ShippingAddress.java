package com.mydemoapp.automation.models;

/**
 * Immutable carrier for the "Enter a shipping address" checkout form, used
 * to avoid long, error-prone parameter lists in page object methods and
 * test data builders.
 */
public record ShippingAddress(
        String fullName,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String zip,
        String country
) {

    public static ShippingAddress valid() {
        return new ShippingAddress(
                "Jane Doe",
                "421 Baker Street",
                "Apt 12",
                "London",
                "Greater London",
                "NW1 6XE",
                "United Kingdom");
    }

    /** Same as {@link #valid()} but with every required field blanked out, for negative tests. */
    public static ShippingAddress empty() {
        return new ShippingAddress("", "", "", "", "", "", "");
    }
}
