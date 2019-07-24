package io.iochord.dev.chdsr.model.cpn.v1.rep;


public class ArcRep {
	
	static enum Direction {
		TtP("Direction.TtP"),
		PtT("Direction.PtT");
		
		private String scaladirect;
		 
	    Direction(String scaladirect) {
	        this.scaladirect = scaladirect;
	    }
	 
	    public String getScalaDirection() {
	        return scaladirect;
	    }
	}
	
	TransitionRep tr;
	PlaceRep pr;
	Direction direct;
	
	public ArcRep(PlaceRep placerep, TransitionRep transitionrep, Direction direct, String type, String classbinding, String arcexp, String TtB, String BtT, String addTime, String noToken, boolean isBase) {
		this.tr = transitionrep;
		this.pr = placerep;
		this.direct = direct;
	}
	
	public TransitionRep getTransition() {
		return this.tr;
	}
	
	public PlaceRep getPlace() {
		return this.pr;
	}
	
	public Direction getDirection() {
		return this.direct;
	}
}
