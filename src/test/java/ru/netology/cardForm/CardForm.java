package ru.netology.cardForm;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CardForm {
    SelenideElement formCard = $x("//form[contains(@class, form)]");
    SelenideElement notice = $x("//div[@data-test-id='notification']");

    @BeforeAll
    public static void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    public void SuccessfulFormSubmission() {
        Calendar calend = new GregorianCalendar();
        calend.add(Calendar.DAY_OF_YEAR, 7);
        SimpleDateFormat formatCalend = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatCalend.format(calend.getTime());
        System.out.println(date);
       formCard.$x(".//span[@data-test-id='city']//input").val("Москва");
       formCard.$x(".//span[@data-test-id='date']//input").sendKeys(Keys.LEFT_CONTROL + "A");
       formCard.$x(".//span[@data-test-id='date']//input").sendKeys(Keys.BACK_SPACE);
       formCard.$x(".//span[@data-test-id='date']//input").setValue(date);
       formCard.$x(".//span[@data-test-id='name']//input").val("Краснова Кристина");
       formCard.$x(".//span[@data-test-id='phone']//input").val("+79998911111");
       formCard.$x(".//label[@data-test-id='agreement']").click();
       formCard.$x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();
       notice.should(Condition.visible, Duration.ofSeconds(15));
       notice.$x(".//div[@class='notification__title']").should(Condition.text("Успешно"));
       notice.$x(".//div[@class='notification__content']").should(Condition.text("Встреча успешно забронирована на " + formCard.$x(".//span[@data-test-id='date']//input").getValue()));

    }
}
