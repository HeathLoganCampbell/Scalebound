package dev.scalebound.master.booter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ProcessRunner extends Thread {
  private ProcessBuilder processBuilder;
  
  private Process process;
  
  private GenericRunnable<Boolean> runnable;
  
  boolean done = false;
  Boolean error = Boolean.FALSE;
  
  public ProcessRunner(File direcotry, String... args) {
    super("ProcessRunner " + args);
    this.processBuilder = new ProcessBuilder(args);
    this.processBuilder.directory(direcotry);
  }
  
  public void run() {
    try {
      this.process = this.processBuilder.start();
      this.process.waitFor();

      String line = null;
      BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
      while ((line = stdoutReader.readLine()) != null) {
        // process procs standard output here
        System.out.println(" stdout: "+ line);
      }
      BufferedReader stderrReader = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));
      while ((line = stderrReader.readLine()) != null) {
        // process procs standard error here
        System.err.println(" stderr: "+ line);
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
      line = reader.readLine();
      while (line != null) {
        if (line.equals("255"))
          this.error = Boolean.TRUE;
        line = reader.readLine();
      }




    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR! " + e.getMessage());
    } finally {
      this.done = true;
      if (this.runnable != null)
        this.runnable.run(this.error);
    } 
  }
  
  public void start(GenericRunnable<Boolean> runnable) {
    start();
    this.runnable = runnable;
  }
  
  public int exitValue() throws IllegalStateException {
    if (this.process != null)
      return this.process.exitValue();
    throw new IllegalStateException("Process not started yet");
  }
  
  public boolean isDone() {
    return this.done;
  }
  
  public void abort() {
    if (!isDone())
      this.process.destroy();
  }
}