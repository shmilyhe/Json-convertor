package io.shmilyhe.convert.tools;

import java.util.Stack;

public class PostfixExpression {
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static int getPriority(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    public static String convertToPostfix(String infixExpression) {
        StringBuilder postfixExpression = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < infixExpression.length(); i++) {
            char c = infixExpression.charAt(i);

            if (Character.isDigit(c)) {
                postfixExpression.append(c);

                // 如果下一个字符不是数字，则添加一个空格作为分隔符
                if (i < infixExpression.length() - 1 && !Character.isDigit(infixExpression.charAt(i + 1))) {
                    postfixExpression.append(" ");
                }
            } else if (isOperator(c)) {
                while (!operatorStack.empty() && isOperator(operatorStack.peek()) &&
                        getPriority(operatorStack.peek()) >= getPriority(c)) {
                    postfixExpression.append(operatorStack.pop());
                    postfixExpression.append(" ");
                }
                operatorStack.push(c);
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.empty() && operatorStack.peek() != '(') {
                    postfixExpression.append(operatorStack.pop());
                    postfixExpression.append(" ");
                }
                operatorStack.pop();
            }
        }

        while (!operatorStack.empty()) {
            postfixExpression.append(operatorStack.pop());
            postfixExpression.append(" ");
        }

        return postfixExpression.toString();
    }

    public static void main(String[] args) {
        String infixExpression = "3      +49*2/(1-5)";
        String postfixExpression = convertToPostfix(infixExpression);
        System.out.println("Infix Expression: " + infixExpression);
        System.out.println("Postfix Expression: " + postfixExpression);
    }
}
