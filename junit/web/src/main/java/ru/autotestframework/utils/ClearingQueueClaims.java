package ru.autotestframework.utils;


import io.restassured.http.Header;
import io.restassured.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.autotestframework.cucumber.type.Pair;
import ru.autotestframework.steps.dbApiSteps.ActionsClaimSteps;

import java.util.List;
import java.util.stream.Collectors;

import static ru.autotestframework.utils.Constants.REQUESTS;

@Slf4j
@Component
public class ClearingQueueClaims extends ActionsClaimSteps {

    @Autowired
    private ClaimFileManager claimFileManager;
    private static final int BATCH_SIZE = 30;

    public void requestExpireAfterTestScenario() {
        if (!claimFileManager.readClaimsFromFile().isEmpty()) {
            cleanPastOrders();
        }
    }

    private void cleanPastOrders() {
        List<String> claimIds = claimFileManager.readClaimsFromFile();
        splittingAndSending(claimIds);
        claimFileManager.clearClaimsFile();
    }

    public void cleanAllPastOrders() {
        List<String> claimIds = getAllPastOrders();
        splittingAndSending(claimIds);
        claimFileManager.clearClaimsFile();
    }

    private void splittingAndSending(List<String> claimIds) {
        for (int i = 0; i < claimIds.size(); i += BATCH_SIZE) {
            List<String> batch = claimIds.subList(i, Math.min(i + BATCH_SIZE, claimIds.size()));
            String batchString = batch.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", "));
            sendRequestForTerminateOrders(batchString);
        }
    }

    private List<String> getAllPastOrders() {
        createNewConnection(REQUESTS, dbProperties.getDataSource(REQUESTS));
        executeQueryByConnection(REQUESTS, "SELECT claim_id FROM requests.rqs_request " +
                "WHERE status_id not in (21, 25) " +
                "AND claim_id similar to 'RKKL\\.[0-9]{0,}\\.0_tstAT" +
                "|CLL\\.[0-9]{0,}_tstAT" +
                "|RKKL\\.[0-9]{0,}_vrf_tstAT" +
                "|RKKL\\.[0-9]{0,}_vrf_vrfAT'");
        return getValuesFromResponseDb("claim_id");
    }

    private void sendRequestForTerminateOrders(String orders) {
        getSteps().setUrl(dbProperties.getLkaUrl());
        setAuthorizationToken("token");
        getSteps().setEndpoint("/requests/tech-support/processing-error/terminate");
        getSteps().setQueryParameters(List.of(
                Pair.of("terminalStatus", "RS_REQUEST_EXPIRE")));
        getSteps().setHeaders(List.of(new Header("Content-Type", "application/json"),
                new Header("Authorization", context.get("token"))));
        getSteps().setBody("[" + orders + "]");
        apiServiceSteps.sendRequest(Method.POST);
        getSteps().checkResponseStatus(200);
    }
}
