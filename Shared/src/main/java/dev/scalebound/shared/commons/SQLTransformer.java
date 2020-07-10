package dev.scalebound.shared.commons;

import java.sql.SQLException;

public interface SQLTransformer<T, R> {
  R accept(T paramT) throws SQLException;
}