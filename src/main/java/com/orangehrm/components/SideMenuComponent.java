package com.orangehrm.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class SideMenuComponent {

    private static final Logger LOGGER = LogManager.getLogger(SideMenuComponent.class);
    private final Page page;

    public SideMenuComponent(Page page) {
        this.page = page;
    }

    @Step("Navigate via side menu to '{menuItemName}'")
    public void navigateTo(String menuItemName) {
        LOGGER.info("Clicking side menu: {}", menuItemName);
        page.getByRole(com.microsoft.playwright.options.AriaRole.LINK, new Page.GetByRoleOptions().setName(menuItemName)).first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public boolean isMenuItemVisible(String menuItemName) {
        return page.getByRole(com.microsoft.playwright.options.AriaRole.LINK, new Page.GetByRoleOptions().setName(menuItemName)).first().isVisible();
    }

    public List<String> getAllMenuItemNames() {
        Locator navTexts = page.locator(".oxd-nav-text");
        int count = navTexts.count();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            names.add(navTexts.nth(i).innerText());
        }
        return names;
    }
}
