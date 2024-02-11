package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;



public class TestCasesDeleteCourier extends CourierResponses {
    private String login;
    private String password;
    private String firstName;

    public TestCasesDeleteCourier() {
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @Test
    @DisplayName("удаление курьера")
    @Description("позитивная проверка удаления курьера по валидным параметрам")
    public void deleteCourierValidData() {
        // новый курьер
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        // код ответа 201
        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        // удалить курьера
        Integer idCourier = getIdCourier(loginCourier(login, password));
        response = deleteCourier(idCourier);


        // код ответа 201
        checkStatusCode(response, 200);
        checkMessage(response, "ok", true);

    }

    @Test
    @DisplayName("удаление курьера с неверным id")
    @Description("негативная проверка удаления курьера с несуществующим id")
    public void deleteCourierWithWrongId() {
        // новый курьер
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        // код ответа 201
        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        // удалить курьера, невалидный id
        Integer idCourier = 0;

        response = deleteCourier(idCourier);

        // код ответа 404
        checkStatusCode(response, 404);
        checkMessage(response, "message", "Курьера с таким id нет.");
    }
}