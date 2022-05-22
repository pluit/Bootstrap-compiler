package io.github.jadefalke2.lexer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

public class Lexer {

	private enum TokenType {
		EOF,
		STRING,
		SYMBOL,
		NUMBER
	}

	private int n;
	private String input;

	private int lastChar;
	private StringBuffer buffer;

	public Lexer () {

	}

	public Queue<Token> tokenize (String input) {
		this.input = input;
		this.n = 0;

		Queue<Token> acc = new LinkedList<>();
		TokenType type = readToken();

		do {
			acc.add(switch (type) {
				case NUMBER -> new Token.Number(buffer.toString());
				case EOF -> new Token.EOF();
				case STRING -> new Token.StringToken(buffer.toString());
				case SYMBOL -> new Token.Symbol((char) lastChar);
			});
		} while ((type = readToken()) != TokenType.EOF);
		return acc;
	}

	private TokenType readToken () {
		lastChar = ' ';
		buffer = new StringBuffer();
		while (Character.isSpaceChar(lastChar)) {
			nextChar();
		}

		// STRING
		if (Character.isAlphabetic(lastChar)) {
			appendWhile(this::isAlphaNumeric);
			return TokenType.STRING;
		}

		// NUMBER
		if (Character.isDigit(lastChar)) {
			appendWhile(Character::isDigit);

			if (lastChar == '.') {
				buffer.append((char) lastChar);
				nextChar();
				appendWhile(Character::isDigit);
			}
			return TokenType.NUMBER;
		}

		// EOF
		if (lastChar == -1) {
			return TokenType.EOF;
		}

		// SYMBOL
		return TokenType.SYMBOL;
	}

	private boolean isAlphaNumeric (int c) {
		return Character.isAlphabetic(c) || Character.isDigit(c);
	}

	private void appendWhile (IntPredicate n) {
		while (n.test(lastChar)) {
			buffer.append((char) lastChar);
			nextChar();
		}
	}

	private void nextChar () {
		if (n >= input.length()) {
			lastChar = -1;
		} else {
			lastChar = input.charAt(n);
			n++;
		}
	}
}
