package io.iochord.dev.chdsr.data.sel.v1.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.iochord.dev.chdsr.data.sel.v1.Event;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@Entity
@Table(name="events")
public class EventImpl extends AttributableObject implements Event<String, String, String, Date, String, String> {
	
	@Id
	@GeneratedValue
	@Getter
	@Setter
	private long eventUid;
	
	@Getter
	@Setter
	private String caseId;

	@Getter
	@Setter
	private String eventId;

	@Getter
	@Setter
	private String activity;

	@Getter
	@Setter
	private Date timestamp;

	@Getter
	@Setter
	private String originator;

	@Getter
	@Setter
	private String resource;
	
	
	public EventImpl() {
		
	}

	public EventImpl(String c, String a, Date t) {
		setCaseId(c);
		setActivity(a);
		setTimestamp(t);
	}

	public EventImpl(String c, String a, Date t, String o, String r) {
		setCaseId(c);
		setActivity(a);
		setTimestamp(t);
		setOriginator(o);
		setResource(r);
	}

}
