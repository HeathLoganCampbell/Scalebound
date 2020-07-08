package dev.sprock.scalebound.shared.booter;

@FunctionalInterface
public interface GenericRunnable<T> {
  void run(T paramT);
}