package org.example.Lexer;

import java.util.regex.Pattern;

public enum Token {

    DELIMITER("[,;\\[\\]\\(\\)\\{\\}]"),
    WS("[ \t\r\n]|(\r\n)"),
    KEYWORD("^((alias)|(and)|(BEGIN)|(begin)|(break)|(case)|(class)|(def)|(defined?)|(do)|(else)|" +
            "(elsif)|(END)|(end)|(ensure)|(false)|(for)|(if)|(in)|(module)|(next)|(nil)|(not)|(or)" +
            "|(redo)|(rescue)|(retry)|(return)|(self)|(super)|(then)|(true)|(undef)|(unless)|(until)|(when)" +
            "|(while)|(yield))"),
    IDENTIFIER("^(((@{1,2})|(\\${0,1}))[A-Za-z_]+[A-Za-z0-9_]*)"),
    COMMENT("^((#.*)|((=begin)(.|[\n\r])*=end))"),
    STRING("(^\"([^\"]|[\r\n])*\")|" +
            "(^%[qQ]([^A-Za-z0-9])(.|[\r\n])*\4)|" +
            "(^'([^']|[\r\n])*')"),//check backslash notation
  //  STRING_BEG("(%[qQ]([^A-Za-z0-9]))|'|\""),
  //  STRING_END("([^A-Za-z0-9])|'|\""),
    NUMBER("^[0-9]+(\\.{0,1}[0-9]+){0,1}"),
    OPERATION("^\\.{1,3}|:{1,2}|={1,3}|(\\*\\*=)|(<=>)|(>=)|(<=)|(\\|\\|)|(\\&\\&)|(\\?:)|(<<)|(>>)|(\\!=)|(%=)|(\\/=)" +
            "|(-=)|(\\+=)|(\\*=)|(\\^=)|[\\~\\+><\\|\\&\\!-\\*%\\/\\^\\?]"),
    ERROR("");
    Token(String str)
    {
        regex = str;
    }

    public String getRegex() {
        return regex;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    private String regex;
    private String lexeme;
}
