package com.autopropel.localagent_java.action.impl;

import com.autopropel.localagent_java.action.ActionHandler;
import com.autopropel.localagent_java.dto.TestStep;
import com.autopropel.localagent_java.service.ExecutionService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;

public class VerifyElementExistsAction implements ActionHandler {
    @Override
    public void execute(WebDriver driver, TestStep step, ExecutionService context) throws Exception {
        org.openqa.selenium.By locator = context.getLocator(step.locatorName, step.objectDetail, step.data);
        boolean exists = !driver.findElements(locator).isEmpty();
        step.actualValue = exists ? "exists" : "not found";
        if (exists) {
            step.result_status = 1;
        } else {
            step.result_status = 0;
            throw new AssertionError("VerifyElementExists FAILED — Element not found: " + (step.objectDetail != null ? step.objectDetail : step.data));
        }
    }
}
