package C.frontend.tokens;

import wci.frontend.*;
import C.frontend.*;

import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;
import java.lang.String;
import java.util.Arrays;
/**
 * <h1>CSpecialSymbolToken</h1>
 *
 * <p> C special symbol tokens.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class CSpecialSymbolToken extends CToken
{
    /**
     * Constructor.
     * @param source the source from where to fetch the token's characters.
     * @throws Exception if an error occurred.
     */
    public CSpecialSymbolToken(Source source)
        throws Exception
    {
        super(source);
    }

    protected int comsumeCharSymbol(String symbol, String[] symbols) {
    	for (int i = 0; i < symbols.length; i++) {
			if (symbol.equals(symbols[i])) {
				return i;
			}
		}
    	return -1;
    }
    
    /**
     * Extract a C special symbol token from the source.
     * @throws Exception if an error occurred.
     */
    protected void extract()
        throws Exception
    {
        char currentChar = currentChar();

        text = Character.toString(currentChar);
        type = null;
        
        String[] oneCharSymbol = new String[] {
        		"+", "-", ",", "\"", ";", "\'", "(", ")", 
        		"{", "}", "#", ".", "<", ">", "=", "/", "*"
        };
        String[] twoCharSymbol = new String[] {
        		"+=", "-=", "==", "!=", "<=", ">=", "||", "&&"
        };
        
        int index = -1;
        index = comsumeCharSymbol(text, oneCharSymbol);

        if (peekChar() != ' ' || peekChar() != '\n') {
        	String tmpWord = text + String.valueOf(peekChar());
        	int tmpIndex = comsumeCharSymbol(tmpWord, twoCharSymbol);
        	if (tmpIndex >= 0) {
        		index = tmpIndex;
        		text = tmpWord;
        		nextChar();
        	}
        }
        
        nextChar();
        if(index <= -1) {
        	type = ERROR;
        	value = INVALID_CHARACTER;
        }

        // Set the type if it wasn't an error.
        if (type == null) {
            type = SPECIAL_SYMBOLS.get(text);
        }
    }
}