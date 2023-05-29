package org.example.Parser.Listeners;

import org.antlr.v4.runtime.*;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class SyntaxErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {

        hasErrorOccurred = true;

        List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);

        System.out.println("Syntax error on line " + line + ", col " + charPositionInLine + 1 + "; Token:"
                + ((Token)offendingSymbol).getText() + " : " + msg);

        System.out.println(stack);
    }

    public boolean hasError()
    {
        return hasErrorOccurred;
    }
    private boolean hasErrorOccurred = false;
}
