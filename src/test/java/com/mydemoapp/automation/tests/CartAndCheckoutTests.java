package com.mydemoapp.automation.tests;

import com.mydemoapp.automation.base.BaseTest;
import com.mydemoapp.automation.models.PaymentCard;
import com.mydemoapp.automation.models.ShippingAddress;
import com.mydemoapp.automation.pages.CartPage;
import com.mydemoapp.automation.pages.CheckoutCompletePage;
import com.mydemoapp.automation.pages.CheckoutInfoPage;
import com.mydemoapp.automation.pages.CheckoutPaymentPage;
import com.mydemoapp.automation.pages.PlaceOrderPage;
import com.mydemoapp.automation.pages.ProductDetailPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.mydemoapp.automation.pages.CatalogPage.SAUCE_LABS_BACKPACK;

@Epic("My Demo App")
@Feature("Cart & Checkout")
public class CartAndCheckoutTests extends BaseTest {

    @Test(description = "Adding a product updates the header cart badge and the cart screen contents")
    public void addingProductToCartUpdatesCartBadgeAndCartScreen() {
        ProductDetailPage detailPage = catalogPage.selectProduct(SAUCE_LABS_BACKPACK);

        detailPage.increaseQuantity(1); // quantity 2
        detailPage.addToCart();

        Assert.assertEquals(detailPage.header.getCartItemCount(), 2,
                "Cart badge should reflect the quantity added, not just the number of add-to-cart taps");

        CartPage cartPage = detailPage.openCart();
        Assert.assertFalse(cartPage.isEmpty(), "Cart screen should no longer report an empty cart");
        Assert.assertEquals(cartPage.getItemsCountText(), "2 Items",
                "Cart screen item count should match what was added on the detail screen");
    }

    @Test(description = "A logged-in user can complete the full checkout flow end to end")
    public void completeCheckoutFlowEndToEnd() {
        catalogPage = catalogPage.openMenu().openLogin().loginAsStandardUser();

        ProductDetailPage detailPage = catalogPage.selectProduct(SAUCE_LABS_BACKPACK);
        detailPage.addToCart();

        CartPage cartPage = detailPage.openCart();
        CheckoutInfoPage checkoutInfoPage = cartPage.proceedToCheckout();

        checkoutInfoPage.fillShippingAddress(ShippingAddress.valid());
        CheckoutPaymentPage paymentPage = checkoutInfoPage.continueToPayment();

        paymentPage.fillPaymentDetails(PaymentCard.valid());
        PlaceOrderPage placeOrderPage = paymentPage.reviewOrder();

        CheckoutCompletePage completePage = placeOrderPage.placeOrder();

        Assert.assertEquals(completePage.getCompleteTitle(), "Checkout Complete",
                "Completing the order should show the checkout-complete confirmation screen");
        Assert.assertEquals(completePage.getThankYouText(), "Thank you for your order",
                "Confirmation screen should thank the user for their order");
    }
}
