package io.github.jadefalke2.lexer;

public interface Token {

	interface DataToken <T> extends Token {
		T data ();
	}

	record Number (String data) implements DataToken<String> {}
	record StringToken(String data) implements DataToken<String> {}
	record Symbol (Character data) implements DataToken<Character> {}
	class EOF implements Token {}
}
