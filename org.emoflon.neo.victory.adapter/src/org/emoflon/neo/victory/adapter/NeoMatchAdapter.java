//package org.emoflon.neo.victory.adapter;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//
//
//import org.emoflon.ibex.tgg.ui.debug.api.Graph;
//import org.emoflon.ibex.tgg.ui.debug.api.Match;
//import org.emoflon.ibex.tgg.ui.debug.api.Rule;
//import org.emoflon.ibex.tgg.ui.debug.api.enums.EdgeType;
//import org.emoflon.ibex.tgg.ui.debug.api.impl.GraphBuilder;
//import org.emoflon.neo.engine.api.patterns.IMatch;
//import org.emoflon.neo.neo4j.adapter.models.NeoCoreBuilder;
//import org.neo4j.driver.v1.types.Node;
//import org.neo4j.driver.v1.types.Path;
//import org.neo4j.driver.v1.types.Relationship;
//
//public class NeoMatchAdapter implements Match {
//    private IMatch match;
//    private Collection<NeoRuleAdapter> rules;
//    private NeoCoreBuilder builder;
//
//    public NeoMatchAdapter(NeoCoreBuilder builder, IMatch match, Collection<NeoRuleAdapter> rules) {
//	this.match = match;
//	this.rules = rules;
//	this.builder = builder;
//    }
//
//    public IMatch getWrappedMatch() {
//	return match;
//    }
//
//    @Override
//    public String getName() {
//	return match.getPattern().getName();
//    }
//
//    @Override
//    public boolean isBlocked() {
//	// TODO Auto-generated method stub
//	return false;
//    }
//
//    @Override
//    public String getBlockingReason() {
//	// TODO Auto-generated method stub
//	return "";
//    }
//
//    @Override
//    public Rule getRule() {
//	return rules.stream().filter(r -> r.getName().equals(getName())).findAny().get();
//    }
//
//    @Override
//    public Graph getGraph(int neighbourhoodSize) {
////	neighbourhoodSize=1;
//	var graphBuilder = new GraphBuilder();
////		for (var id : match.getNodeIDs().values()) {
//	var result = builder.executeQuery(MatchQuery.create(match, getRule(), 1));
//
//	System.out.println(result.summary());
//	
//	var records = result.list();
////	System.out.println(records);
//	Map<Long, NeoNodeAdapter> nodeToNeoNode = new HashMap<>();
//	Map<Long, NeoNodeAdapter> matchNodes = new HashMap<>();
//	Map<Long, NeoModelEdgeAdapter> relations = new HashMap<>();
//
//	for (var rec : records) {
//	    
//	    var map = rec.asMap();
//	    for (var o : map.values()) {
//		var path = (Path) o;
////		System.out.println(path);
//
//		
//		
//			   
//       path.relationships().forEach(r -> relations.putIfAbsent(r.id(), new NeoModelEdgeAdapter(nodeToNeoNode.get(r.startNodeId()), nodeToNeoNode.get(r.endNodeId()), EdgeType.NORMAL, "Edge")));
//			
//		    
//		
//		
//	path.nodes().forEach(n -> nodeToNeoNode.putIfAbsent(n.id(), new NeoNodeAdapter(n)));
//
//		
//
//		
//		
//	
//	    
//	    
//	}
// }
//	nodeToNeoNode.forEach((Node, neoNode) -> graphBuilder.addNode(neoNode));
//	
//	
//	
//
//       match.getNodeIDs().forEach((name,id)->{
//
//	   
//	   getRule().getGraph().getNodes().forEach(n->{
//	       if(n.getName().equals(name)){
//		   var ruleNode=n;
//		   graphBuilder.addEdge(new NeoModelEdgeAdapter(ruleNode,nodeToNeoNode.get(id),EdgeType.MATCH,"" ));
//	       }
//	       });
//       
//	   
//       
//	   
//       });
////       if(neighbourhoodSize==0) {
////
////	    
////	    match.getEdgeIDs().forEach((name,id) ->
////	       
////	       {
////		   var queRyResult=builder.executeQuery("MATCH (a)-[r]->(b) WHERE id(r)= 224 RETURN r");
////	       queRyResult.list().forEach(n->{
////		   System.out.println(n.asMap().values());
////		   for(var val: n.asMap().values()) {
////		       var vall=(Relationship) val;
////		       vall.endNodeId();
////		       vall.startNodeId();
////		       vall.type();
////		       relations.putIfAbsent(vall.id(), new NeoModelEdgeAdapter(nodeToNeoNode.get(vall.endNodeId()), nodeToNeoNode.get(vall.startNodeId()), EdgeType.CORR,vall.type()));
////		       System.out.println("N0:"+ relations.get(vall.id()));
////		   } 
////		   
////		   });	       
////		
////	   
////	      
////	      
////	       });
////
////	}
//       relations.forEach((id,relation) -> graphBuilder.addEdge(relation));
////       match.getEdgeIDs().forEach((name,id) ->
////       
////       {var queRyResult=builder.executeQuery("MATCH (a)-[r]->(b) WHERE id(r)= 223 RETURN r");
////       queRyResult.list().forEach(n->{
////	   System.out.println(n.asMap().values());
////	   for(var val: n.asMap().values()) {
////	       var vall=(Relationship) val;
////	       vall.endNodeId();
////	       vall.startNodeId();
////	       vall.type();
////	       
////	   } 
////	   
////	   });
//////       relations.putIfAbsent(r.id(), new NeoModelEdgeAdapter(nodeToNeoNode.get(r.startNodeId()), nodeToNeoNode.get(r.endNodeId()), EdgeType.NORMAL, "edgeName"));
////	
////   
////      
////      
////       });
//
//	return graphBuilder.build();
//    }
//}

package org.emoflon.neo.victory.adapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.emoflon.ibex.tgg.ui.debug.api.Graph;
import org.emoflon.ibex.tgg.ui.debug.api.Match;
import org.emoflon.ibex.tgg.ui.debug.api.Rule;
import org.emoflon.ibex.tgg.ui.debug.api.enums.EdgeType;
import org.emoflon.ibex.tgg.ui.debug.api.impl.GraphBuilder;
import org.emoflon.neo.engine.api.patterns.IMatch;
import org.emoflon.neo.neo4j.adapter.models.NeoCoreBuilder;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;

public class NeoMatchAdapter implements Match {
    private IMatch match;
    private Collection<NeoRuleAdapter> rules;
    private NeoCoreBuilder builder;
    private Map<Integer, Graph> graphs = new HashMap<>();

    public NeoMatchAdapter(NeoCoreBuilder builder, IMatch match, Collection<NeoRuleAdapter> rules) {
	this.match = match;
	this.rules = rules;
	this.builder = builder;
    }

    public IMatch getWrappedMatch() {
	return match;
    }

    @Override
    public String getName() {
	return match.getPattern().getName();
    }

    @Override
    public boolean isBlocked() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public String getBlockingReason() {
	// TODO Auto-generated method stub
	return "";
    }

    @Override
    public Rule getRule() {
	return rules.stream().filter(r -> r.getName().equals(getName())).findAny().get();
    }

    @Override
    public Graph getGraph(int pNeighbourhoodSize) {
	if (!graphs.containsKey(pNeighbourhoodSize))
	    buildGraph(pNeighbourhoodSize);
	return graphs.get(pNeighbourhoodSize);
    }

    private void buildGraph(int neighbourhoodSize) {

	GraphBuilder graphBuilder = new GraphBuilder();
	Map<Long, NeoNodeAdapter> nodeToNeoNode = new HashMap<>();
	Map<Long, NeoModelEdgeAdapter> relations = new HashMap<>();

	if (!(match.getEdgeIDs().isEmpty() && match.getNodeIDs().isEmpty())) {

	    var result = builder.executeQuery(MatchQuery.create(match, getRule(), neighbourhoodSize));

	    var records = result.list();
	    for (var rec : records) {

		var map = rec.asMap();
		for (var o : map.values()) {
		    var path = (Path) o;

		    path.nodes().forEach(n -> nodeToNeoNode.putIfAbsent(n.id(), new NeoNodeAdapter(n)));

		    if (neighbourhoodSize > 0) {
			path.relationships().forEach(r -> {
			    if (r.hasType("corr")) {

				relations.putIfAbsent(r.id(),
					new NeoModelEdgeAdapter(nodeToNeoNode.get(r.startNodeId()),
						nodeToNeoNode.get(r.endNodeId()), EdgeType.CORR,
						r.asMap().values().toString()));
			    } else {
				relations.putIfAbsent(r.id(),
					new NeoModelEdgeAdapter(nodeToNeoNode.get(r.startNodeId()),
						nodeToNeoNode.get(r.endNodeId()), EdgeType.NORMAL, r.type()));
			    }
			});
		    }
		}
	    }

	    // populate edges when neighbourhoodSize=0
	    if (neighbourhoodSize == 0) {
		match.getEdgeIDs().forEach((name, id) ->

		{
		    var matchEdges = builder.executeQuery(MatchQuery.getMatchEdges(id));
		    matchEdges.list().forEach(n -> {
			for (var val : n.asMap().values()) {
			    var edge = (Relationship) val;

			    if (edge.hasType("corr")) {
				relations.putIfAbsent(edge.id(),
					new NeoModelEdgeAdapter(nodeToNeoNode.get(edge.endNodeId()),
						nodeToNeoNode.get(edge.startNodeId()), EdgeType.CORR,
						edge.asMap().values().toString()));
			    } else
				relations.putIfAbsent(edge.id(),
					new NeoModelEdgeAdapter(nodeToNeoNode.get(edge.endNodeId()),
						nodeToNeoNode.get(edge.startNodeId()), EdgeType.NORMAL, edge.type()));

			}
		    });

		});
	    }

	}

	nodeToNeoNode.forEach((Node, neoNode) -> graphBuilder.addNode(neoNode));
	relations.forEach((id, relation) -> graphBuilder.addEdge(relation));
	addMatchTypeEdge(graphBuilder, nodeToNeoNode);
	graphs.put(neighbourhoodSize, graphBuilder.build());

    }

    // Adding MATCH edges between rule and match nodes
    private void addMatchTypeEdge(GraphBuilder graphBuilder, Map<Long, NeoNodeAdapter> nodeToNeoNode) {
	match.getNodeIDs().forEach((name, id) -> {

	    var ruleNode = getRule().getGraph().getNodes().stream().filter(n -> n.getName().equals(name)).findFirst()
		    .get();
	    graphBuilder.addEdge(new NeoModelEdgeAdapter(ruleNode, nodeToNeoNode.get(id), EdgeType.MATCH, ""));

	});
    }

}
