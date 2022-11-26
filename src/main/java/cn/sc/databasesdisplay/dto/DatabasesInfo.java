package cn.sc.databasesdisplay.dto;

import lombok.Data;

@Data
public class DatabasesInfo {

    private Long id;
    private String name;
    private String type;
    private String ip;
    private String port;
    private String userName;
    private String password;
    private String args;

}
