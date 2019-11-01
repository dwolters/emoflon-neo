/*
 * generated by Xtext 2.16.0
 */
package org.emoflon.neo.emsl.generator

import java.net.URI
import java.util.List
import java.util.stream.Collectors
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.FileLocator
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.preferences.InstanceScope
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.ui.preferences.ScopedPreferenceStore
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.emoflon.neo.emsl.compiler.TGGCompiler
import org.emoflon.neo.emsl.eMSL.ActionOperator
import org.emoflon.neo.emsl.eMSL.AtomicPattern
import org.emoflon.neo.emsl.eMSL.Constraint
import org.emoflon.neo.emsl.eMSL.EMSL_Spec
import org.emoflon.neo.emsl.eMSL.Entity
import org.emoflon.neo.emsl.eMSL.GraphGrammar
import org.emoflon.neo.emsl.eMSL.Metamodel
import org.emoflon.neo.emsl.eMSL.MetamodelNodeBlock
import org.emoflon.neo.emsl.eMSL.Model
import org.emoflon.neo.emsl.eMSL.ModelNodeBlock
import org.emoflon.neo.emsl.eMSL.ModelRelationStatement
import org.emoflon.neo.emsl.eMSL.Pattern
import org.emoflon.neo.emsl.eMSL.Rule
import org.emoflon.neo.emsl.eMSL.TripleGrammar
import org.emoflon.neo.emsl.refinement.EMSLFlattener
import org.emoflon.neo.emsl.util.ClasspathUtil
import org.emoflon.neo.emsl.util.EMSLUtil
import org.eclipse.core.runtime.NullProgressMonitor
import org.emoflon.neo.emsl.eMSL.TripleRule
import java.util.function.Predicate

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class EMSLGenerator extends AbstractGenerator {
	public static final String TGG_GEN_FOLDER = "tgg-gen"
	public static final String SRC_GEN_Folder = "src-gen"
	public static final String API_ROOT = "org/emoflon/neo/api/"
	public EMSL_Spec emslSpec;

	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		if(resource.contents.isEmpty) return

		val apiPath = getAPIPath(resource)
		val apiName = getAPIName(resource)
		val apiFile = getAPIFileName(resource)

		emslSpec = resource.contents.get(0) as EMSL_Spec
		emslSpec.entities.filter[it instanceof TripleGrammar].map[it as TripleGrammar].forEach [
			new TGGCompiler(it, apiPath + "/" + apiName).compileAll(fsa)
		]

		fsa.generateFile(API_ROOT + "API_Common.java", generateCommon())
		fsa.generateFile(API_ROOT + apiPath + "/" + apiFile + ".java",
			generateAPIFor(apiFile, apiPath, emslSpec, resource))
	}

	def getAPIName(Resource resource) {
		val segments = resource.URI.trimFileExtension.segmentsList
		return segments.last
	}

	def getAPIFileName(Resource resource) {
		return "API_" + getAPIName(resource)
	}

	def getAPIPath(Resource resource) {
		val segments = resource.URI.trimFileExtension.segmentsList

		// Always remove:  resource/projectName
		var prefixSegments = 2
		// If the msl file is nested any deeper, also remove first folder (typically src)
		if (segments.size > 3)
			prefixSegments++

		val apiPath = segments //
		.drop(prefixSegments).take(segments.size - (prefixSegments + 1)) // only take path up to EMSL file
		.join("/");

		return apiPath
	}

	override void afterGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val segments = resource.URI.trimFileExtension.segmentsList
		val projectName = segments.get(1)

		val project = ResourcesPlugin.workspace.root.getProject(projectName)
		ClasspathUtil.setUpAsJavaProject(project)
		ClasspathUtil.setUpAsPluginProject(project)
		ClasspathUtil.setUpAsXtextProject(project)
		ClasspathUtil.addDependencies(project, List.of("org.emoflon.neo.neo4j.adapter"))
		ClasspathUtil.makeSourceFolderIfNecessary(project.getFolder(SRC_GEN_Folder))
		ClasspathUtil.makeSourceFolderIfNecessary(project.getFolder(TGG_GEN_FOLDER))

		if (project.getFolder(TGG_GEN_FOLDER).exists)
			project.getFolder(TGG_GEN_FOLDER).touch(new NullProgressMonitor)
	}

	def generateCommon() {
		val store = new ScopedPreferenceStore(InstanceScope.INSTANCE, EMSLUtil.UI_PLUGIN_ID)

		val uri = store.getString(EMSLUtil.P_URI);
		val userName = store.getString(EMSLUtil.P_USER);
		val password = store.getString(EMSLUtil.P_PASSWORD);

		'''
			/** 
			 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
			 */
			package org.emoflon.neo.api;			
			import org.emoflon.neo.cypher.models.NeoCoreBuilder;
			
			public class API_Common {
				// Default values (might have to be changed)
				public static final String PLATFORM_PLUGIN_URI = "«getInstallLocation»";
				public static final String PLATFORM_RESOURCE_URI = "../";
			
				public static NeoCoreBuilder createBuilder() {
					return new NeoCoreBuilder("«uri»", "«userName»", "«password»");
				}
			}
		'''
	}

	private def getInstallLocation() {
		val plugin = Platform.getBundle("org.emoflon.neo.neocore");
		val fileURL = FileLocator.resolve(plugin.getEntry("/")).toString
		val fileURI = new URI(fileURL.replace(" ", "%20")).normalize
		val segments = fileURI.path.split("/")
		val path = segments.take(segments.length - 1)
		path.join("/") + "/"
	}

	def generateAPIFor(String apiName, String apiPath, EMSL_Spec spec, Resource resource) {
		'''
			/** 
			 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
			 */
			package org.emoflon.neo.api«subPackagePath(apiPath)»;
			
			import org.emoflon.neo.cypher.models.NeoCoreBuilder;
			import org.emoflon.neo.neo4j.adapter.patterns.NeoMatch;
			import org.emoflon.neo.neo4j.adapter.rules.NeoCoMatch;
			import org.emoflon.neo.emsl.eMSL.EMSL_Spec;
			import org.emoflon.neo.emsl.eMSL.Model;
			import org.emoflon.neo.emsl.eMSL.Metamodel;
			import org.emoflon.neo.emsl.util.EMSLUtil;
			import org.emoflon.neo.emsl.util.FlattenerException;
			import org.emoflon.neo.engine.api.patterns.IPattern;
			import org.emoflon.neo.engine.api.rules.IRule;
			import org.emoflon.neo.neo4j.adapter.rules.NeoRule;
			import org.emoflon.neo.neo4j.adapter.rules.NeoRuleAccess;
			import org.emoflon.neo.neo4j.adapter.patterns.NeoPattern;
			import org.emoflon.neo.neo4j.adapter.patterns.NeoPatternFactory;
			import org.emoflon.neo.emsl.eMSL.Pattern;
			import org.emoflon.neo.emsl.eMSL.Rule;
			import org.emoflon.neo.emsl.eMSL.TripleRule;
			import org.emoflon.neo.neo4j.adapter.rules.NeoRuleFactory;
			import org.emoflon.neo.neo4j.adapter.constraints.NeoConstraint;
			import org.emoflon.neo.neo4j.adapter.constraints.NeoConstraintFactory;
			import org.emoflon.neo.engine.api.constraints.IConstraint;
			import org.emoflon.neo.emsl.eMSL.Constraint;
			import org.neo4j.driver.v1.Value;
			import org.neo4j.driver.v1.Record;
			import org.emoflon.neo.neo4j.adapter.patterns.NeoPatternAccess;
			import org.emoflon.neo.neo4j.adapter.patterns.NeoMask;
			import org.emoflon.neo.neo4j.adapter.patterns.NeoData;
			import org.emoflon.neo.neo4j.adapter.rules.NeoRuleCoAccess;
			import org.emoflon.neo.api.API_Common;
			import java.util.Collection;
			import java.util.HashSet;
			import java.util.HashMap;
			import java.util.Map;
			import java.util.stream.Stream;
			import java.util.Optional;
			import java.time.LocalDate;
			
			@SuppressWarnings("unused")
			public class «apiName» {
				private EMSL_Spec spec;
				private NeoCoreBuilder builder;
			
				/** Use this constructor for default values */
				public «apiName»(NeoCoreBuilder builder) {
					this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI);
				}
			
				/** Use this constructor to configure values for loading EMSL files */
				public «apiName»(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot){
					spec = (EMSL_Spec) EMSLUtil.loadSpecification("«resource.URI»", platformResourceURIRoot, platformPluginURIRoot);
					this.builder = builder;
				}
			
				«FOR e : spec.entities SEPARATOR "\n"»
					//:~> «resource.URI»#«resource.getURIFragment(e)»
					«generateAccess(e, spec.entities.indexOf(e))»
				«ENDFOR»
			}
		'''
	}

	def subPackagePath(String apiPath) {
		if (apiPath.length > 0)
			"." + apiPath.replace("/", ".")
		else
			""
	}

	dispatch def generateAccess(Entity e, int index) {
		''''''
	}

	dispatch def generateAccess(Pattern p, int index) {
		if(p.body.abstract) return ""
		try {
			val patternBody = EMSLFlattener.flatten(p.body) as AtomicPattern
			val rootName = namingConvention(patternBody.name)
			val dataClassName = rootName + "Data"
			val accessClassName = rootName + "Access"
			val maskClassName = rootName + "Mask"
			'''
				public «accessClassName» getPattern_«rootName»() {
					return new «accessClassName»();
				}
				
				public class «accessClassName» extends NeoPatternAccess<«dataClassName»,«maskClassName»> {
					«FOR node : patternBody.nodeBlocks»
						public final String «node.name» = "«node.name»";
					«ENDFOR»
					
					@Override
					public NeoPattern matcher(){
						var p = (Pattern) spec.getEntities().get(«index»);
						return NeoPatternFactory.createNeoPattern(p, builder);
					}
					
					@Override
					public NeoPattern matcher(«maskClassName» mask) {
						var p = (Pattern) spec.getEntities().get(«index»);
						return NeoPatternFactory.createNeoPattern(p, builder, mask);
					}
					
					@Override
					public Stream<«dataClassName»> data(Collection<NeoMatch> matches) {
						var data = NeoMatch.getData(matches);
						return data.stream().map(d -> new «dataClassName»(d));
					}
					
					@Override
					public «maskClassName» mask() {
						return new «maskClassName»();
					}
				}
				
				public class «dataClassName» extends NeoData {
					«classMembers(patternBody.nodeBlocks)»
					
					«constructor(dataClassName, patternBody.nodeBlocks)»
					
					«helperClasses(patternBody.nodeBlocks)»
				}
				
				public class «maskClassName» extends NeoMask {
				
					«maskClassMembers()»
				
					«maskMethods(patternBody.nodeBlocks, maskClassName)»
				
				}
			'''
		} catch (Exception e) {
			e.printStackTrace
			'''//FIXME Unable to generate API: «e.toString»  */ '''
		}
	}

	private def CharSequence maskClassMembers() '''
		private HashMap<String, Long> nodeMask = new HashMap<>();
		private HashMap<String, Object> attributeMask = new HashMap<>();
		
		@Override
		public Map<String, Long> getMaskedNodes() {
			return nodeMask;
		}
		
		@Override
		public Map<String, Object> getMaskedAttributes() {
			return attributeMask;
		}
		
	'''

	protected def CharSequence helperClasses(Iterable<ModelNodeBlock> nodeBlocks) {
		helperClasses(nodeBlocks, [true], [true])
	}

	protected def CharSequence helperClasses(Iterable<ModelNodeBlock> nodeBlocks, Predicate<ModelNodeBlock> nodeFilter,
		Predicate<ModelRelationStatement> edgeFilter) '''
		«FOR node : nodeBlocks.filter(nodeFilter)»
			«helperNodeClass(node)»
			
			«FOR rel : node.relations.filter(edgeFilter).filter[!EMSLUtil.isVariableLink(it)]»
				«helperRelClass(node, rel)»
			«ENDFOR»
		«ENDFOR»
	'''

	protected def CharSequence helperRelClass(ModelNodeBlock node, ModelRelationStatement rel) {
		val relName = EMSLUtil.relationNameConvention(node.name, rel.allTypes, rel.target.name,
			node.relations.indexOf(rel))
		'''
			public class «relName.toFirstUpper»Rel {
				«FOR prop : rel.types.flatMap[it.type.properties]»
					public «EMSLUtil.getJavaType(prop.type)» «prop.name»;
				«ENDFOR»
			
				public «relName.toFirstUpper»Rel(Value «relName») {
					«FOR prop : rel.types.flatMap[it.type.properties]»
						if(!«relName».get("«prop.name»").isNull())
							this.«prop.name» = «relName».get("«prop.name»").as«EMSLUtil.getJavaType(prop.type).toFirstUpper»();
					«ENDFOR»
				}
			}
		'''
	}

	def getAllTypes(ModelRelationStatement rel) {
		EMSLUtil.getAllTypes(rel)
	}

	protected def CharSequence helperNodeClass(ModelNodeBlock node) '''
		public class «node.name.toFirstUpper»Node {
			«FOR prop : allProperties(node.type)»
				public «EMSLUtil.getJavaType(prop.type)» «prop.name»;
			«ENDFOR»
			
			public «node.name.toFirstUpper»Node(Value «node.name») {
				«FOR prop : allProperties(node.type)»
					if(!«node.name».get("«prop.name»").isNull())
						this.«prop.name» = «node.name».get("«prop.name»").as«EMSLUtil.getJavaType(prop.type).toFirstUpper»();
				«ENDFOR»
			}
		}
	'''

	protected def CharSequence constructor(String fileName, Iterable<ModelNodeBlock> nodeBlocks) {
		constructor(fileName, nodeBlocks, [true], [true])
	}

	protected def CharSequence constructor(String fileName, Iterable<ModelNodeBlock> nodeBlocks,
		Predicate<ModelNodeBlock> nodeFilter, Predicate<ModelRelationStatement> edgeFilter) '''
		public «fileName»(Record data) {
			«FOR node : nodeBlocks.filter(nodeFilter)»
				var «node.name» = data.get("«node.name»");
				this.«node.name» = new «node.name.toFirstUpper»Node(«node.name»);
				«FOR rel : node.relations.filter(edgeFilter).filter[!EMSLUtil.isVariableLink(it)]»
					«val relName = EMSLUtil.relationNameConvention(//
						node.name,// 
						rel.allTypes,//
						rel.target.name,// 
						node.relations.indexOf(rel))»
					var «relName» = data.get("«relName»");
					this.«relName» = new «relName.toFirstUpper»Rel(«relName»);
				«ENDFOR»			
			«ENDFOR»
		}
		
	'''

	def CharSequence classMembers(Iterable<ModelNodeBlock> nodeBlocks) {
		classMembers(nodeBlocks, [true], [true])
	}

	def CharSequence classMembers(Iterable<ModelNodeBlock> nodeBlocks, Predicate<ModelNodeBlock> nodeFilter,
		Predicate<ModelRelationStatement> edgeFilter) {
		'''
			«FOR node : nodeBlocks.filter(nodeFilter)»
				public final «node.name.toFirstUpper»Node «node.name»;
				«FOR rel : node.relations.filter(edgeFilter).filter[!EMSLUtil.isVariableLink(it)]»
					«val relName = EMSLUtil.relationNameConvention(//
						node.name,// 
						rel.allTypes,//
						rel.target.name,// 
						node.relations.indexOf(rel))»
					public final «relName.toFirstUpper»Rel «relName»;
				«ENDFOR»
			«ENDFOR»
		'''
	}

	def CharSequence maskMethods(Iterable<ModelNodeBlock> nodeBlocks, String maskClassName) {
		'''
			«FOR node : nodeBlocks»
				public «maskClassName» set«node.name.toFirstUpper»(Long value) {
					nodeMask.put("«node.name»", value);
					return this;
				}
				«FOR prop : allProperties(node.type)»
					public «maskClassName» set«node.name.toFirstUpper»«prop.name.toFirstUpper»(«EMSLUtil.getJavaType(prop.type)» value) {
						attributeMask.put("«node.name».«prop.name»", value);
						return this;
					}
				«ENDFOR»
				«FOR rel : node.relations.filter[!EMSLUtil.isVariableLink(it)]»
					«FOR prop : rel.types.flatMap[it.type.properties]»
						«val relName = EMSLUtil.relationNameConvention(//
											node.name,// 
											rel.allTypes,//
											rel.target.name,// 
											node.relations.indexOf(rel))»
						public «maskClassName» set«relName.toFirstUpper»«prop.name.toFirstUpper»(«EMSLUtil.getJavaType(prop.type)» value) {
							attributeMask.put("«relName».«prop.name»", value);
							return this;
						}
					«ENDFOR»
				«ENDFOR»
			«ENDFOR»
		'''
	}

	def allProperties(MetamodelNodeBlock nb) {
		EMSLUtil.thisAndAllSuperTypes(nb).flatMap[it.properties]
	}

	dispatch def generateAccess(GraphGrammar gg, int index) {
		if(gg.abstract) return ""
		try {
			val ruleMethods = gg.rules.stream.map["getRule_" + namingConvention(it.name) + "().rule()"].collect(
				Collectors.toSet)
			'''
				public Collection<NeoRule> getAllRulesFor«namingConvention(gg.name)»() {
					Collection<NeoRule> rules = new HashSet<>();
					«FOR access : ruleMethods»
						rules.add(«access»);
					«ENDFOR»
					return rules;
				}
				
				public Collection<Rule> getAllEMSLRulesFor«namingConvention(gg.name)»(){
					var rules = new HashSet<Rule>();
					«FOR r : gg.rules»
						rules.add((Rule) spec.getEntities().get(«emslSpec.entities.indexOf(r)»));
					«ENDFOR»
					return rules;
				}
			'''
		} catch (Exception e) {
			e.printStackTrace
			'''//FIXME Unable to generate API: «e.toString»  */ '''
		}
	}

	dispatch def generateAccess(TripleGrammar tgg, int index) {
		if(tgg.abstract) return ""
		try {
			val rootName = namingConvention(tgg.name)
			'''
				public void exportMetamodelsFor«rootName»() throws FlattenerException {
					«FOR mm : tgg.srcMetamodels + tgg.trgMetamodels»
						«val apiPath = API_ROOT + getAPIPath(mm.eResource) + "/" + getAPIFileName(mm.eResource)»
						«val apiFQN = apiPath.replace("/", ".").replace("..", ".")»
						«val mmName = namingConvention(mm.name)»
						{
							var api = new «apiFQN»(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI);
							builder.exportEMSLEntityToNeo4j(api.getMetamodel_«mmName»());
						}
					«ENDFOR»
				}
				
				public Collection<TripleRule> getTripleRulesOf«rootName»(){
					var rules = new HashSet<TripleRule>();
					«FOR tr : tgg.rules»
						rules.add((TripleRule) spec.getEntities().get(«emslSpec.entities.indexOf(tr)»));
					«ENDFOR»
					return rules;
				}
			'''
		} catch (Exception e) {
			e.printStackTrace
			'''//FIXME Unable to generate API: «e.toString»  */ '''
		}
	}

	dispatch def generateAccess(TripleRule tr, int index) {
		if (tr.abstract)
			return ""
		else
			'''
				public static final String «tr.type.name»__«tr.name» = "«tr.name»";
				«FOR node : tr.srcNodeBlocks+tr.trgNodeBlocks»
					public static final String «tr.type.name»__«tr.name»__«node.name» = "«node.name»";
				«ENDFOR»
			'''
	}

	dispatch def generateAccess(Rule r, int index) {
		if(r.abstract) return ""
		try {
			val rule = EMSLFlattener.flatten(r) as Rule;
			val rootName = namingConvention(rule.name)
			val dataClassName = rootName + "Data"
			val codataClassName = rootName + "CoData"
			val accessClassName = rootName + "Access"
			val maskClassName = rootName + "Mask"
			'''
				public «accessClassName» getRule_«rootName»() {
					return new «accessClassName»();
				}
				
				public class «accessClassName» extends NeoRuleCoAccess<«dataClassName», «codataClassName», «maskClassName»> {
					«FOR node : rule.nodeBlocks»
						public final String «node.name» = "«node.name»";
					«ENDFOR»
					
					@Override
					public NeoRule rule(){
						var r = (Rule) spec.getEntities().get(«index»);
						return NeoRuleFactory.createNeoRule(r, builder);
					}
					
					@Override
					public NeoRule rule(«maskClassName» mask) {
						var r = (Rule) spec.getEntities().get(«index»);
						return NeoRuleFactory.createNeoRule(r, builder, mask);
					}
					
					@Override
					public Stream<«dataClassName»> data(Collection<NeoMatch> matches) {
						var data = NeoMatch.getData(matches);
						return data.stream().map(d -> new «dataClassName»(d));
					}
						
					@Override
					public Stream<«codataClassName»> codata(Collection<NeoCoMatch> matches) {
						var data = NeoMatch.getData(matches);
						return data.stream().map(d -> new «codataClassName»(d));
					}
					
					@Override
					public «maskClassName» mask() {
						return new «maskClassName»();
					}
				}
				
				public class «dataClassName» extends NeoData {
					«val blackAndRedNodes = [ModelNodeBlock n | n.action === null || n.action.op == ActionOperator.DELETE]»
					«val blackAndRedEdges = [ModelRelationStatement e | e.action === null || e.action.op == ActionOperator.DELETE]»
					«classMembers(rule.nodeBlocks, blackAndRedNodes, blackAndRedEdges)»
					
					«constructor(dataClassName, rule.nodeBlocks, blackAndRedNodes, blackAndRedEdges)»
					
					«helperClasses(rule.nodeBlocks, blackAndRedNodes, blackAndRedEdges)»
				}
				
				public class «codataClassName» extends NeoData {
					«val blackAndGreenNodes = [ModelNodeBlock n | n.action === null || n.action.op == ActionOperator.CREATE]»
					«val blackAndGreenEdges = [ModelRelationStatement e | e.action === null || e.action.op == ActionOperator.CREATE]»
					«classMembers(rule.nodeBlocks, blackAndGreenNodes, blackAndGreenEdges)»
				
					«constructor(codataClassName, rule.nodeBlocks, blackAndGreenNodes, blackAndGreenEdges)»
				
					«helperClasses(rule.nodeBlocks, blackAndGreenNodes, blackAndGreenEdges)»
				}
				
				public class «maskClassName» extends NeoMask {
					«maskClassMembers()»
					
					«maskMethods(rule.nodeBlocks, maskClassName)»
				}
			'''
		} catch (Exception e) {
			e.printStackTrace
			'''//FIXME Unable to generate API: «e.toString»  */ '''
		}
	}

	dispatch def generateAccess(Model m, int index) {
		if(m.abstract) return "// No API for abstract models"
		'''
			public Model getModel_«namingConvention(m.name)»(){
				return (Model) spec.getEntities().get(«index»);
			}
		'''
	}

	dispatch def generateAccess(Metamodel m, int index) {
		'''
			public Metamodel getMetamodel_«namingConvention(m.name)»(){
				return (Metamodel) spec.getEntities().get(«index»);
			}
			
			«FOR type : m.nodeBlocks»
				public static final String «m.name»__«type.name» = "«m.name»__«type.name»";
			«ENDFOR»
		'''
	}

	dispatch def generateAccess(Constraint c, int index) {
		'''
			public IConstraint getConstraint_«namingConvention(c.name)»() {
				var c = (Constraint) spec.getEntities().get(«index»);
				return NeoConstraintFactory.createNeoConstraint(c, builder);
			}
		'''
	}

	def String namingConvention(String name) {
		name.toFirstUpper.replace(".", "_")
	}
}
