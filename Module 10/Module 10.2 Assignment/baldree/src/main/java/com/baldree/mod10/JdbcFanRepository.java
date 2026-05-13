package com.baldree.mod10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcFanRepository implements FanRepository {

    public static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/databasedb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static final String DEFAULT_USER = "student1";
    public static final String DEFAULT_PASSWORD = "pass";

    private final String databaseUrl;
    private final String username;
    private final String password;

    public JdbcFanRepository() {
        this(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public JdbcFanRepository(String databaseUrl, String username, String password) {
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, username, password);
    }

    @Override
    public Optional<Fan> findById(int id) throws SQLException {
        String sql = """
                SELECT ID, firstname, lastname, favoriteteam
                FROM fans
                WHERE ID = ?
                """;

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Fan fan = new Fan(
                            resultSet.getInt("ID"),
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            resultSet.getString("favoriteteam"));

                    return Optional.of(fan);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean update(Fan fan) throws SQLException {
        String sql = """
                UPDATE fans
                SET firstname = ?, lastname = ?, favoriteteam = ?
                WHERE ID = ?
                """;

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fan.getFirstName());
            statement.setString(2, fan.getLastName());
            statement.setString(3, fan.getFavoriteTeam());
            statement.setInt(4, fan.getId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated == 1;
        }
    }
}