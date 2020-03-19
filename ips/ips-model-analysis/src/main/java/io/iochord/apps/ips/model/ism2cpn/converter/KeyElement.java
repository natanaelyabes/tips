package io.iochord.apps.ips.model.ism2cpn.converter;

public final class KeyElement {
	public static final String TYPE = "Type";
	public static final String RESOURCE = "Resource";
	public static final String QUEUE = "Queue";
	public static final String PLACE = "Place";
	public static final String TRANSITION = "Transition";
	public static final String GUARD = "Guard";
	public static final String ACTION = "Action";
	public static final String ACTIONFUN = "ActionFun";
	public static final String ACTIONTTB = "ActionTtB";
	public static final String ACTIONBTT = "ActionBtT";
	public static final String BINDING = "Binding";
	public static final String EVAL = "Eval";
	public static final String MERGE = "Merge";
	public static final String ARC = "Arc";
	public static final String ARCEXP = "ArcExp";
	public static final String TTB = "TtB";
	public static final String BTT = "BtT";
	public static final String ADDTIME = "addTime";
	
	public static final String BPRECONC = " = (b:";
	public static final String BENDCONC = ") => {";
	public static final String SETORIGINCONC = ".setOrigin(Map[String,String]((\"origin\",\"";
	public static final String RESCONC = "Resource[";
	public static final String SOMECONC = "Some(entity)";
	public static final String SOMENONECONC = "Some(entity), None";
	public static final String ENDCOLCONC = "\")))\n";
	public static final String ROLECONC = "\"),(\"role\",\"";
	public static final String GRAPHENDCONC = "\ncgraph";
	public static final String TYPECONC = "type ";
	public static final String COLSETCONC = "colset";
	public static final String ENTCONC = "entity";
	public static final String ENTOPTCONC = "entity:Option[";
	public static final String ENDSTRCONC = "_end_";
	public static final String NAP2STRCONC = "_nap2";
	public static final String NAP3STRCONC = "_nap3";
	public static final String NATENDSTRCONC = "_natend";
	public static final String NATSTARTSTRCONC = "_natstart";
	public static final String QENDPSTRCONC = "_qendp";
	public static final String QSTTSTRCONC = "_qstt";
	public static final String STARTSTRCONC = "_start_";
	public static final String START0STRCONC = "_start_0";
	public static final String BENTTGETCONC = "b.entity.get";
	public static final String BQGETCONC = "b.queue.get";
	public static final String BENTTQGETCONC = "b.entity.get::b.queue.get";
	public static final String BRESGETCONC = "b.resource.get";
	
	private KeyElement() {
		
	}
}

