package com.autopropel.localagent_java.action.impl;

import com.autopropel.localagent_java.action.ActionHandler;
import com.autopropel.localagent_java.dto.TestStep;
import com.autopropel.localagent_java.service.ExecutionService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * VerifyText — finds element, extracts text, compares with expectedValue.
 * Uses step.data as fallback if expectedValue is null.
 */
public class VerifyTextAction implements ActionHandler {
    @Override
    public void execute(WebDriver driver, TestStep step, ExecutionService context) throws Exception {
        String expected = step.expectedValue != null ? step.expectedValue.trim() : (step.data != null ? step.data.trim() : "");
        org.openqa.selenium.By locator = context.getLocator(step.locatorName, step.objectDetail, step.data);
        WebElement element = driver.findElement(locator);
        String actual = element.getText().trim();
        step.actualValue = actual;
        if (actual.equals(expected)) {
            step.result_status = 1;
        } else {
            step.result_status = 0;
            throw new AssertionError("VerifyText FAILED — Expected: \"" + expected + "\" | Actual: \"" + actual + "\"");
        }
    }
}
