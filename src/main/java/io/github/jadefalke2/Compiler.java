package io.github.jadefalke2;

import io.github.jadefalke2.lexer.Lexer;

public class Compiler {

	public static void main (String[] args) {
		System.out.println(new Lexer().tokenize(
			"""
				test abc def ... 100 100.247348 34932094 abc def qqqqq q q q
				asdsads
				sad          
				ss       
				d
				100
				12
				-5   
				
				     
				     a
				sadisj
				"""
		));
	}

}
