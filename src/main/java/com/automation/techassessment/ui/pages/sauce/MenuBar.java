package com.automation.techassessment.ui.pages.sauce;

import com.automation.techassessment.ui.lib.UIThreadManager;
import com.slickqa.webdriver.FindBy;
import com.slickqa.webdriver.PageElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MenuBar {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private PageElement buttonMenuBar = new PageElement("Menu Bar Button", FindBy.className("bm-burger-button"));
    private PageElement shopingCart = new PageElement("shoping cart", FindBy.xpath("//*[@class='shopping_cart_link fa-layers fa-fw']"));
    public boolean menuBarButtonExists() {
        return UIThreadManager.getBrowser().exists(buttonMenuBar);
    }
    
    public void clickCart(){
        UIThreadManager.getBrowser().waitFor(shopingCart);
        UIThreadManager.getBrowser().click(shopingCart);
    }
}
