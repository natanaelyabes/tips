package io.iochord.apps.ips.common.models;

import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class IdentifiableImpl implements Identifiable {

	@Getter
	@Setter
	private String id = String.valueOf(System.currentTimeMillis());

}
