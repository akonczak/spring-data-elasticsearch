package org.springframework.data.elasticsearch.core.query;

/**
 * @author Ryan Murfitt
 * @author Artur Konczak
 */
public class ScriptField {

	private final String fieldName;
	private final String type;
	private final String script;

	public ScriptField(String fieldName, String type, String script) {
		this.fieldName = fieldName;
		this.type = type;
		this.script = script;
	}

	public String fieldName() {
		return fieldName;
	}

	public String script() {
		return script;
	}

	public String getType() {
		return type;
	}
}
