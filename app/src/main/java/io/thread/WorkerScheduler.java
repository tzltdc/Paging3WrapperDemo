package io.thread;

import io.reactivex.Scheduler;
import javax.inject.Inject;

public class WorkerScheduler {

  private final Scheduler ioScheduler;

  @Inject
  WorkerScheduler(Scheduler workerScheduler) {
    this.ioScheduler = workerScheduler;
  }

  public Scheduler get() {
    return ioScheduler;
  }
}
