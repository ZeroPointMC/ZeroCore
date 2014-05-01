package zeropoint.core.io;




/**
 * Classes implementing <code>IStreamIO</code> can be both read from and written to.
 * 
 * @author Zero Point
 */
public interface IStreamIO extends IReadable<Integer>, IWritable<Character> {}
