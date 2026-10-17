package tests;

import base.BaseTest;
import config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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


    @Test
    void autoplayFailureFallsBackToPoster() {
        ChromeDriver chromeDriver = (ChromeDriver) driver;

        // 자동재생 실패 상황 대처: play() 호출 시 항상 reject되도록
        // 실제 iOS 저전력 모드 재현은 불가능하여 동일한 실패 결과를 유발하도록 대체함
        String script =
                "HTMLMediaElement.prototype.play = function() {" +
                        " return Promise.reject(new DOMException('NotAllowedError'));" +
                        "};";

        chromeDriver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", Map.of("source", script));

        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        var poster = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='poster']"))
        );

        assertTrue(poster.isDisplayed());
    }

    @Test
    void autoplaySucceedsUnderNormalCondition() {
        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // video 요소가 DOM에 존재하는지 확인
        WebElement video = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("video"))
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // paused가 false가 될 때까지 대기 (= 재생 중 상태)
        wait.until(
                webDriver -> Boolean.FALSE.equals(js.executeScript("return arguments[0].paused;", video
                ))
        );

        Boolean isPaused = (Boolean) js.executeScript("return arguments[0].paused;", video);
        assertFalse(isPaused, "영상이 정상적으로 재생 중이어야 합니다.");
    }
}