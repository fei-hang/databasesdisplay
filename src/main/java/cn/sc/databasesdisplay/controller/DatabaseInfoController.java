package cn.sc.databasesdisplay.controller;

import cn.hutool.core.util.IdUtil;
import cn.sc.databasesdisplay.dto.DatabasesInfo;
import cn.sc.databasesdisplay.dto.Result;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/database")
public class DatabaseInfoController {

    final JdbcTemplate h2Connexion;

    @GetMapping("/getAllIdAndNameOfDatabaseInfo")
    public Result<List<DatabasesInfo>> getAllIdAndNameOfDatabaseInfo() {
        return Result.ok(h2Connexion.queryForList("SELECT id, name FROM databasesInfo;", DatabasesInfo.class));
    }

    @GetMapping("/getDatabasesInfoResultById/{id}")
    public Result<DatabasesInfo> getDatabasesInfoResultById(@PathVariable("id") String id) {
        return Result.ok(h2Connexion.queryForObject("SELECT * FROM databasesInfo WHERE id=?;", DatabasesInfo.class, id));
    }

    @PostMapping("/addDatabaseInfo")
    public Result<DatabasesInfo> addDatabaseInfo(@RequestBody DatabasesInfo databasesInfo) {
        long id = IdUtil.getSnowflakeNextId();
        h2Connexion.update("INSERT INTO databasesInfo(id, name, type, ip, port, userName, password, args) VALUES (?,?,?,?,?,?,?,?);",
                id,
                databasesInfo.getName(),
                databasesInfo.getType(),
                databasesInfo.getIp(),
                databasesInfo.getPort(),
                databasesInfo.getUserName(),
                databasesInfo.getPassword(),
                databasesInfo.getArgs());
        databasesInfo.setId(id);
        return Result.ok(databasesInfo);
    }

    @PutMapping("/updateDatabaseInfo")
    public Result<DatabasesInfo> updateDatabasesInfoResult(@RequestBody DatabasesInfo databasesInfo) {
        h2Connexion.update("UPDATE databasesInfo set name=?, type=?, ip=?, port=?, userName=?, password=?, args=? WHERE id=?;",
                databasesInfo.getName(),
                databasesInfo.getType(),
                databasesInfo.getIp(),
                databasesInfo.getPort(),
                databasesInfo.getUserName(),
                databasesInfo.getPassword(),
                databasesInfo.getArgs(),
                databasesInfo.getId());
        return Result.ok(databasesInfo);
    }

    @DeleteMapping("deleteDatabaseInfo")
    public Result<Object> deleteDatabaseInfo(@RequestParam("databaseId") String databaseId) {
        h2Connexion.update("DELETE databasesInfo WHERE id=?", databaseId);
        return Result.ok();
    }

}




