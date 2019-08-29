package io.iochord.dev.chdsr.simulator.web.v1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@Service
public class AllServices {

	@Autowired
	@Getter
	DataConnectionService dataConnectionService;
	
}
