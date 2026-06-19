package com.mydemoapp.automation.tests;

import com.mydemoapp.automation.base.BaseTest;
import com.mydemoapp.automation.pages.AboutPage;
import com.mydemoapp.automation.pages.CartPage;
import com.mydemoapp.automation.pages.MenuPage;
import com.mydemoapp.automation.pages.ProductDetailPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.mydemoapp.automation.pages.CatalogPage.SAUCE_LABS_BACKPACK;

@Epic("My Demo App")
@Feature("Navigation")
public class NavigationTests extends BaseTest {

    @Test(description = "Tapping a catalog item navigates to that product's detail screen")
    public void tappingProductOpensProductDetail() {
        ProductDetailPage detailPage = catalogPage.selectProduct(SAUCE_LABS_BACKPACK);

        Assert.assertEquals(detailPage.getProductTitle(), SAUCE_LABS_BACKPACK,
                "Product detail screen should display the title of the product that was tapped");
    }

    @Test(description = "The navigation drawer opens secondary screens such as About")
    public void drawerNavigatesToAboutScreen() {
        MenuPage menu = catalogPage.openMenu();

        AboutPage aboutPage = menu.openAbout();

        Assert.assertEquals(aboutPage.getScreenTitle(), "About",
                "Selecting 'About' in the drawer should open the About screen");
    }

    @Test(description = "The header cart icon navigates to the cart screen from the catalog")
    public void headerCartIconNavigatesToCartScreen() {
        CartPage cartPage = catalogPage.openCart();

        Assert.assertTrue(cartPage.isEmpty(),
                "A freshly launched session should start with an empty cart");
    }

    @Test(description = "The drawer can navigate back to the Catalog screen from another screen")
    public void drawerNavigatesBackToCatalog() {
        AboutPage aboutPage = catalogPage.openMenu().openAbout();

        catalogPage = aboutPage.openMenu().openCatalog();

        Assert.assertEquals(catalogPage.getScreenTitle(), "Products",
                "Selecting 'Catalog' in the drawer should return to the product catalog");
    }
}
