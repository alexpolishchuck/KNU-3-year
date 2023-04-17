package org.example;

import org.example.Lexer.Lexer;
import org.example.Lexer.Token;
import org.example.Lexer.TokenWrapper;

import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        String file = "C:\\Users\\oleksandr.polishchuk\\Desktop\\oleksandrsfolder\\gitrepo\\KNU-3-year\\First semester\\Ruby\\untitled\\test.rb";

            Lexer lexer = new Lexer();
        ArrayDeque<TokenWrapper> tokens = lexer.processFile(file);

        for(TokenWrapper token : tokens)
        {
            StringBuilder sb = new StringBuilder();

            if(token.getLexeme().equals("\t"))
                sb.append("\\t");
            else if(token.getLexeme().equals("\n"))
                sb.append("\\n");
            else if(token.getLexeme().equals("\r"))
                sb.append("\\r");
            else if(token.getLexeme().equals("\r\n"))
                sb.append("\\r\\n");
            else
                sb.append(token.getLexeme());

            System.out.println("[ " + sb.toString() + " ]" + " - " + token.getToken().name());
        }

    }
}