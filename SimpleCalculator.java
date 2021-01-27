package SimpleCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator {

	JFrame frame;
	GridBagLayout layout;
	GridBagConstraints constraints;
	JTextField textField;
	String expression;

	private SimpleCalculator() {

		frame = new JFrame();
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		textField = new JTextField("");
		frame.setLayout(layout);
		frame.setTitle("Simple Calculator");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(textField, constraints);
		frame.add(textField);
		constraints.gridwidth = 1;

		ActionListener insert = new InsertAction();
		ActionListener command = new CommandAction();

		addButton("CE", command);
		addButton("del", command);
		addButton("(", insert);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		addButton(")", insert);
		constraints.gridwidth = 1;
		addButton("1", insert);
		addButton("2", insert);
		addButton("3", insert);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		addButton("+", insert);
		constraints.gridwidth = 1;
		addButton("4", insert);
		addButton("5", insert);
		addButton("6", insert);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		addButton("-", insert);
		constraints.gridwidth = 1;
		addButton("7", insert);
		addButton("8", insert);
		addButton("9", insert);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		addButton("*", insert);
		constraints.gridwidth = 1;
		addButton("0", insert);
		addButton(".", insert);
		addButton("=", command);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		addButton("/", insert);

		frame.setBounds(300, 300, 300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class InsertAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			textField.setText(textField.getText() + e.getActionCommand());
		}
	}

	private class CommandAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("CE")) {
				textField.setText("");
			} else if (e.getActionCommand().equals("del")) {
				textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
			} else {
				expression = textField.getText();
				textField.setText(Double.toString(calculate(expression)));
			}
		}
	}

	private static double calculate(String expression) {
		
		Stack numberStack = new Stack();
		Stack commandStack = new Stack();
		
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			if (Character.isDigit(c) || c == '.') {
				StringBuilder sb = new StringBuilder();
				while (Character.isDigit(c) || c == '.') {
					sb.append(c);
					i++;
					if (i >= expression.length()) {
						break;
					}
					c = expression.charAt(i);
				}
				numberStack.push(sb.toString());
				i--;
			} else {
				if (commandStack.isEmpty()) {
					commandStack.push(c);
					continue;
				}
				while (!commandStack.isEmpty()) {
					char d = (char) commandStack.peek();
					if (compareOperator(d, c) < 0) {
						commandStack.push(c);
						break;
					} else if (compareOperator(d, c) == 0) {
						commandStack.pop();
						break;
					} else {
						double num1 = Double.parseDouble(numberStack.pop().toString());
						double num2 = Double.parseDouble(numberStack.pop().toString());
						char command = (char) commandStack.pop();
						double result = getResult(command, num2, num1);
						numberStack.push(result);
						if (commandStack.isEmpty()) {
							commandStack.push(c);
							break;
						}
					}
				}
			}
		}

		while (!commandStack.isEmpty()) {
			char command = (char) commandStack.pop();
			double num1 = Double.parseDouble(numberStack.pop().toString());
			double num2 = Double.parseDouble(numberStack.pop().toString());
			double result = getResult(command, num2, num1);
			numberStack.push(result);
		}
		
		return Double.parseDouble(numberStack.pop().toString());
	}

	private static double getResult(char command, double num1, double num2) {
		switch (command) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		case '/':
			return num1 / num2;
		}
		return 0;
	}

	private static int compareOperator(char c, char d) {
		if (c == '+' || c == '-') {
			if (d == '*' || d == '/' || d == '(') {
				return -1;
			}
		}
		if (c == '*' || c == '/') {
			if (d == '(') {
				return -1;
			}
		}
		if (c == '(') {
			if (d == ')') {
				return 0;
			} else {
				return -1;
			}
		}
		return 1;
	}

	private void addButton(String title, ActionListener listener) {
		JButton button = new JButton(title);
		layout.setConstraints(button, constraints);
		button.addActionListener(listener);
		frame.add(button);
	}

	public static void main(String[] args) {
		new SimpleCalculator();
	}
}