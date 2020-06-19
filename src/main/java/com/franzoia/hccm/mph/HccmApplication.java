package com.franzoia.hccm.mph;

import java.text.ParseException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.franzoia.hccm.mph.service.PriceHandlerService;

@SpringBootApplication
@EnableAsync
public class HccmApplication implements WebMvcConfigurer {	
	
	@Autowired
	private PriceHandlerService priceHandlerService;
	
	private ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
	
    public static void main(String[] args) {
		SpringApplication.run(HccmApplication.class, args);
    }
	
    /**
     * creates a schedule task that will read the messages from a server
     * For testing purposes the messages are read from a queue that keeps being filled every time it becomes empty
     * @return
     */
	@Bean
	public Executor taskExecutor() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		Runnable task = () -> {
			try {
				if (!messageQueue.isEmpty()) {
					priceHandlerService.onMessage(messageQueue.poll());
				} else {
					createMessageQueue();
				}
			} catch (InterruptedException | NumberFormatException | ParseException e) {
				e.printStackTrace();
			}
		};
		executor.scheduleWithFixedDelay(task, 0, 30, TimeUnit.SECONDS);
		return executor;
	}
	
	/**
	 * creates sample messages for the reading queue
	 */
	public void createMessageQueue() {
		
		messageQueue.add("1, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");
		messageQueue.add("2, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:001");
		messageQueue.add("3, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:001");
		messageQueue.add("4, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:100");
		
	}
}