package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.apache.http.HttpStatus.*;

public class TestCasesDeleteCourier {
    private CourierResponses courierResponses;
    private String login;
    private String password;
    private String firstName;

    public TestCasesDeleteCourier() {
        this.courierResponses = new CourierResponses();
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
        // Создание нового курьера
        Response response = courierResponses.createCourier(login, password, firstName);
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        // Проверка успешного создания
        courierResponses.checkStatusCode(response, SC_CREATED);
        courierResponses.checkMessage(response, "ok", true);

        // Получение ID для удаления
        Integer idCourier = courierResponses.getIdCourier(courierResponses.loginCourier(login, password));
        response = courierResponses.deleteCourier(idCourier);

        // Проверка успешного удаления
        courierResponses.checkStatusCode(response, SC_OK);
        courierResponses.checkMessage(response, "ok", true);
    }

    @Test
    @DisplayName("удаление курьера с неверным id")
    @Description("негативная проверка удаления курьера с несуществующим id")
    public void deleteCourierWithWrongId() {
        // Создание нового курьера для получения актуального статуса системы
        Response response = courierResponses.createCourier(login, password, firstName);
        courierResponses.setIsCreated(courierResponses.isCourierCreated(response, SC_CREATED));

        // Проверка успешного создания
        courierResponses.checkStatusCode(response, SC_CREATED);
        courierResponses.checkMessage(response, "ok", true);

        // Попытка удалить курьера с невалидным ID
        Integer idCourier = 0; // Пример невалидного ID
        response = courierResponses.deleteCourier(idCourier);

        // Проверка ответа на невалидный ID
        courierResponses.checkStatusCode(response, SC_NOT_FOUND);
        courierResponses.checkMessage(response, "message", "Курьера с таким id нет.");
    }
}
