package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.apache.http.HttpStatus.*;

public class TestCasesLoginCourier {
    private CourierResponses courierResponses;
    private String login;
    private String password;
    private String firstName;

    public TestCasesLoginCourier() {
        this.courierResponses = new CourierResponses();
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();

        // Создание курьера перед каждым тестом
        Response response = courierResponses.createCourier(login, password, firstName);
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));
    }

    @Test
    @DisplayName("авторизация курьера")
    @Description("авторизация по валидным параметрам")
    public void loginCourierValidData() {
        Response response = courierResponses.loginCourier(login, password);

        // Проверка успешного логина
        courierResponses.checkStatusCode(response, SC_OK);
        courierResponses.checkCourierIDNotNull(response);
    }

    @Test
    @DisplayName("авторизация курьера с незаполненными параметрами")
    @Description("негативная проверка авторизации курьера с незаполненными параметрами")
    public void loginCourierNullData() {
        Response response = courierResponses.loginCourier("", "");

        // Проверка ответа на запрос с пустыми данными
        courierResponses.checkStatusCode(response, SC_BAD_REQUEST);
        courierResponses.checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("авторизация курьера без логина")
    @Description("негативная проверка авторизации курьера без логина")
    public void loginCourierWithoutLogin() {
        Response response = courierResponses.loginCourier("", password);

        // Проверка ответа на запрос без логина
        courierResponses.checkStatusCode(response, SC_BAD_REQUEST);
        courierResponses.checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("авторизация курьера без пароля")
    @Description("негативная проверка авторизации курьера без пароля")
    public void loginCourierWithoutPassword() {
        Response response = courierResponses.loginCourier(login, "");

        // Проверка ответа на запрос без пароля
        courierResponses.checkStatusCode(response, SC_BAD_REQUEST);
        courierResponses.checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("авторизация курьера с несуществующим логином")
    @Description("негативная проверка авторизации курьера с несуществующим логином")
    public void loginCourierWithIncorrectLogin() {
        Response response = courierResponses.loginCourier(login + "incorrect", password);

        // Проверка ответа на запрос с несуществующим логином
        courierResponses.checkStatusCode(response, SC_NOT_FOUND);
        courierResponses.checkMessage(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("авторизация курьера с несуществующим паролем")
    @Description("негативная проверка авторизации курьера с неверным паролем")
    public void loginCourierWithIncorrectPassword() {
        Response response = courierResponses.loginCourier(login, password + "incorrect");

        // Проверка ответа на запрос с неверным паролем
        courierResponses.checkStatusCode(response, SC_NOT_FOUND);
        courierResponses.checkMessage(response, "message", "Учетная запись не найдена");
    }
}
