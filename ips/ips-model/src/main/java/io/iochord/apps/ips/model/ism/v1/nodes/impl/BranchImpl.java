package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.NODE_BRANCH)
public class BranchImpl extends NodeImpl implements Branch {
	@Getter
	@Setter
	private BranchGate gate = BranchGate.XOR;

	private BranchType branchType = BranchType.SPLIT;

	@Getter
	@Setter
	private BranchRule rule = BranchRule.PROBABILITY;

	@Getter
	@Setter
	private Map<String, String> conditions = new LinkedHashMap<>();
	
	public String getElementType() {
		return ElementType.NODE_BRANCH;
	}

	@Override
	public BranchType getType() {
		return branchType;
	}
	
	public void setType(BranchType branchType) {
		this.branchType = branchType;
	}
	
	@Override
	public void rConsumeToken(String type, int i) {
		if (getType() == BranchType.SPLIT) {
			super.rConsumeToken(SELF, i);
		} else if (getType() == BranchType.JOIN) {
			if (!getRToken().containsKey(type)) {
				getRToken().put(type, 0);
			}
			int consumed = Math.min(getRToken().get(type), i);
			getRToken().put(type, getRToken().get(type) - consumed);
			setRConsumed(getRConsumed() + consumed);
		}
	}
	
	@Override
	public void rProduceToken(String type, int i) {
		if (getType() == BranchType.SPLIT) {
			super.rProduceToken(SELF, i);
		} else if (getType() == BranchType.JOIN) {
			if (!getRToken().containsKey(type)) {
				getRToken().put(type, 0);
			}
			getRToken().put(type, getRToken().get(type) + i);
			setRProduced(getRProduced() + i);
		}
	}
	
	@Override
	public boolean rIsNodeEnabled() {
		if (getType() == BranchType.SPLIT) {
			return super.rIsNodeEnabled();
		} else if (getType() == BranchType.JOIN) {
			if (getGate() == BranchGate.XOR) {
				boolean result = false;
				for (NodeImpl n : getRInputNodes()) {
					if (getRToken().containsKey(n.getId()) 
						&& getRToken().get(n.getId()) > 0) {
						result = true;
						break;
					}
				}
				return result;
			} else if (getGate() == BranchGate.AND && getRInputNodes() != null) {
				boolean result = true;
				for (NodeImpl n : getRInputNodes()) {
					if (!getRToken().containsKey(n.getId()) 
						|| getRToken().get(n.getId()) < 1) {
						result = false;
						break;
					}
				}
				return result;
			}
		}
		return false;
	}
	
	@Override
	public void rFire(NodeImpl ns) {
		if (!rIsNodeEnabled()) {
			if (getRInputNodes() != null) {
				for (NodeImpl n : getRInputNodes()) {
					if (!(n instanceof ActivityImpl)) {
						n.rFire(this);
					}
				}
			}
		}
		if (rIsNodeEnabled()) {
			System.out.println("Firing " + getId() + " " + getLabel() + " " + getGate());
			if (getType() == BranchType.SPLIT) {
				rConsumeToken(SELF, 1);
				if (getGate() == BranchGate.XOR) {
					if (getROutputNodes() != null) {
						if (ns != null && getROutputNodes().contains(ns)) {
							ns.rProduceToken(getId(), 1);
						} else {
							for (NodeImpl n : getROutputNodes()) {
								n.rProduceToken(getId(), 1);
							}
						}
					}
				} else if (getGate() == BranchGate.AND) {
					if (getROutputNodes() != null) {
						for (NodeImpl n : getROutputNodes()) {
							n.rProduceToken(getId(), 1);
						}
					}
				}
			} else if (getType() == BranchType.JOIN){
				if (getGate() == BranchGate.XOR) {
					for (NodeImpl n : getRInputNodes()) {
						if (getRToken().containsKey(n.getId()) 
							&& getRToken().get(n.getId()) > 0) {
							rConsumeToken(n.getId(), 1);
							break;
						}
					}
				} else if (getGate() == BranchGate.AND) {
					for (NodeImpl n : getRInputNodes()) {
						if (!getRToken().containsKey(n.getId()) 
							|| getRToken().get(n.getId()) < 1) {
							rConsumeToken(n.getId(), 1);
						}
					}
				}
				if (getROutputNodes() != null) {
					for (NodeImpl n : getROutputNodes()) {
						n.rProduceToken(getId(), 1);
					}
				}
			}
		}
	}
	
}
