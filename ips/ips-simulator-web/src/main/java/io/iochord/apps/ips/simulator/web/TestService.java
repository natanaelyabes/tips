package io.iochord.apps.ips.simulator.web;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.iochord.apps.ips.common.util.LoggerUtil;

/**
 *
 * Test Async Service
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@Service
public class TestService {
	private static Logger log = LoggerFactory.getLogger(TestService.class);

	private long id = 0;

	@Async("asyncExecutor")
	public CompletableFuture<Long> asyncService() {
		long result = id++;
		log.info("async " + result + " start");
		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
			LoggerUtil.log(ex);
		}
		log.info("async " + result + " complete");
		return CompletableFuture.completedFuture(result);
	}

}
