package tests;

import base.BaseTest;
import config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest extends BaseTest {
    @Test
    void mainPage() {
        driver.get(TestConfig.BASE_URL);

        String currentUrl = driver.getCurrentUrl();

        assertEquals(
                TestConfig.BASE_URL + "/",
                currentUrl
        );
    }

    @ParameterizedTest(name = "{0} 클릭 시 {1} 경로로 이동한다.")
    @CsvSource({
            "전시 소개, /exhibition",
            "작품 목록, /works",
            "부스 배치도, /booth",
            "방명록, /guestbook",
            "협찬/제휴 소개, /partners"
    })
    void menuNavigation(String menuText, String expectedPath) {
        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText(menuText))
        ).click();

        assertEquals(TestConfig.BASE_URL + expectedPath, driver.getCurrentUrl());
    }
}