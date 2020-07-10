package dev.scalebound.shared.commons;

import java.sql.SQLException;

public interface SQLConsumer<T> {
  void accept(T paramT) throws SQLException;
}