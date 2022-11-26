package cn.sc.databasesdisplay.controller;

import cn.sc.databasesdisplay.Application;
import cn.sc.databasesdisplay.dto.DatabasesInfo;
import cn.sc.databasesdisplay.dto.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@Slf4j
public class DatabaseInfoControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    DatabaseInfoController databaseInfoController;

    @Test
    public void getAllDatabasesInfoResult() throws JsonProcessingException {
        Result<Object> allDatabasesInfoResult = databaseInfoController.getAllDatabasesInfoResult();
        log.info(objectMapper.writeValueAsString(allDatabasesInfoResult));
        Assert.assertEquals(allDatabasesInfoResult.getCode(),"0");
    }

    @Test
    public void addDatabaseInfo() throws JsonProcessingException {
        DatabasesInfo databasesInfo = new DatabasesInfo();
        databasesInfo.setName("mysqltest");
        databasesInfo.setType("mysql");
        databasesInfo.setIp("127.0.0.1");
        databasesInfo.setPort("3306");
        databasesInfo.setUserName("root");
        databasesInfo.setPassword("rootroot");
        databasesInfo.setArgs("");
        Result<DatabasesInfo> objectResult = databaseInfoController.addDatabaseInfo(databasesInfo);
        log.info(objectMapper.writeValueAsString(objectResult));
    }

    @Test
    public void updateDatabasesInfoResult() throws JsonProcessingException {
        DatabasesInfo databasesInfo = new DatabasesInfo();
        databasesInfo.setId(1596526377100214272L);
        databasesInfo.setName("mysqlTest");
        databasesInfo.setType("mysql");
        databasesInfo.setIp("127.0.0.1");
        databasesInfo.setPort("3306");
        databasesInfo.setUserName("root");
        databasesInfo.setPassword("rootroot");
        databasesInfo.setArgs("");
        Result<DatabasesInfo> infoResult = databaseInfoController.updateDatabasesInfoResult(databasesInfo);
        log.info(objectMapper.writeValueAsString(infoResult));
    }

}