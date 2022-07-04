package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class CardDelivery {

    public String nextDate(int days) {
        LocalDate parsedDate = LocalDate.now();
        LocalDate addedDate = parsedDate.plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return addedDate.format(formatter);
    }

    String planningDate = nextDate(7);
    SelenideElement notification = $x("//div[@data-test-id='notification']");


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void ShouldCardDelivery() {
        $x(".//span[@data-test-id='city']//input").setValue("Москва");
        $x(".//span[@data-test-id=date]//input").doubleClick().sendKeys(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Иванов Виктор");
        $x(".//span[@data-test-id='phone']//input").setValue("+79101234567");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//span[contains(text(),'Забронировать')]").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно!"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на " + planningDate));
        notification.$x(".//button").click();
        notification.should(hidden);

    }
}
