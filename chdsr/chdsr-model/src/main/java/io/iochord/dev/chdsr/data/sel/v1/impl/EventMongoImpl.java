package io.iochord.dev.chdsr.data.sel.v1.impl;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@Entity
@Document(collection="ev1000")
public class EventMongoImpl extends EventImpl {
	
	public EventMongoImpl() {
		
	}

	public EventMongoImpl(String c, String a, Date t) {
		setCaseId(c);
		setActivity(a);
		setTimestamp(t);
	}

	public EventMongoImpl(String c, String a, Date t, String o, String r) {
		setCaseId(c);
		setActivity(a);
		setTimestamp(t);
		setOriginator(o);
		setResource(r);
	}

}
