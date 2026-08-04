package tests;

import base.BaseTest;
import config.TestConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest extends BaseTest {
    @Test
    void mainPage(){
        driver.get(TestConfig.BASE_URL);

        String currentUrl = driver.getCurrentUrl();

        assertEquals(
                TestConfig.BASE_URL + "/",
                driver.getCurrentUrl()
        );
    }
}