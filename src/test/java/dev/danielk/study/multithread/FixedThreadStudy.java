package dev.danielk.study.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FixedThreadStudy {

	@Test
	public void test01() throws InterruptedException {
		
		ExecutorService service = Executors.newFixedThreadPool(3);
		for(int i=0; i < 13; i++) {
			final int SEQ = i;
			service.execute(new Runnable() {
				public void run() {
					log.debug("i({}) \t\t starting", SEQ);
					try {
						Thread.sleep(1000*3);
					} catch (InterruptedException e) {
					}
					log.debug("i({}) \t\t\t\t done", SEQ);
				}
			});
			log.debug("i({})  execute thread");
		}
		
		service.shutdown();
		boolean isGracefulTermination = service.awaitTermination(2, TimeUnit.MINUTES);
		log.debug("isGracefulTermination: {}", isGracefulTermination);
	}
}
