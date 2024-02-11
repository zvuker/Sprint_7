package courier;

import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class TestCasesCreateCourier extends CourierResponses {
    private String login;
    private String password;
    private String firstName;

    public TestCasesCreateCourier() {
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
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
    @DisplayName("создание курьера")
    @Description("позитивная проверка создания курьера")
    public void createCourierValidData() {
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }
    @Test
    @DisplayName("создание двух одинаковых курьеров")
    @Description("негативная проверка создания двух курьеров с аналогичными данными")

    public void createTwoCouriersWithTheSameData() {

        // новый курьер
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        // код ответа
        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        // второй курьера
        response = createCourier(login, password, firstName);

        // код ответа 409
        checkStatusCode(response, 409);
        checkMessage(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("создание курьера по пустым параметрам")
    @Description("негативная проверка создания курьера с незаполненными обязательными параметрами")
    public void createCourierNullData() {
        // новый курьер с пустыми входными данными
        Response response = createCourier("", "", "");
        setIsCreated(isCourierCreated(response, 201));

        // код ответа 400
        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("создание курьера без логина")
    @Description("негативная проверка создания курьера без логина")
    public void createCourierWithoutLogin() {
        // новый курьер без логина
        Response response = createCourier("", password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        // код ответа 400
        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("создание курьера без пароля")
    @Description("негативная проверка  создания курьера без пароля")
    public void createCourierWithoutPassword() {
        // новый курьер без пароля
        Response response = createCourier(login, "", firstName);
        setIsCreated(isCourierCreated(response, 201));

        // код ответа 400
        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("cоздание нового курьера без имени")
    @Description("позитивная проверка создания курьера без имени")
    public void createCourierWithoutFirstName() {
        // новый курьер без имени (необязательное поле")
        Response response = createCourier(login, password, "");
        setIsCreated(isCourierCreated(response, 201));

        // код ответа 201
        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }
}