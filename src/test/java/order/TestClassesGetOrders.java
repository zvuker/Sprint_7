package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.OrderResponses;
import org.junit.Before;
import org.junit.Test;

public class TestClassesGetOrders {
    private OrderResponses orderResponses; // Объявление переменной для экземпляра OrderResponses

    @Before
    public void setup() {
        this.orderResponses = new OrderResponses(); // Инициализация экземпляра в методе setup
    }

    @Test
    @DisplayName("список заказов")
    @Description("проверка get-запроса на получение списка заказов")
    public void getOrderListWithoutParamsIsSuccess() {
        // Вызов метода через экземпляр orderResponses
        Response response = orderResponses.getOrdersList();

        // Применение методов экземпляра для проверки ответа
        orderResponses.checkStatusCode(response, 200);
        orderResponses.checkOrdersInResponse(response);
    }
}
