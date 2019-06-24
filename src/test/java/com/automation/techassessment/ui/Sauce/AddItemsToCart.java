/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.ui.Sauce;

import com.automation.techassessment.ui.pages.sauce.CartPage;
import com.automation.techassessment.ui.pages.sauce.DashboardPage;
import com.automation.techassessment.ui.pages.sauce.LoginPage;
import com.automation.techassessment.ui.pages.sauce.MenuBar;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 *
 * @author Jeff Bickmore
 */
public class AddItemsToCart extends BaseUITest {
    public static final LoginPage login = new LoginPage();
    public static final DashboardPage dashboard = new DashboardPage();
    public static final MenuBar menuBar = new MenuBar();
    public static final CartPage cartpage = new CartPage();
    @Test
    public void addItemsToCart(){
        SoftAssert softAssert = new SoftAssert();
        login.login("standard_user", "secret_sauce");
        softAssert.assertTrue(menuBar.menuBarButtonExists(), "Unable to find Menu Bar button, login must have failed");
        softAssert.assertTrue(dashboard.productContainerIsVisible(), "Unable to find Product Container, dashboard must have failed to load");
        List<String>productsToAdd = Arrays.asList("Sauce Labs Onesie", "Sauce Labs Bike Light");
        //add the products into the cart
        try{
            dashboard.addProductsToCart(productsToAdd);
        }catch(Exception e){
            softAssert.assertTrue(false, e.getMessage());
        }
        //check to see if the items actually made it to the cart
        menuBar.clickCart();
        softAssert.assertTrue(cartpage.navigatedTo(), "failed to navigate to the cart page");
        for(String product: productsToAdd){
            softAssert.assertTrue(cartpage.isProductInCart(product), "unable to find product: "+product + " in the cart");
        }
        
        //should probably remove all product from the cart that where added
        //in this run, just to make sure that we get back to the starting state.
        try{
            cartpage.removeProductsFromCart(productsToAdd);
        }catch(Exception exc){
            softAssert.assertTrue(false, exc.getMessage());
        }           
        softAssert.assertAll();
    }
}
