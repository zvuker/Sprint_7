package order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.OrderResponses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)

public class TestCasesCreateOrder extends OrderResponses {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private final List<String> scooterColor;
    private Integer trackId;

    public TestCasesCreateOrder(List<String> scooterColor) {

        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] initParamsForTest() {
        return new Object[][] {
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Before
    @Step("тестовые данные")
    public void prepareTestData() {
        this.firstName = "Ivan";
        this.lastName = "Petrov";
        this.address = "Санкт-Петербург, пр. Солидарности, д. 29";
        this.phone = "+7 (987)6543211";
        this.rentTime = "2";
        this.deliveryDate = "2024-02-11";
        this.comment = "go ride";
    }

    @After
    public void cleanTestData() {
        if (trackId == null) return;

        deleteOrder(trackId);
    }

    @Test
    @DisplayName("создание заказа")
    @Description("проверка создания заказа по вариантам цвета: без выбора цвета, черный, серый, оба цвета")
    public void createOrderValidData() {
        Allure.parameter("Цвет самоката", scooterColor);

        Response response = createOrder(firstName, lastName, address, phone, rentTime, deliveryDate, comment, scooterColor);
        checkStatusCode(response, 201);
        checkResponseParamNotNull(response, "track");

        this.trackId = getTrack(response);
    }
}
