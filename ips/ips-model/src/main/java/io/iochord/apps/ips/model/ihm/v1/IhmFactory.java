package io.iochord.apps.ips.model.ihm.v1;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public interface IhmFactory extends Element {
	IhmGraph create();
	
	IhmGraph create(IhmGraph ref);
	
	Model addModel(IhmGraph cnet);
	
	Node addNode(Model model);
	
	Connector addConnector(Model model, Element source, Element target);
}
