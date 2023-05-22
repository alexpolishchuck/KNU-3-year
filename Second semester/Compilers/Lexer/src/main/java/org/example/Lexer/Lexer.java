package org.example.Lexer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(currentPos >= file.length())
            return null;

        int size = file.length();

        //find each ws/delimiter
        Pattern delimetersAndWs = Pattern.compile(Token.WS.getRegex() +"|"+ Token.DELIMITER.getRegex());
        Matcher matcher = delimetersAndWs.matcher(file);

       if(matcher.find(currentPos))
           return runThroughFilters(file, matcher.start());

        //if not found any, check if there are symbols left to read, i.e. "\r\n asdasdasd"

        return runThroughFilters(file, size);
    }
    private Token runThroughFilters(String file, int matcherPos)
    {
       Token token = WSFilter(file, matcherPos);

       if(token != null)
           return token;

       token = DelimiterFilter(file, matcherPos);

        if(token != null)
            return token;

        String subString = file.substring(currentPos);

        Token[] values = Token.values();

        int size = values.length;
        int counter = 4;

        token = KeywordIdentifierFilter(subString, matcherPos);

        if(token != null)
            return token;

        while(token == null && counter < size)
        {
            token = generalFilter(subString, matcherPos, values[counter]);

            if(token != null)
                return token;

            counter++;
        }

       return Token.ERROR;
    }
    private Token WSFilter(String file, int matcherPos) {
       if(matcherPos != currentPos
               || currentPos >= file.length()
               || !Character.toString(file.charAt(currentPos)).matches(Token.WS.getRegex()))
           return null;

       StringBuilder lexeme = new StringBuilder();

       Token token = Token.WS;

       Character ch = file.charAt(currentPos);
       lexeme.append(ch);
       currentPos += 1;

       if (currentPos != file.length() && ch.equals('\r') && Character.compare('\n', file.charAt(currentPos)) == 0)
       {
           lexeme.append(file.charAt(currentPos));
           currentPos += 1;
       }

        token.setLexeme(lexeme.toString());
        return token;
    }

    private Token DelimiterFilter(String file, int matcherPos)
    {
        if(currentPos >= file.length() || currentPos != matcherPos)
            return null;

        Token token = Token.DELIMITER;

        if(Character.toString(file.charAt(matcherPos)).matches(Token.DELIMITER.getRegex()))
        {
            token.setLexeme(Character.toString(file.charAt(matcherPos)));
            currentPos++;
            return token;
        }

        return null;
    }
    private Token KeywordIdentifierFilter(String fileSubstr, int matcherPos)
    {
        Pattern pattern = Pattern.compile(Token.IDENTIFIER.getRegex());
        Matcher matcher = pattern.matcher(fileSubstr);

        if(!matcher.find(0))
            return null;

        String lexeme = fileSubstr.substring(0,matcher.end());
        Token token = null;

        if(lexeme.matches(Token.KEYWORD.getRegex()))
            token = Token.KEYWORD;
        else
            token = Token.IDENTIFIER;

        token.setLexeme(lexeme);

        currentPos += matcher.end();

        return token;
    }
    private Token generalFilter(String fileSubstr, int matcherPos, Token tokenType)
    {
        Pattern pattern = Pattern.compile(tokenType.getRegex());
        Matcher matcher = pattern.matcher(fileSubstr);

        if(!matcher.find(0))
            return null;

        tokenType.setLexeme(fileSubstr.substring(0, matcher.end() - matcher.start()));

        currentPos += matcher.end() - matcher.start();

        return tokenType;
    }

    private int currentPos;
}
