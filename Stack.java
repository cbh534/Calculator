package SimpleCalculator;

public class Stack {
	private Object[] elements;
	private int capacity = 100;
	private int top;

	public Stack() {
		elements = new Object[capacity];
	}

	public int getSize() {
		return top;
	}

	public boolean isEmpty() {
		return top <= 0;
	}

	public void push(Object e) {
		elements[top] = e;
		top++;
	}

	public Object pop() {
		if (top <= 0) {
			throw new StackOverflowError("Stack is empty");
		}
		top--;
		return elements[top];
	}

	public Object peek() {
		return elements[top - 1];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = top - 1; i >= 0; i--) {
			sb.append(elements[i]);
		}
		return sb.toString();
	}
}
