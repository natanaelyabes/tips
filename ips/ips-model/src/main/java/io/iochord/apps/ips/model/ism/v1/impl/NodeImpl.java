package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.ELEMENT_NODE)
public class NodeImpl extends ElementImpl implements Node {
	
	public static final String SELF = "self";

	public String getElementType() {
		return ElementType.ELEMENT_NODE;
	}

	@Getter
	@Setter
	@JsonIgnore
	private List<Connector> inputConnectors = new ArrayList<>();

	@Getter
	@Setter
	@JsonIgnore
	private List<Connector> outputConnectors = new ArrayList<>();

	@Getter
	@Setter
	@JsonIgnore
	private Set<NodeImpl> rInputNodes = new HashSet<>();

	@Getter
	@Setter
	@JsonIgnore
	private Set<NodeImpl> rOutputNodes = new HashSet<>();

	@Getter
	@Setter
	@JsonIgnore
	private Map<String, Integer> rTokenInitial = new LinkedHashMap<>();

	@Getter
	@Setter
	@JsonIgnore
	private Map<String, Integer> rToken = new LinkedHashMap<>();

	@Getter
	@Setter
	@JsonIgnore
	private int rProduced = 0;

	@Getter
	@Setter
	@JsonIgnore
	private int rConsumed = 0;

	@Getter
	@Setter
	@JsonIgnore
	private int rMissing = 0;
	
	public NodeImpl() {
		getRTokenInitial().put(SELF, 0);
	}
	
	@JsonIgnore
	public int getRRemaining() {
		int token = 0;
		for (Entry<String, Integer> i : getRToken().entrySet()) {
			token += i.getValue();
		}
		return token;
	}
	
	@JsonIgnore
	public void rReset() {
		getRToken().clear();
		int produced = 0;
		for (Entry<String, Integer> i : getRTokenInitial().entrySet()) {
			getRToken().put(i.getKey(), i.getValue());
			produced += i.getValue();
		}
		setRProduced(produced);
		setRConsumed(0);
		setRMissing(0);
	}
	
	@JsonIgnore
	public boolean rIsNodeEnabled() {
		return getRRemaining() > 0;
	}
	
	@JsonIgnore
	public void rConsumeToken(String type, int i) {
		getRToken().put(SELF, getRToken().get(SELF) - i);
		setRConsumed(getRConsumed() + i);
	}
	
	@JsonIgnore
	public void rProduceToken(String type, int i) {
		getRToken().put(SELF, getRToken().get(SELF) + i);
		setRProduced(getRProduced() + i);
	}
	
	@JsonIgnore
	public void rFire(NodeImpl ns) {
		System.out.println("Firing " + getId() + " " + getLabel());
		if (!rIsNodeEnabled()) {
			if (getRInputNodes() != null) {
				for (NodeImpl n : getRInputNodes()) {
					if (!(n instanceof ActivityImpl)) {
						n.rFire(this);
					}
				}
			}
		}
		if (getRRemaining() > 0) {
			rConsumeToken(SELF, 1);
		} else {
			setRMissing(getRMissing() + 1);
		}
		if (getROutputNodes() != null) {
			for (NodeImpl n : getROutputNodes()) {
				n.rProduceToken(n.getId(), 1);
			}
		}
	}
	
}
