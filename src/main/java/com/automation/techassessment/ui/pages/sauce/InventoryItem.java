/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.ui.pages.sauce;

import com.automation.techassessment.ui.lib.UIThreadManager;
import com.slickqa.webdriver.PageElement;
import com.slickqa.webdriver.WebDriverWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *  This class is for the inventory items that show up on the dashboard page.
 * Not designed to work with the cart page. Those are the cartItems
 * @author Jeff Bickmore
 */
public class InventoryItem {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private String inventoryItemName;
    private static final String inventoryItemBase = "//div[@class='inventory_item' and .//div[@class='inventory_item_name' and text() ='";
    private static final String inventoryItemEnd = "']]";
    private static final String inventoryItemTitle = "//div[@class='inventory_item_name']";
    private static final String addToCartButton = ".//button[text()= 'ADD TO CART']";
    private static final String removeFromCartButton = ".//button[text()= 'REMOVE']";
    private WebElement webElement;
    private WebDriverWrapper webDriverWrapper;
    private PageElement pageElement;
    private int waitTime = 10;
    
    public InventoryItem(By finder) {
        webDriverWrapper = UIThreadManager.getBrowser();
        this.webElement = webDriverWrapper.getDriver().findElement(finder);
        pageElement = new PageElement("InventoryItem", finder);
        this.inventoryItemName = this.webElement.findElement(By.xpath(inventoryItemTitle)).getText();
    }
    
    /**
     * attempts to create an xpath that relies on class attributes and the text of 
     * the product name. 
     * @param inventoryItemName
     * @return 
     */
    public static String createXpathFor(String inventoryItemName){
        return inventoryItemBase + inventoryItemName + inventoryItemEnd;
    }   
    
    /**
     * clicks the add to cart button for the inventory item.
     * If the InventoryItem is in the cart already throws an exception 
     * @throws Exception 
     */
    public void addToCart()throws Exception{
        if(isInCart()){
            logger.log(Level.ERROR, "unable to add "+inventoryItemName + " to cart because it is already in the cart");
            throw new Exception(inventoryItemName + " is already in the cart");
        }
        this.webDriverWrapper.click(new PageElement(getAddToCartButton()));
    }
    
    /**
     * Removes the InventoryItem from the cart, by clicking the remove button
     * Throws an exception if the item is not in the cart
     * @throws Exception 
     */
    public void removeFromCart()throws Exception{
        if(!isInCart()){
            logger.log(Level.ERROR, "unable to remove "+inventoryItemName + " from cart because it is not in the cart");
            throw new Exception(inventoryItemName + " is already not in the cart.");
        }
        this.webDriverWrapper.click(new PageElement(getRemoveFromCartButton()));
    }
    
    /**
     * Perhaps not the best way to tell if an item is in the cart or not. Bases the decision 
     * off of if the addCartToButton for the given inventory item exists
     * @return true if the add to cart button is present
     */
    public boolean isInCart(){
        return (!this.webDriverWrapper.exists(new PageElement(getAddToCartButton())));
    }
    
    /**
     * @return a webelement for the addToCartButton for the inventoryItem
     */
    private WebElement getAddToCartButton(){
        return this.webElement.findElement(By.xpath(addToCartButton));
    }
    
    /**
     * @return a webelement for the removeFromCartButton for the inventoryItem
     */
    private WebElement getRemoveFromCartButton(){
        return this.webElement.findElement(By.xpath(removeFromCartButton));
    }
    
    /**
     * @return a pageElement that is associated with the given product
     */
    public PageElement getPageElement(){
        return this.pageElement;
    }
    
    /**
     * attempts to wait for the invintoryItem to be visible
     */
    public void waitForDisplay(){
        this.webDriverWrapper.waitFor(this.pageElement, this.waitTime);
    }
}
