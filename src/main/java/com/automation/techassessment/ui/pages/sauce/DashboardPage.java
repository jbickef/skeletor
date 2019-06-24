package com.automation.techassessment.ui.pages.sauce;

import com.automation.techassessment.ui.lib.UIThreadManager;
import com.slickqa.webdriver.FindBy;
import com.slickqa.webdriver.PageElement;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DashboardPage {
    private final Logger logger = LogManager.getLogger(this.getClass());
    
    private PageElement productContainer = new PageElement("Product container", FindBy.id("inventory_container"));

    public boolean productContainerIsVisible() {
        return UIThreadManager.getBrowser().exists(productContainer);
    }
    
    /**
     * Given the name of a product, attempts to add it to the cart. If it fails
     * an exception is thrown. Relies on the InventoryItem.addToCart function
     * @param productName
     * @throws Exception 
     */
    public void addProductToCart(String productName)throws Exception{
        InventoryItem product = new InventoryItem(FindBy.xpath(InventoryItem.createXpathFor(productName)));
        product.waitForDisplay();
        product.addToCart();
    }
    
    /**
     * Given a list of products attempts to add them to the cart. Relies on the 
     * DashboardPage.addProductToCart function
     * @param productNames
     * @throws Exception 
     */
    public void addProductsToCart(List<String>productNames) throws Exception{
        String errors = "";
        for(String product: productNames){
            try{
                addProductToCart(product);
            }catch(Exception e){
                errors += e.getMessage()+"\n";
            }
        }if(!errors.equals("")){
            logger.log(Level.ERROR, errors);
            throw new Exception(errors);
        }
    }
}

