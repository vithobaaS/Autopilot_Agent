package com.autopropel.localagent_java.action.impl;

import com.autopropel.localagent_java.action.ActionHandler;
import com.autopropel.localagent_java.dto.TestStep;
import com.autopropel.localagent_java.service.ExecutionService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VerifyElementHiddenAction implements ActionHandler {
    @Override
    public void execute(WebDriver driver, TestStep step, ExecutionService context) throws Exception {
        org.openqa.selenium.By locator = context.getLocator(step.locatorName, step.objectDetail, step.data);
        java.util.List<WebElement> elements = driver.findElements(locator);
        boolean hidden = elements.isEmpty() || !elements.get(0).isDisplayed();
        step.actualValue = hidden ? "hidden" : "visible";
        if (hidden) {
            step.result_status = 1;
        } else {
            step.result_status = 0;
            throw new AssertionError("VerifyElementHidden FAILED — Element is visible but expected to be hidden: " + (step.objectDetail != null ? step.objectDetail : step.data));
        }
    }
}
