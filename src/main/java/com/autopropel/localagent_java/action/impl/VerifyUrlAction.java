package com.autopropel.localagent_java.action.impl;

import com.autopropel.localagent_java.action.ActionHandler;
import com.autopropel.localagent_java.dto.TestStep;
import com.autopropel.localagent_java.service.ExecutionService;
import org.openqa.selenium.WebDriver;

public class VerifyUrlAction implements ActionHandler {
    @Override
    public void execute(WebDriver driver, TestStep step, ExecutionService context) throws Exception {
        String expected = step.expectedValue != null ? step.expectedValue.trim() : (step.data != null ? step.data.trim() : "");
        String actual = driver.getCurrentUrl();
        step.actualValue = actual;
        if (actual.contains(expected)) {
            step.result_status = 1;
        } else {
            step.result_status = 0;
            throw new AssertionError("VerifyURL FAILED — Expected URL to contain: \"" + expected + "\" | Actual: \"" + actual + "\"");
        }
    }
}
