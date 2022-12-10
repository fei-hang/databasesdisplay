package cn.sc.databasesdisplay.controller;

import cn.sc.databasesdisplay.dto.DatabasesInfo;
import cn.sc.databasesdisplay.dto.Result;
import cn.sc.databasesdisplay.utils.DatabaseDisplayUtils;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/connect")
public class ConnectController {

    final DatabaseInfoController databaseInfoController;
    final DatabaseDisplayUtils databaseDisplayUtils;
    final String ConnectPre = "CONN";

    @PostMapping("/getConnectName")
    public Result<String> getConnectName(@RequestBody DatabasesInfo databasesInfo) {

        DruidDataSource druidDataSource = new DruidDataSource();
        switch (databasesInfo.getType().toLowerCase()) {
            case "mysql" :
                druidDataSource.setUrl("jdbc:mysql://"+databasesInfo.getIp()+":" + databasesInfo.getPort());
                druidDataSource.setValidationQuery("SELECT 1");
        };

        druidDataSource.setUsername(databasesInfo.getUserName());
        druidDataSource.setPassword(databasesInfo.getPassword());
        try {
            druidDataSource.setConnectionErrorRetryAttempts(0);
            druidDataSource.setFailFast(true);
            druidDataSource.getConnection();
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
        if (databasesInfo.getId() != null) databaseInfoController.updateDatabasesInfoResult(databasesInfo);
        else databaseInfoController.addDatabaseInfo(databasesInfo);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(druidDataSource);
        databaseDisplayUtils.setJdbcTemplate(ConnectPre + databasesInfo.getId(), jdbcTemplate);
        return Result.ok(ConnectPre + databasesInfo.getId());
    }

    @GetMapping("/getCustomDatabaseNames/{jdbcId}")
    public Result<List<String>> getCustomDatabaseNames(@PathVariable("jdbcId") String jdbcId) {
        JdbcTemplate jdbcTemplate = databaseDisplayUtils.getJdbcTemplate(jdbcId);
        List<String> databasesNames = jdbcTemplate.queryForList("SHOW DATABASES;", String.class);
        return Result.ok(databasesNames);
    }

    @GetMapping("/getCustomTablesName/{jdbcId}/{databaseName}")
    public Result<List<String>> getCustomTablesName(@PathVariable("jdbcId") String jdbcId, @PathVariable("databaseName") String databaseName) {
        if (!getCustomDatabaseNames(jdbcId).getData().contains(databaseName)) throw new RuntimeException("数据库不存在!");
        JdbcTemplate jdbcTemplate = databaseDisplayUtils.getJdbcTemplate(jdbcId);
        jdbcTemplate.execute("USE " + databaseName + ";");
        List<String> databasesNames = jdbcTemplate.queryForList("SHOW TABLES;", String.class);
        return Result.ok(databasesNames);
    }

}
