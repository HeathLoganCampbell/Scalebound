package dev.scalebound.master.booter;

@FunctionalInterface
public interface GenericRunnable<T> {
  void run(T paramT);
}