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
        String init_db = "CREATE TABLE sys_user (id bigint(64) NOT NULL auto_increment,account varchar(64) NOT NULL,passwd varchar(128) NOT NULL,salt varchar(32) NOT NULL,name varchar(32) NOT NULL,email varchar(64),phone varchar(32),roleid varchar(32) ,status varchar(1) NOT NULL, createdate datetime ,modifyDate datetime,PRIMARY KEY (`id`) );INSERT INTO sys_user (id, account, passwd, salt, name, email, phone, roleid, status, createdate, modifyDate) VALUES ('1', 'xjjdog', '$2a$05$RHa4AOqIrdUZxr6I10nyKOQdrPCjqEVJumINBqqxMmisAu3h/10HK', '$2a$05$RHa4AOqIrdUZxr6I10nyKO', 'admin', 'admin@admin.com', '13000000000', 'admin', '1', NULL, NULL);";



        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(init_db);
        } catch (SQLException e) {
            log.error("h2 table already exist",e);
        }

    }

}
