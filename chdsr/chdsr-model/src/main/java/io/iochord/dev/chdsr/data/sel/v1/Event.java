package io.iochord.dev.chdsr.data.sel.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.data.sel.v1.impl.EventImpl;

/**
 *
 * SEL = Simple Event Log
 *
 * @package chdsr-model
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 *
 */
@JsonDeserialize(as=EventImpl.class)
public interface Event<E, C, A, T, O, R> extends Attributable {
	public static final String TYPE = "event";
	
	long getEventUid();
	
	E getEventId();

	C getCaseId();

	A getActivity();

	T getTimestamp();

	O getOriginator();

	R getResource();
}
