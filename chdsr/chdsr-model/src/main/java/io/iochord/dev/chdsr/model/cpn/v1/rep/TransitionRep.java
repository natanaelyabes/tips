package io.iochord.dev.chdsr.model.cpn.v1.rep;

import java.util.ArrayList;
import java.util.List;

public class TransitionRep {
	
	List<ArcRep> in = new ArrayList<ArcRep>();
	List<ArcRep> out = new ArrayList<ArcRep>();
	
	public TransitionRep(String name, String guard, String action, String classbinding, String eval, String merge) {
		
	}
	
	public void addIn(ArcRep arc) {
		in.add(arc);
	}
	
	public void addOut(ArcRep arc) {
		out.add(arc);
	}
	
	public void removeIn(ArcRep arc) {
		in.remove(arc);
	}
	
	public void removeOut(ArcRep arc) {
		out.remove(arc);
	}
}
