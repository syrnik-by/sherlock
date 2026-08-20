package ru.autotestframework.steps.dbApiSteps;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.autotestframework.configuration.FrameworkProperties;
import ru.autotestframework.core.FileLoaderImpl;
import ru.autotestframework.core.PlaceholderResolver;
import ru.autotestframework.core.PlaceholderResolverImpl;
import ru.autotestframework.core.context.Context;
import ru.autotestframework.dataprovider.DbProperties;
import ru.autotestframework.sql_steps.SqlSteps;
import ru.autotestframework.sql_steps.components.ConnectionPoolContainer;
import ru.autotestframework.sql_steps.components.DBContextContainer;
import ru.autotestframework.sql_steps.components.SqlMethod;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class DbSteps {

    @Autowired
    protected Context context;

    @Autowired
    protected DbProperties dbProperties;

    @Autowired
    protected PlaceholderResolver placeholderResolver;

    protected SqlSteps sqlSteps = new SqlSteps(context,
            new PlaceholderResolverImpl(new StringSubstitutor()),
            new FileLoaderImpl(placeholderResolver, new FrameworkProperties()),
            new DBContextContainer());


    @Step
    @Title("Создание подключения к базе данных {connectName}")
    public void createNewConnection(String connectName, DataSource dataSource) {
        if (!Objects.nonNull(sqlSteps.getConnectionPoolContainer())) {
            sqlSteps.setConnectionPoolContainer(new ConnectionPoolContainer());
        }
        sqlSteps.createConnection(connectName, dataSource);
    }

    @Step
    @Title("Отправка в БД запроса {sqlQuery}")
    public void executeQueryByConnection(String connector, String sqlQuery) {
        sqlSteps.executeQueryByConnection(connector, SqlMethod.SELECT, sqlQuery);
    }

    @Step
    @Title("Выполнение запроса в БД {dbName} запроса {sqlQuery}")
    public void executeQuery(String dbName, String sqlQuery) {
        createNewConnection(dbName, dbProperties.getDataSource(dbName));
        executeQueryByConnection(dbName, sqlQuery);
    }

    @Step
    @Title("Получение количества записей из ответа на запрос в БД")
    public int getCountRecordsFromQuery() {
        return sqlSteps.getDbContextContainer().getActualRecords().size();
    }

    @Step
    @Title("Получение ответа БД одной строкой")
    public String getRecordsFromQuery() {
        return sqlSteps.getDbContextContainer().getActualRecords().toString();
    }


    @Step
    @Title("Получение значения переменной {name} из ответа на запрос в БД")
    public String getVariables(String name) {
        return Optional.ofNullable(sqlSteps.getDbContextContainer().getActualRecords())
                .filter(records -> !records.isEmpty())
                .map(records -> records.get(0).get(name))
                .map(Object::toString)
                .orElse(null);
    }

    @Step
    @Title("Получение значений переменной {name} из ответа на запрос в БД")
    public List<String> getValuesFromResponseDb(String name) {
        int counter = 0;
        List<String> values = new ArrayList<>();
        int getCount = getCountRecordsFromQuery();
        while (getCount != counter) {
            Object value = (sqlSteps.getDbContextContainer().getActualRecords().get(counter)).get(name);
            values.add(value.toString());
            counter++;
        }
        return values;
    }

    @Step
    @Title("Получение значения переменной {name} из {int} записи ответа на запрос в БД")
    public String getVariablesRowNumber(String name, int rowNumber) {
        return Optional.ofNullable(sqlSteps.getDbContextContainer().getActualRecords())
                .filter(records -> !records.isEmpty())
                .filter(records -> rowNumber >= 0 && rowNumber <= records.size())
                .map(records -> records.get(rowNumber - 1).get(name))
                .map(Object::toString)
                .orElse(null);
    }
}
