package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;

import java.io.ByteArrayInputStream;

public class AllureUtils {

    public static void takeScreenshot(WebDriver driver) {
        if (driver == null) {
            return;
        }
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                    "Screenshot on failure",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );
        } catch (WebDriverException ignored) {
            // Ignore capture failures so the original test error remains visible.
        }
    }
}
