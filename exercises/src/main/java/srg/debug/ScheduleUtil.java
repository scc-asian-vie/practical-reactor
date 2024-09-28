package srg.debug;

import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class ScheduleUtil implements Disposable {
  private final Scheduler scheduler;

  public ScheduleUtil(String name) {
    this.scheduler = Schedulers.newSingle(name);
  }

  public Scheduler getScheduler() {
    return scheduler;
  }

  @Override
  public void dispose() {
    scheduler.dispose();
  }
}
