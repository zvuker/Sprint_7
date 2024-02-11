package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.OrderResponses;
import org.junit.Test;

public class TestClassesGetOrders extends OrderResponses {

    @Test
    @DisplayName("список заказов")
    @Description("проверка get-запроса на получение списка заказов")
    public void getOrderListWithoutParamsIsSuccess() {
        Response response = getOrdersList();

        checkStatusCode(response, 200);
        checkOrdersInResponse(response);
        }
    }