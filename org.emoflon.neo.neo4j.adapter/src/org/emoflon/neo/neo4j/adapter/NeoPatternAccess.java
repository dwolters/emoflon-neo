package org.emoflon.neo.neo4j.adapter;

public abstract class NeoPatternAccess<Data extends NeoData, Mask extends NeoMask> {
	public abstract NeoPattern matcher();

	public abstract NeoPattern matcher(Mask mask);

	public abstract Mask mask();

	public abstract Data data(NeoMatch m);
}
