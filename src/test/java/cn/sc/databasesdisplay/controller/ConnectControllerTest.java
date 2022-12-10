package cn.sc.databasesdisplay.controller;

import cn.hutool.json.JSONUtil;
import cn.sc.databasesdisplay.Application;
import cn.sc.databasesdisplay.dto.DatabasesInfo;
import cn.sc.databasesdisplay.dto.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@Slf4j
class ConnectControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Resource
    ConnectController connectController;

    @Test
    void getConnectName() {
    }

    @Test
    void getCustomDatabaseNames() throws JsonProcessingException {
        DatabasesInfo databasesInfo = getDatabasesInfo();
        Result<String> connectName = connectController.getConnectName(databasesInfo);
        log.info(objectMapper.writeValueAsString(connectName));
        Assert.assertEquals(connectName.getCode(), "0");

        Result<List<String>> customDatabaseNames = connectController.getCustomDatabaseNames(connectName.getData());
        log.info(objectMapper.writeValueAsString(customDatabaseNames));
    }



    private DatabasesInfo getDatabasesInfo() {
        DatabasesInfo databasesInfo = new DatabasesInfo();
        databasesInfo.setId(1596526377100214272L);
        databasesInfo.setName("mysqlTest");
        databasesInfo.setType("mysql");
        databasesInfo.setIp("127.0.0.1");
        databasesInfo.setPort("3306");
        databasesInfo.setUserName("root");
        databasesInfo.setPassword("rootroot");
        databasesInfo.setArgs("");
        return databasesInfo;
    }


    @Test
    public void getCustomTablesName() {
        DatabasesInfo databasesInfo = getDatabasesInfo();
        Result<String> connectName = connectController.getConnectName(databasesInfo);
        Result<List<String>> customDatabaseNames = connectController.getCustomDatabaseNames(connectName.getData());
        customDatabaseNames.getData().parallelStream().forEach(v -> {
            Result<List<String>> customTablesName = connectController.getCustomTablesName(connectName.getData(), v);
            log.info("{}", JSONUtil.toJsonStr(customTablesName));
        });
    }

}