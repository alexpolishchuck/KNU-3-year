package org.example.Lexer;

import java.util.regex.Pattern;

public enum Token {

    DELIMITER("[,\\.;\\[\\]\\(\\)\\{\\}]"),
    WS("[ \t\r\n]|(\r\n)"),
    KEYWORD("(alias)|(and)|(BEGIN)|(begin)|(break)|(case)|(class)|(def)|(defined?)|(do)|(else)|" +
            "(elsif)|(END)|(end)|(ensure)|(false)|(for)|(if)|(in)|(module)|(next)|(nil)|(not)|(or)" +
            "|(redo)|(rescue)|(retry)|(return)|(self)|(super)|(then)|(true)|(undef)|(unless)|(until)|(when)" +
            "|(while)|(yield)"),
    IDENTIFIER(""),
    COMMENT(""), // fucking awesome
    STRING(""),//check backslash notation
    NUMBER("[0-9]+\\.[0-9]+"),
    OPERATION("[+=><|&!-~*%/^?:]|(==)|(>=)|(<=)|(||)|(&&)|(?:)|(<<)|(>>)|(===)|(<=>)|(!=)|(%=)|(/=)" +
            "|(-=)|(+=)|(*=)|(**=)|(..)|(...)"),
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
