package io.iochord.dev.chdsr.simulator.web.v1.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import lombok.Getter;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */

public abstract class AService {

	@Autowired
	@Getter
	private DataSource dataSource;

	@Autowired
	@Getter
	private SimpMessagingTemplate wsmTemplate; 
	
}