package com.autopropel.localagent_java.action.impl;

import com.autopropel.localagent_java.action.ActionHandler;
import com.autopropel.localagent_java.dto.TestStep;
import com.autopropel.localagent_java.service.ExecutionService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * VerifyAttribute — verifies an element's attribute value.
 * Usage: locatorValue = "#submit", stepDesc = "data-testid", expectedValue = "submit-btn"
 * The attribute name is read from step.stepDesc (re-purposing description field).
 */
public class VerifyAttributeAction implements ActionHandler {
    @Override
    public void execute(WebDriver driver, TestStep step, ExecutionService context) throws Exception {
        String expected = step.expectedValue != null ? step.expectedValue.trim() : "";
        String attrName  = step.stepDesc != null && !step.stepDesc.isBlank() ? step.stepDesc.trim() : "value";
        org.openqa.selenium.By locator = context.getLocator(step.locatorName, step.objectDetail, step.data);
        WebElement element = driver.findElement(locator);
        String actual = element.getAttribute(attrName);
        step.actualValue = actual;
        if (expected.equals(actual)) {
            step.result_status = 1;
        } else {
            step.result_status = 0;
            throw new AssertionError("VerifyAttribute [" + attrName + "] FAILED — Expected: \"" + expected + "\" | Actual: \"" + actual + "\"");
        }
    }
}
