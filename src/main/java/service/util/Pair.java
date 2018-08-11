package service.util;

public class Pair {
	
	private final String left;
	private final String right;

	public Pair(String left, String right) {
		this.left = left;
		this.right = right;
	}

	public String getLeft() {
		return left;
	}

	public String getRight() {
		return right;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        return this.left.equals(pair.left) && this.right.equals(pair.right);
    }
}