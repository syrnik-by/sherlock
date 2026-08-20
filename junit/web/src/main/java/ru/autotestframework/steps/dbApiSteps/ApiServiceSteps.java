package ru.autotestframework.steps.dbApiSteps;


import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;
import ru.autotestframework.core.FileLoaderImpl;
import ru.autotestframework.core.context.Context;
import ru.autotestframework.cucumber.type.Pair;
import ru.autotestframework.dataprovider.DbProperties;
import ru.autotestframework.http_steps.HttpSteps;
import ru.autotestframework.http_steps.components.RequestContainer;
import ru.autotestframework.http_steps.components.restassured.RestAssuredConfiguration;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiServiceSteps {

    @Autowired
    protected DbProperties dbProperties;

    @Autowired
    protected FileLoaderImpl fileLoader;

    @Autowired
    protected Context context;

    @Autowired
    protected RestAssuredConfiguration raConfig;

    @Autowired
    protected RequestContainer requestContainer;

    protected HttpSteps getSteps() {
        return new HttpSteps(context, fileLoader, raConfig, requestContainer);
    }

    public void sendRequestWithAuthorization(Method method, String url, String endpoint, String body, String token, int code) throws ExpressionException {
        Response response;
        try {
            setUrl(url);
            getSteps().getSpecification().basePath(endpoint).header("Content-Type", "application/json").header("authorization", token).body(body).relaxedHTTPSValidation();
            sendRequest(method);
            response = getSteps().getResponse();
            Assert.assertEquals(String.format("Ответ сервиса %s, ожидаемый ответ %s", response.getStatusCode(), code), response.getStatusCode(), code);
        } catch (RuntimeException e) {
            throw new ExpressionException("Ошибка при отправке запоса Method " + method + ", Url " + url + ", endpoint " + endpoint, e.getMessage());
        }
    }

    @Step
    @Title("Отправляем PUT запрос на 'requests/manual-distribution-request/distributeRequests', для назначения ответственного по заявке")
    public void putDistributeRequests(String token, String body, int code) {
        sendRequestWithAuthorization(Method.PUT, dbProperties.getLkaUrl(), "/requests/manual-distribution-request/distributeRequests", body, token, code);
    }

    /**
     * Отправлеяет запрос для эмуляции создания предыдущей заявки
     *
     * @param token       авторизационный токен
     * @param clientId    id клиента, для которого создается предыдущая заявка
     * @param claimNumber номер шаблона заявки
     * @param code        ожидаемый код ответа
     */
    @Step
    @Title("Отправляем POST запрос на '/kflScans/stub/vrf/uploadClaimList', для эмуляции создания предыдущей заявки")
    public void postPreviousClaim(String token, String clientId, String claimNumber, int code) {
        sendRequestWithAuthorization(Method.POST, dbProperties.getTestStand1Url(), "/kflScans/stub/vrf/uploadClaimList", getBody(clientId, claimNumber), token, code);
        sendRequestWithAuthorization(Method.POST, dbProperties.getTestStand2Url(), "/kflScans/stub/vrf/uploadClaimList", getBody(clientId, claimNumber), token, code);
    }

    @Step
    @Title("Создаем тело запроса для назначения ответственного по заявке с номером {requestId}")
    public String createBodyDistributeRequests(String requestIds, String userLogin) {
        return this.fileLoader.readFileAsString("data/json/" + "distributeRequests.json")
                .replace("testat1", userLogin)
                .replace("11111", requestIds);
    }

    /**
     * Отправлеяет запрос для эмуляции создания предыдущей заявки
     *
     * @param clientId    id клиента, для которого создается предыдущая заявка
     * @param claimNumber номер шаблона заявки
     */
    @Step
    @Title("Создаем тело запроса для эмуляции создания предыдущей заявки по шаблону с номером {claimNumber} для клиента {clientId}")
    public String getBody(String clientId, String claimNumber) {
        return this.fileLoader.readFileAsString("data/json/" + "previous_claim_" + claimNumber + ".json")
                .replace("${{clientId}}", clientId);
    }

    @Step
    @Title("Отправка текущего запроса выбранным методом")
    public void sendRequest(Method method) {
        try {
            getSteps().sendRequest(method); // Делегируем вызов методу sendRequest
        } catch (Exception e) {
            System.err.println("Произошла ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    @Step
    @Title("Установить URL = {url}")
    public void setUrl(String url) {
        getSteps().setUrl(url);
    }

    @Step
    @Title("Установить эндпоинт = {endpoint}")
    public void setEndpoint(String endpoint) {
        getSteps().setEndpoint(endpoint);
    }

    @Step
    @Title("Установить тело запроса body = {body}")
    public void setBody(String body) {
        getSteps().setBody(body);
    }

    @Step
    @Title("Установить заголовки запроса headers = {headers}")
    public void setHeaders(List<Header> headers) {
        getSteps().setHeaders(headers);
    }

    @Step
    @Title("Установить query parameters = {params}")
    public void setQueryParameters(List<Pair> params) {
        getSteps().setQueryParameters(params);
    }

    @Step
    @Title("Проверить, что код ответа на запрос = {code}")
    public void checkResponseStatus(int code) {
        getSteps().checkResponseStatus(code);
    }
}
