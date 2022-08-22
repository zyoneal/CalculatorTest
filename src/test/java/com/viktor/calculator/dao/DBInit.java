package com.viktor.calculator.dao;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBInit {

    public static void startUp() throws Exception {
        URL url1 = ExpressionDaoImplTest.class.getClassLoader().getResource("create_model.sql");
        URL url2 = ExpressionDaoImplTest.class.getClassLoader().getResource("expression_data.sql");

        List<String> strings1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = strings1.stream().collect(Collectors.joining());

        List<String> strings2 = Files.readAllLines(Paths.get(url2.toURI()));
        String sql2 = strings2.stream().collect(Collectors.joining());
        try (Connection connection = ConnectionBuilder.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
        }
    }
}
