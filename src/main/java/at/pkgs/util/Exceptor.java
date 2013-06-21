package at.pkgs.util;

public class Exceptor <ExceptionType extends Exception> implements AutoCloseable {

	private ExceptionType throwable;

	public void add(ExceptionType throwable) {
		if (this.throwable == null) {
			this.throwable = throwable;
		}
		else {
			this.throwable.addSuppressed(throwable);
		}
	}

	@SuppressWarnings("unchecked")
	public void add(ExceptionType throwable, boolean unwrap) {
		if (unwrap) {
			this.add((ExceptionType)throwable.getCause());
		}
		else {
			this.add(throwable);
		}
	}

	public void finish() throws ExceptionType {
		if (this.throwable != null) throw this.throwable;
	}

	@Override
	public void close() throws ExceptionType {
		this.finish();
	}

	public static <ExceptionType extends Exception> Exceptor<ExceptionType> create() {
		return new Exceptor<ExceptionType>();
	}

}
