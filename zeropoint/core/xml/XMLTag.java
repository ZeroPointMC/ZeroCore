// Apr 30, 2014 10:20:24 AM
package zeropoint.core.xml;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("javadoc")
public class XMLTag implements Map<String, String> {
	protected final String tag;
	protected final List<XMLTag> inner = new ArrayList<XMLTag>();
	protected String cdata = "";
	protected final XMLAttrs attrs = new XMLAttrs();
	public static boolean validate(String tag) {
		if (tag == null) {
			return false;
		}
		return tag.toLowerCase().matches("\\w[-\\w\\d.]*") && !tag.toLowerCase().matches(".*?[\\s:~/\\\\;?$&%@^=*+()|'\"`{}\\[\\]<>].*");
	}
	public XMLTag(String name) {
		if ( !validate(name)) {
			throw new IllegalArgumentException("Invalid XML tag");
		}
		this.tag = name;
	}
	public XMLTag(String name, Map<String, String> attrList) {
		this(name);
		this.attrs.add(attrList);
	}
	public String tag() {
		return this.tag;
	}
	public String cdata() {
		return this.cdata;
	}
	public void cdata(Object text) {
		this.cdata = String.valueOf(text);
	}
	public XMLAttrs attrs() {
		return this.attrs;
	}
	public void attr(String key, String val) {
		this.attrs.attr(key, val);
	}
	public String attr(String key) {
		return this.attrs.attr(key);
	}
	@Override
	public void clear() {
		this.attrs.clear();
	}
	@Override
	public boolean containsKey(Object testKey) {
		return this.attrs.containsKey(testKey);
	}
	@Override
	public boolean containsValue(Object testVal) {
		return this.attrs.containsValue(testVal);
	}
	@Override
	public Set<Map.Entry<String, String>> entrySet() {
		return this.attrs.entrySet();
	}
	@Override
	public String get(Object key) {
		if (key.equals("~")) {
			return this.tag();
		}
		return this.attrs.get(key);
	}
	@Override
	public boolean isEmpty() {
		return this.attrs.isEmpty();
	}
	@Override
	public Set<String> keySet() {
		return this.attrs.keySet();
	}
	@Override
	public String put(String key, String val) {
		return this.attrs.put(key, val);
	}
	@Override
	public void putAll(Map<? extends String, ? extends String> attrList) {
		this.attrs.putAll(attrList);
	}
	@Override
	public String remove(Object key) {
		return this.attrs.remove(key);
	}
	@Override
	public int size() {
		return this.attrs.size();
	}
	@Override
	public Collection<String> values() {
		return this.attrs.values();
	}
	public String openTag() {
		return "<" + this.tag() + this.attrs.toXML() + ">";
	}
	public String closeTag() {
		return "</" + this.tag() + ">";
	}
	public String selfClosingTag() {
		return "<" + this.tag() + this.attrs.toXML() + " />";
	}
	@Override
	public String toString() {
		if (this.cdata().isEmpty() && this.inner.isEmpty()) {
			return this.selfClosingTag();
		}
		StringBuffer b = new StringBuffer();
		b.append(this.openTag() + this.cdata());
		for (XMLTag internal : this.inner) {
			b.append(internal);
		}
		b.append(this.closeTag());
		return b.toString();
	}
	public String toString(Object innerContent) {
		String in = String.valueOf(innerContent);
		if (in.isEmpty()) {
			return this.selfClosingTag();
		}
		return this.openTag() + in + this.closeTag();
	}
	public void addInner(XMLTag node) {
		this.inner.add(node);
	}
}
