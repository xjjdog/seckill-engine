package com.github.xjjdog.seckill.demo.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Slf4j
public class DbInit implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) {
        String init_db = "CREATE TABLE exec_err (id varchar(64) PRIMARY KEY NOT NULL,pid varchar(64) NOT NULL,dbname varchar(255) NOT NULL,content varchar(20971520) NOT NULL,status varchar(1) NOT NULL,affectrow int(11) DEFAULT 0,orderno bigint(20) DEFAULT 0,remark varchar(4096) NOT NULL,createdate datetime NOT NULL,modifyDate datetime NOT NULL);CREATE INDEX IDX_EXEC_CREATETIME ON exec_err(createdate);CREATE INDEX IDX_EXEC_PID ON exec_err(pid);";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(init_db);
        } catch (SQLException e) {
            log.error("h2 table already exist");
        }

    }

}
