package org.example;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.Lexer.Lexer;
import org.example.Lexer.TokenWrapper;
import org.example.Parser.gen.exprLexer;
import org.example.Parser.gen.exprParser;

import java.io.IOException;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws Exception {

        parserMain();
    }
    private static void parserMain() throws IOException {
        String file = "C:\\Users\\oleksandr.polishchuk\\Desktop\\oleksandrsfolder\\gitrepo\\KNU-3-year\\First semester\\Ruby\\untitled\\test.rb";
        CharStream input = CharStreams.fromFileName(file);

        exprLexer lexer = new exprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        exprParser parser = new exprParser(tokens);

        ParseTree tree = parser.prog();

        System.out.println(tree.toStringTree(parser));
    }
    private static void lexerMain() throws Exception {
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