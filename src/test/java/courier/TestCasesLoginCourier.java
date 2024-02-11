package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class TestCasesLoginCourier extends CourierResponses {
    private String login;
    private String password;
    private String firstName;

    public TestCasesLoginCourier() {
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();

        createCourier(login, password, firstName);
    }

    @After
    public void cleanTestData() {
        if (!isCourierCreated()) return;

        Integer idCourier = getIdCourier(loginCourier(login, password));

        if (idCourier != null) {
            deleteCourier(idCourier);
        }

        setIsCreated(false);
    }

        @Test
        @DisplayName("авторизация курьера")
        @Description("авторизация по валидным параметрам")
        public void loginCourierValidData() {
            Response response = loginCourier(login,password);

            checkStatusCode(response, 200);
            checkCourierIDNotNull(response);
        }

    @Test
    @DisplayName("авторизация курьера с незаполненными параметрами")
    @Description("негативная проверка  авторизации курьера с незаполненными параметрами")
    public void loginCourierNullData() {
        Response response = loginCourier("", "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("авторизация курьера без логина")
    @Description("негативная проверка  авторизации курьера с незаполненным логином")
    public void loginCourierWithoutLogin() {
        Response response = loginCourier("", password);

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("авторизация курьера без пароля")
    @Description("негативная проверка авторизации курьера с незаполненным паролем")
    public void loginCourierWithoutPasword() {
        Response response = loginCourier(login, "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("авторизация курьера с несуществующим логином")
    @Description("негативная проверка  авторизации курьера с неверным логином")
    public void loginCourierWithIncorrectLogin() {
        Response response = loginCourier(login + "qwerty", password);

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }
    @Test
    @DisplayName("авторизация курьера с несуществующим паролем")
    @Description("негативная проверка авторизации курьера с неверны паролем")
    public void loginCourierWithIncorrectPassword() {
        Response response = loginCourier(login, password + "qwerty");

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }
}
