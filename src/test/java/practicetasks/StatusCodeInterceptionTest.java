package practicetasks;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatusCodeInterceptionTest {
  Playwright playwright;
  Browser browser;
  BrowserContext context;
  Page page;

  private static final String EXPECTED_MOCKED_MESSAGE = "Mocked Success Response";

  @BeforeEach
  void setUp() {
    playwright = Playwright.create();
    browser = playwright.chromium()
        .launch(new BrowserType.LaunchOptions().setHeadless(false));
    context = browser.newContext();
    page = context.newPage();

    context.route("**/status_codes/404", route -> {
      route.fulfill(new Route.FulfillOptions()
          .setStatus(200)
          .setHeaders(Collections.singletonMap("Content-Type", "text/html"))
          .setBody("<h3>" + EXPECTED_MOCKED_MESSAGE + "</h3>")
      );
    });
  }

  @Test
  void testMockedStatusCode() {
    page.navigate("https://the-internet.herokuapp.com/status_codes");

    page.locator("//*[@href='status_codes/404']").click();

    String actualMockedText = page.locator("h3").textContent();
    Assertions.assertEquals(EXPECTED_MOCKED_MESSAGE, actualMockedText);
  }

  @AfterEach
  void tearDown() {
    browser.close();
    playwright.close();
  }
}
