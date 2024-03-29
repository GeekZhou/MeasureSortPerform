/**
 * CpuTimer - class to compute CPU time used by a block of code.
 * 
 * To use, create a CpuTimer object. Calling the getElapsedCpuTime() method will
 * then return the CPU time used (in seconds, floating point) since the object
 * was created. The time returned is the combined user and system time. The
 * CpuTimer object must be created and used within the same thread, and measures
 * time only for that thread.
 * 
 * NOTE: for accurate timing comparisons, make sure to specify the parameter
 * -Xint when running programs using this class. This parameter turns off Java
 * run-time optimizations using the JIT (Just In Time) compiler, which may cause
 * unpredictable timing results. In Eclipse, go to 
 *     Project -> Properties -> Run/Debug Settings.
 * Double click on the launch configuration that you're using, and select the
 * "Arguments" tab. Add -Xint to the "VM arguments" box.
 */

import java.lang.management.*;

/**
 * @author Mike Goss (mikegoss@cs.du.edu)
 * 
 */
public class CpuTimer {
  public CpuTimer() {
    bean = ManagementFactory.getThreadMXBean();
    assert bean.isCurrentThreadCpuTimeSupported()
      : "getCurrentThreadCpuTime not supported by this JVM, use a different JVM";
    startTimeSeconds = 1.0e-9 * (double) bean.getCurrentThreadCpuTime();
  }
  
  public double getElapsedCpuTime() {
    return 1.0e-9 * (double) bean.getCurrentThreadCpuTime() - startTimeSeconds;
  }

  // Thread management bean to access CPU time
  private ThreadMXBean bean;

  // Amount of CPU time used before creation of this class
  private double startTimeSeconds;
}