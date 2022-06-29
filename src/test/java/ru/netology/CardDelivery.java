package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class CardDelivery {

    SelenideElement notification = $x("//div[@data-test-id='notification']");

    @BeforeEach
    public  void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void ShouldCardDelivery () {
        $x(".//span[@data-test-id='city']//input").setValue("Москва");
       // $x(".//span[@data-test-id=date]//input").setValue("02.07.2022");
        $x(".//span[@data-test-id='name']//input").setValue("Иванов Виктор");
        $x(".//span[@data-test-id='phone']//input").setValue("+79101234567");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//span[contains(text(),'Забронировать')]").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно!"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на "
                + $x(".//span[@data-test-id='date']//input").getValue()));
        notification.$x(".//button").click();
        notification.should(hidden);

    }
}
