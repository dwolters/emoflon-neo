package org.emoflon.neo.neo4j.adapter;

public abstract class NeoAccess {
	public abstract NeoPattern matcher();

	public abstract NeoPattern matcher(NeoMask mask);
}
