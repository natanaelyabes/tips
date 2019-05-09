package io.iochord.dev.chdsr.model.converter.sbp2cpn;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph;
import lombok.Getter;
import lombok.Setter;

public class CPNScalaGraph extends CPNGraph {
	@Getter
	@Setter
	private Map<String, String> colsets = new LinkedHashMap<>();
}
