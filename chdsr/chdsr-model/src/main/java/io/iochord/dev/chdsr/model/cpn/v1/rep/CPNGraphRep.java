package io.iochord.dev.chdsr.model.cpn.v1.rep;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CPNGraphRep {
	
	List<TransitionRep> listtrans = new ArrayList<TransitionRep>();
	List<PlaceRep> listplace = new ArrayList<PlaceRep>();
	List<ArcRep> listarc = new ArrayList<ArcRep>();
	
	public void addTransition(TransitionRep tr) {
		listtrans.add(tr);
	}
	
	public void removeTransition(TransitionRep tr) {
		listtrans.remove(tr);
		Iterator<ArcRep> iterator = listarc.iterator();
		while(iterator.hasNext()) {
			ArcRep arc = iterator.next();
			if(arc.getTransition() == tr)
			{
				listarc.remove(arc);
				break;
			}
		}
	}
	
	public void addPlace(PlaceRep pr) {
		listplace.add(pr);
		Iterator<ArcRep> iterator = listarc.iterator();
		while(iterator.hasNext()) {
			ArcRep arc = iterator.next();
			if(arc.getPlace() == pr)
			{
				listarc.remove(arc);
				break;
			}
		}
	}
	
	public void removePlace(PlaceRep pr) {
		listplace.remove(pr);
	}
	
	public void addArc(ArcRep arc) {
		listarc.add(arc);
		if(arc.getDirection() == ArcRep.Direction.PtT)
			arc.getTransition().addIn(arc);
		else
			arc.getTransition().addOut(arc);
		
	}
	
	public void removeArc(ArcRep arc) {
		listarc.remove(arc);
		if(arc.getDirection() == ArcRep.Direction.PtT)
			arc.getTransition().removeIn(arc);
		else
			arc.getTransition().removeOut(arc);
	}
}
