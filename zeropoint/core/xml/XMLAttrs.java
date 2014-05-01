// Apr 30, 2014 10:21:15 AM
package zeropoint.core.xml;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("javadoc")
public class XMLAttrs implements Map<String, String> {
	protected final HashMap<String, String> attrs;
	public XMLAttrs() {
		this.attrs = new HashMap<String, String>();
	}
	public XMLAttrs(Map<? extends String, ? extends String> init) {
		this();
		this.add(init);
	}
	public XMLAttrs(Object key, Object val) {
		this();
		this.attr(key, val);
	}
	public boolean has(Object testKey) {
		return this.containsKey(String.valueOf(testKey));
	}
	public String attr(Object key) {
		return this.attr(key, true);
	}
	public String attr(Object key, boolean clean) {
		if (clean) {
			return this.containsKey(String.valueOf(key)) ? this.attrs.get(String.valueOf(key)).replaceAll("&quot;", "\"").replaceAll("&amp;", "&") : null;
		}
		return this.containsKey(String.valueOf(key)) ? this.attrs.get(String.valueOf(key)) : null;
	}
	public String attr(Object key, Object val) {
		return this.put(String.valueOf(key), String.valueOf(val));
	}
	public String delete(Object key) {
		return this.remove(key);
	}
	public void add(Map<? extends String, ? extends String> vals) {
		this.putAll(vals);
	}
	@Override
	public void clear() {
		this.attrs.clear();
	}
	@Override
	public boolean isEmpty() {
		return this.attrs.isEmpty();
	}
	public String[] keys() {
		return this.attrs.keySet().toArray(new String[] {});
	}
	@Override
	public Set<String> keySet() {
		Set<String> s = Collections.synchronizedSet(new HashSet<String>());
		s.addAll(this.attrs.keySet());
		return s;
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
		Set<Map.Entry<String, String>> s = Collections.synchronizedSet(new HashSet<Map.Entry<String, String>>());
		s.addAll(this.attrs.entrySet());
		return s;
	}
	@Override
	public String get(Object key) {
		return this.attr(key);
	}
	@Override
	public String put(String key, String val) {
		key = String.valueOf(key);
		val = String.valueOf(val);
		return this.attrs.put(key.replaceAll("(^[^a-zA-Z_]|[^a-zA-Z0-9_-]+)", ""), val.replaceAll("&", "&amp;").replaceAll("\"", "&quot;"));
	}
	@Override
	public void putAll(Map<? extends String, ? extends String> vals) {
		for (String key : vals.keySet()) {
			key = String.valueOf(key);
			this.attr(key, String.valueOf(vals.get(key)));
		}
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
	@Override
	public String toString() {
		return this.toXML().substring(1);
	}
	public String toXML() {
		StringBuffer b = new StringBuffer();
		for (String key : this.keys()) {
			b.append(" " + key + "=\"" + this.attr(key, false) + "\"");
		}
		return b.toString();
	}
}
