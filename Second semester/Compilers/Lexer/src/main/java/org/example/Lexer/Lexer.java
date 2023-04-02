package org.example.Lexer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;

public class Lexer {

   public ArrayDeque<Token> processFile(String filePath) throws Exception
    {
        currentPos = 0;

        ArrayDeque<Token> res = new ArrayDeque<>();

        Path path = Paths.get(filePath);
        String file = Files.readString(path, StandardCharsets.UTF_8);

        int size = file.length();

        while(currentPos < size)
        {
            Token token = nextToken(file);

            if(token != null)
            {
                res.add(token);

                if(token == Token.ERROR)
                    return res;
            }
        }

        return res;
    }

    public Token nextToken(String s)
    {
        int size = s.length();

        StringBuilder lexeme = new StringBuilder();
        Character ch;

        while(currentPos != size)
        {
            ch = s.charAt(currentPos);

            if(ch.toString().matches(Token.WS.getRegex())
                    || ch.toString().matches(Token.DELIMITER.getRegex()))
            {
                WSFilter(s, lexeme);
                return validateLexeme(lexeme);
            }

            lexeme.append(ch);
            currentPos++;
        }

        if(!lexeme.isEmpty())
            return validateLexeme(lexeme);

        return null;
    }

    private Token validateLexeme(StringBuilder lexeme)
    {
        if(lexeme.isEmpty())
            return Token.ERROR;

        Token token = findToken(lexeme.toString());

        while(!lexeme.isEmpty() && token == Token.ERROR)
        {
            lexeme.deleteCharAt(lexeme.length() - 1);
            currentPos--;
            token = findToken(lexeme.toString());
        }

        return token;
    }

    private Token findToken(String lexeme)
    {
        for(Token token : Token.values())
        {
            if(lexeme.matches(token.getRegex()))
            {
                token.setLexeme(lexeme);
                return token;
            }
        }

        return Token.ERROR;
    }

    private void WSFilter(String file, StringBuilder lexeme)
    {
        if(!lexeme.isEmpty())
            return;

        Character ch = file.charAt(currentPos);
        lexeme.append(ch);

        if(currentPos == file.length() - 1)
            return;

        if(ch.equals('\r') && Character.compare('\n',file.charAt(currentPos + 1)) == 0)
            lexeme.append(file.charAt(currentPos + 1));

        currentPos += 2;
    }

    private void commentFilter(String file, StringBuilder lexeme)
    {

    }

    private void operatorFilter(String file, StringBuilder lexeme)
    {

    }
    private int currentPos;

}
