package io.iochord.apps.ips.model.ism.v1.nodes.enums;

import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public enum DistributionType {
	CONSTANT("1"),
	RANDOM("Rand.randInt(lowB, upB)"),
	BINOMIAL("Binomial(1, )"),
	NEGATIVE_BINOMIAL("NegativeBinomial(n, p)"),
	GAMMA("Gamma(n, p)"),
	GAUSSIAN("Gaussian(mean,stddev)"),
	LAPLACE("Laplace(location, scale)"),
	LOGNORMAL("LogNormal(mean, stddev)"),
	STUDENTST("StudentsT(degreeOfFreedom)"),
	UNIFORM("Uniform(lowB, upB)"),
	RAYLEIGH("Rayleigh(scale)"),
	WEIBULL("Weibull(alpha, beta)"),
	;
	
	@Getter
	@Setter
	private String defaultValue;
	
	private DistributionType(String defaultValue) {
		setDefaultValue(defaultValue);
	}
	
}
