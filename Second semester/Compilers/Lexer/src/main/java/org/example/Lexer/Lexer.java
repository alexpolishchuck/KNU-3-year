package org.example.Lexer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;

public class Lexer {

   public ArrayDeque<TokenWrapper> processFile(String filePath) throws Exception
    {
        currentPos = 0;

        ArrayDeque<TokenWrapper> res = new ArrayDeque<>();

        Path path = Paths.get(filePath);
        String file = Files.readString(path, StandardCharsets.UTF_8);

        int size = file.length();

        while(currentPos < size)
        {
            Token token = nextToken(file);

            if(token != null)
            {
                res.add(new TokenWrapper(token));

                if(token == Token.ERROR)
                    return res;
            }
        }

        return res;
    }

    public Token nextToken(String file)
    {
        int size = file.length();

        StringBuilder lexeme = new StringBuilder();
        Character ch;

        while(currentPos != size)
        {
            ch = file.charAt(currentPos);

            if(ch.toString().matches(Token.WS.getRegex())
                    || ch.toString().matches(Token.DELIMITER.getRegex()))
            {
                if(runThroughFilters(file, lexeme))
                    return validateLexeme(lexeme);
            }

            lexeme.append(ch);
            currentPos++;
        }

        if(!lexeme.isEmpty())
            return validateLexeme(lexeme);

        return null;
    }
    private boolean runThroughFilters(String file, StringBuilder lexeme)
    {
        if(WSFilter(file, lexeme))
            return true;
        if(stringFilter(file, lexeme))
            return true;
        if(commentFilter(file, lexeme))
            return true;

        return false;
    }

   private boolean stringFilter(String file, StringBuilder lexeme)
    {
        int size = file.length();

        if(size >= 3 && file.substring(0,4).matches("%[qQ][^A-Za-z0-9]]"))
            return true;

        return size >= 1 && (file.charAt(0) == '\'' || file.charAt(0) == '\"');
    }

    private boolean commentFilter(String file, StringBuilder lexeme)
    {
        int size = file.length();

        if(size >= 1 && file.charAt(0)=='#')
            return true;

        return size >= 6 && file.substring(0, 7).matches("=begin");
    }

    private boolean operatorFilter(String file, StringBuilder lexeme)
    {
        return true;
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

    private boolean WSFilter(String file, StringBuilder lexeme) {
        if (!lexeme.isEmpty())
            return false;

        Character ch = file.charAt(currentPos);
        lexeme.append(ch);
        currentPos += 1;

        if (currentPos == file.length())
            return true;

        if (ch.equals('\r') && Character.compare('\n', file.charAt(currentPos)) == 0)
        {
            lexeme.append(file.charAt(currentPos));
            currentPos += 1;
        }

        return true;
    }
    private int currentPos;
}
