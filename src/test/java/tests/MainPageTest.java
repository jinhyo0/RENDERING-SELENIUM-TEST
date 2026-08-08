package tests;

import base.BaseTest;
import config.TestConfig;
import org.junit.jupiter.api.Test;
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

    @Test
    void exhibitionMenuNavigation() {
        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 메뉴는 영상 종료(isEnded) 또는 포스터 노출(showPoster) 이후에만 렌더링됨
        wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("전시 소개"))
        ).click();

        assertEquals(TestConfig.BASE_URL + "/exhibition", driver.getCurrentUrl());
    }

    @Test
    void worksMenuNavigation() {
        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("작품 목록"))
        ).click();

        assertEquals(TestConfig.BASE_URL + "/works", driver.getCurrentUrl());
    }

    @Test
    void boothMenuNavigation() {
        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("부스 배치도"))
        ).click();

        assertEquals(TestConfig.BASE_URL + "/booth", driver.getCurrentUrl());
    }

    @Test
    void guestbookMenuNavigation() {
        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("방명록"))
        ).click();

        assertEquals(TestConfig.BASE_URL + "/guestbook", driver.getCurrentUrl());
    }

    @Test
    void partnersMenuNavigation() {
        driver.get(TestConfig.BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("협찬/제휴 소개"))
        ).click();

        assertEquals(TestConfig.BASE_URL + "/partners", driver.getCurrentUrl());
    }
}