package com.sunilos.springboot.jobs;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	@Scheduled(fixedDelay = 2000)
	public void scheduleTaskWithFixedRate() {
		System.out.println("Fixed Delay Task :: Execution Time - {}" + new Date());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	@Scheduled(fixedRate = 2000, initialDelay = 5000)
	public void scheduleTaskWithInitialDelay() {
		System.out.println("Fixed Rate Task with Initial Delay :: Execution Time - " + new Date());
	}

	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskWithCronExpression() {
		System.out.println("Cron Task :: Execution Time - {}" + new Date());
	}

	public void scheduleTaskWithFixedDelay() {
	}
}
