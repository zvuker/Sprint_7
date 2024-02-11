package courier;

import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.apache.http.HttpStatus.*;

public class TestCasesCreateCourier {
    private CourierResponses courierResponses;
    private String login;
    private String password;
    private String firstName;

    public TestCasesCreateCourier() {
        this.courierResponses = new CourierResponses();
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @After
    public void cleanTestData() {
        if (!courierResponses.isCourierCreated()) return;

        Response loginResponse = courierResponses.loginCourier(login, password);
        Integer idCourier = courierResponses.getIdCourier(loginResponse);

        if (idCourier != null) {
            courierResponses.deleteCourier(idCourier);
        }

        courierResponses.setIsCreated(false);
    }

    @Test
    @DisplayName("создание курьера")
    @Description("позитивная проверка создания курьера")
    public void createCourierValidData() {
        Response response = courierResponses.createCourier(login, password, firstName);
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        courierResponses.checkStatusCode(response, SC_CREATED);
        courierResponses.checkMessage(response, "ok", true);
    }

    @Test
    @DisplayName("создание двух одинаковых курьеров")
    @Description("негативная проверка создания двух курьеров с аналогичными данными")
    public void createTwoCouriersWithTheSameData() {
        Response response = courierResponses.createCourier(login, password, firstName);
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        courierResponses.checkStatusCode(response, SC_CREATED);
        courierResponses.checkMessage(response, "ok", true);

        response = courierResponses.createCourier(login, password, firstName);

        courierResponses.checkStatusCode(response, SC_CONFLICT);
        courierResponses.checkMessage(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("создание курьера по пустым параметрам")
    @Description("негативная проверка создания курьера с незаполненными обязательными параметрами")
    public void createCourierNullData() {
        Response response = courierResponses.createCourier("", "", "");
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        courierResponses.checkStatusCode(response, SC_BAD_REQUEST);
        courierResponses.checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("создание курьера без логина")
    @Description("негативная проверка создания курьера без логина")
    public void createCourierWithoutLogin() {
        Response response = courierResponses.createCourier("", password, firstName);
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        courierResponses.checkStatusCode(response, SC_BAD_REQUEST);
        courierResponses.checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("создание курьера без пароля")
    @Description("негативная проверка создания курьера без пароля")
    public void createCourierWithoutPassword() {
        Response response = courierResponses.createCourier(login, "", firstName);
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        courierResponses.checkStatusCode(response, SC_BAD_REQUEST);
        courierResponses.checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("создание нового курьера без имени")
    @Description("позитивная проверка создания курьера без имени")
    public void createCourierWithoutFirstName() {
        Response response = courierResponses.createCourier(login, password, "");
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        courierResponses.checkStatusCode(response, SC_CREATED);
        courierResponses.checkMessage(response, "ok", true);
    }
}
