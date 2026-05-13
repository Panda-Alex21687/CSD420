package com.baldree.mod10;

import java.sql.SQLException;
import java.util.Optional;

public interface FanRepository {
    Optional<Fan> findById(int id) throws SQLException;

    boolean update(Fan fan) throws SQLException;
}