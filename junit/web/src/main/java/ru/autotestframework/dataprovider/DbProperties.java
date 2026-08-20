package ru.autotestframework.dataprovider;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;
import ru.autotestframework.sql_steps.components.SupportedDbms;

import javax.sql.DataSource;
import java.util.Map;

@Getter
@Component
public class DbProperties {

    @Value("${framework.variables.undwrDb.host}")
    private String undwrDbHost;

    @Value("${framework.variables.undwrDb.host.verification}")
    private String undwrDbHostVerification;

    @Value("${framework.variables.undwrDb.login}")
    private String undwrDbLogin;

    @Value("${framework.variables.undwrDb.password}")
    private String undwrDbPassword;

    @Value("${framework.variables.undwrBmpDb.login}")
    private String undwrBmpDbLogin;

    @Value("${framework.variables.undwrBmpDb.password}")
    private String undwrBmpDbPassword;

    @Value("${framework.variables.lka.api.url}")
    private String lkaUrl;

    @Value("${framework.variables.test.stand1.api.url}")
    private String testStand1Url;

    @Value("${framework.variables.test.stand2.api.url}")
    private String testStand2Url;

    @Value("${framework.variables.lka.user}")
    private String lkaUser;

    @Value("${framework.variables.lka.password}")
    private String lkaPassword;

    @Value("${framework.variables.undwrDb.authorizationClientSecret}")
    private String authorizationClientSecret;

    public DataSource getDataSource(String connection) {
        String url;
        String passwordDb;
        String loginDb;
        switch (connection.toUpperCase()) {
            case "REQUESTS":
                url = "jdbc:postgresql://" + undwrDbHost + "/requests";
                loginDb = undwrDbLogin;
                passwordDb = undwrDbPassword;
                break;
            case "VERIFICATION":
                url = "jdbc:postgresql://" + undwrDbHostVerification + "/verification?currentSchema=verification";
                loginDb = undwrBmpDbLogin;
                passwordDb = undwrBmpDbPassword;
                break;
            case "EMPLOYEE":
                url = "jdbc:postgresql://" + undwrDbHostVerification + "/employee";
                loginDb = undwrBmpDbLogin;
                passwordDb = undwrBmpDbPassword;
                break;
            default:
                throw new IllegalArgumentException("Unsupported database: " + connection);
        }
        return DataSourceBuilder.create()
                .driverClassName(SupportedDbms.findByName("POSTGRES").getDriverName())
                .url(url)
                .username(loginDb)
                .password(passwordDb)
                .build();
    }
}
