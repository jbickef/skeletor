/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.techassessment.ui.pages.sauce;

import com.automation.techassessment.ui.lib.UIThreadManager;
import com.slickqa.webdriver.PageElement;
import com.slickqa.webdriver.WebDriverWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Jeff Bickmore
 */
public class CartItem {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private String inventoryItemName;
    private static final String cartItemBase = "//div[@class='cart_list' and .//div[@class='inventory_item_name' and text() ='";
    private static final String cartItemEnd = "']]";
    private static final String cartItemTitle = "//div[@class='inventory_item_name']";
    private static final String removeButton = ".//button[text()= 'REMOVE']";
    private WebElement webElement;
    private WebDriverWrapper webDriverWrapper;
    private PageElement pageElement;
    private int waitTime = 10;
    
    public CartItem(By finder) {
        webDriverWrapper = UIThreadManager.getBrowser();
        this.webElement = webDriverWrapper.getDriver().findElement(finder);
        pageElement = new PageElement("InventoryItem", finder);
    }
    
    /**
     * attempts to create an xpath that relies on class attributes and the text of 
     * the product name. 
     * @param cartItemName
     * @return 
     */
    public static String createXpathFor(String cartItemName){
        return cartItemBase + cartItemName + cartItemEnd;
    }   
    
    /**
     * Removes the cartItem from the cart, by clicking the remove button
     * @throws Exception 
     */
    public void removeFromCart()throws Exception{
        this.webDriverWrapper.click(new PageElement(getRemoveFromCartButton()));
    }
    
    /**
     * @return a webelement for the removeFromCartButton for the inventoryItem
     */
    private WebElement getRemoveFromCartButton(){
        return this.webElement.findElement(By.xpath(removeButton));
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
