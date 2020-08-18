package io.iochord.apps.ips.model.analysis.services.dtm.model;

import java.util.ArrayList;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 *
 * @param <E>
 */
public class DecisionTree<E, F> {
	
	/**
	 * The array to store children of a tree node.
	 * 
	 * @param children The children of a tree node.
	 * @return The current state of a children of tree node.
	 */
	@Getter @Setter private ArrayList<DecisionTree<E, F>> children;
	
	/**
	 * The condition that causes the traversal to current node.
	 * 
	 * @param condition The condition owned by the current node.
	 * @return The condition that causes the traversal to current node.
	 */
	@Getter @Setter private E condition;
	
	/**
	 * The data stored in a tree node.
	 * 
	 * @param data The data of a tree node.
	 * @return The data stored in a tree node.
	 */
	@Getter @Setter private F data; // node value
	
	public DecisionTree(F nodeData) {
		initializeTree(null, nodeData);
	}
	
	public DecisionTree(E nodeEdge, F nodeData) {
		initializeTree(nodeEdge, nodeData);
	}
	
	/**
	 * Method to initialize tree.
	 * 
	 * @param nodeEdge The condition applied to the traversed node.
	 * @param nodeData Payload of the data to represent the node.
	 */
	public void initializeTree(E nodeEdge, F nodeData) {
		data = nodeData;
		condition = nodeEdge;
		children = new ArrayList<>();
	}
	
	/**
	 * Inserts new value to a tree node.
	 * 
	 * @param insertValue
	 */
	public void insert(E insertCondition, F insertValue) {
		children.add(new DecisionTree<>(insertCondition, insertValue));
	}
	
	/**
	 * Remove child node from a branch of parent node.
	 * 
	 * @param index The index of child node to remove.
	 * @return true if children does exists, false otherwise.
	 */
	public boolean remove(int index) {
		if (children.size() > index) { 
			children.remove(index); 
		} else { 
			return false; 
		}
		return true;
	}
	
	/**
	 * Wrapper method to get children from the tree.
	 * 
	 * @param index The index of the children node.
	 * @return The children of decision tree at index.
	 */
	public DecisionTree<E, F> getChildren(int index) {
		if (index < children.size()) {
			return children.get(index);
		}
		return null;
	}
	
	/**
	 * Convert the decision tree into flat stream.
	 * 
	 * @return The flattened tree.
	 */
	public Stream<DecisionTree<E, F>> flattened() {
		return Stream.concat(
                Stream.of(this),
                children.stream().flatMap(DecisionTree<E, F>::flattened));
	}
	
	/**
	 * The main method of a class. For testing purpose only.
	 * Deleted or commented out when no longer needed.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Tree definition: Root -> (1 -> (3 & 4)) & (2 -> (5))
		DecisionTree<String, String> tree = new DecisionTree<>("Root");
			tree.insert("Amount > 100", "1");
				tree.getChildren().get(0).insert("Policy = A", "3");
				tree.getChildren().get(0).insert("Policy = B", "4");
			tree.insert("Amount <= 100", "2");
				tree.getChildren().get(1).insert("Policy = C", "5");
				
		// Flatten the tree and print to stdout.
		tree.flattened().map(c -> c.getData()).forEach(System.out::println);
	}
}
