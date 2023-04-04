package org.example.Lexer;

public class TokenWrapper {

    public TokenWrapper(Token token)
    {
        this.token = token;
        this.lexeme = token.getLexeme();
    }

    public Token getToken() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }

    private Token token;
    private String lexeme;
}
