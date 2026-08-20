package ru.autotestframework.steps.actions;

import com.codeborne.selenide.Condition;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static ru.autotestframework.pages.BasePage.sleep;
import static ru.autotestframework.utils.Constants.SPINNER;
import static ru.autotestframework.utils.Constants.TIMEOUT;

interface IElementWait<T extends BaseActions<T>> {

    T getSelf();

    default T waitBusyCondition() {
        sleep(1);
        SPINNER.should(Condition.disappear, Duration.ofSeconds(TIMEOUT));
        return getSelf();
    }

    default T waittext(int seconds, String text) {
        $x("//*[contains(text(), '" + text + "')]").shouldBe(Condition.visible, Duration.ofSeconds(seconds));
        return getSelf();
    }

    default T waitElementDisappear(String title) {
        getSelf().getElementByTitle(title).shouldBe(Condition.disappear, true);
        return getSelf();
    }

    default T waitElementVisible(String title) {
        getSelf().getElementByTitle(title).shouldBe(Condition.visible, true);
        return getSelf();
    }
}
