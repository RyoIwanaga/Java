package jp.reu.util.lazy;
public abstract class LazyStream
{
	protected LazyStream nextStream;
	
	public LazyStream force()
	{
		if (this.nextStream == null) {
			this.nextStream = makeNextStream();
		}

		return nextStream;
	}
	
	abstract public LazyStream makeNextStream();
}
