package io.iochord.apps.ips.common.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Resource extends IdentifiableImpl {

	@Getter
	@Setter
	private String parentId;

	@Getter
	@Setter
	private String type;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String json;

	@Getter
	@Setter
	private Date created;

	@Getter
	@Setter
	private Long createdBy;

	@Getter
	@Setter
	private Date modified;

	@Getter
	@Setter
	private Long modifiedBy;

	@Getter
	@Setter
	private Date deleted;

	@Getter
	@Setter
	private Long deletedBy;
	
}
