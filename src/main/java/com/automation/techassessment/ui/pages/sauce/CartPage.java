/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.ui.pages.sauce;

import com.automation.techassessment.ui.lib.UIThreadManager;
import com.slickqa.webdriver.PageElement;
import com.slickqa.webdriver.WebDriverWrapper;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

/**
 *
 * @author Jeff Bickmore
 */
public class CartPage { 
    private final Logger logger = LogManager.getLogger(this.getClass());
    private PageElement checkOutButton = new PageElement(By.xpath(".//*[@class = 'btn_secondary' and text() = 'Continue Shopping']"));
    private PageElement continueShopingButton = new PageElement(By.xpath(".//*[@class = 'btn_action checkout_button' and text() = 'CHECKOUT']"));
    
    /**
     * Additional item could be added here, to verify that the correct page shows up. 
     * @return 
     */
    public boolean navigatedTo(){
        WebDriverWrapper webdriverWrapper = UIThreadManager.getBrowser();
        return webdriverWrapper.exists(checkOutButton) && webdriverWrapper.exists(continueShopingButton);
    }
    /**
     * attempts to remove a product from the cart
     * @param productName
     * @throws Exception if the cartItem.removeFromCart throws an exception
     */
    public void removeProductFromCart(String productName)throws Exception{
        CartItem product = new CartItem(By.xpath(CartItem.createXpathFor(productName)));
        if(isProductInCart(product)){
            product.removeFromCart();
        }
    }
    
    /**
     * checks to see if a product is in the cart
     * @param productName
     * @return true if there is a product with the given name in the cart
     */
    public boolean isProductInCart(String productName){
        CartItem product = new CartItem(By.xpath(CartItem.createXpathFor(productName)));
        return isProductInCart(product);
    }
    
    /**
     * checks to see if a product is in the cart.
     * If we needed to we could build out a fuller test where we check to make 
     * sure that the description and the price are accurate as well. After considering
     * that it was something that i wasn't sure that we would want to do each time
     * that we might use this code in the future. If it is, then we could add that later on
     * @param product
     * @return true if the product is in the cart
     */
    public boolean isProductInCart(CartItem product){
        return UIThreadManager.getBrowser().exists(product.getPageElement());
    }
    
    /**
     * Given a list of productNames, attempts to remove them all from the cart
     * @param productNames
     * @throws Exception 
     */
    public void removeProductsFromCart(List<String>productNames)throws Exception{
        String errs = "";
        for(String productName: productNames){
            try{
                removeProductFromCart(productName);
            }
            catch(Exception e){
                errs+= e.getMessage();
            }
        }if(!errs.equals("")){
            logger.log(Level.ERROR, "errors while attempting to remove products:\n"+errs);
            throw new Exception(errs);
        }
    }
}
