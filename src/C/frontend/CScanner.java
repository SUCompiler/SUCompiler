package C.frontend;

import wci.frontend.*;
import C.frontend.tokens.*;

import static wci.frontend.Source.EOF;
import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;


public class CScanner extends Scanner
{
    /**
     * Constructor
     * @param source the source to be used with this scanner.
     */
    public CScanner(Source source)
    {
        super(source);
    }

    /**
     * Extract and return the next C token from the source.
     * @return the next token.
     * @throws Exception if an error occurred.
     */
    protected Token extractToken()
        throws Exception
    {
        skipWhiteSpace();
        Token token;
        char currentChar = currentChar();

        // Construct the next token.  The current character determines the
        // token type.
        if (currentChar == EOF) {
            token = new EofToken(source);
        }
        else if (Character.isLetter(currentChar)) {
            token = new CWordToken(source);
        }
        else if (Character.isDigit(currentChar)) {
            token = new CNumberToken(source);
        }
        else if (currentChar == '\'') {
            token = new CStringToken(source);
        }
        else if (CTokenType.SPECIAL_SYMBOLS
                 .containsKey(Character.toString(currentChar))) {
            token = new CSpecialSymbolToken(source);
        }
        else {
            token = new CErrorToken(source, INVALID_CHARACTER,
                                         Character.toString(currentChar));
            nextChar();  // consume character
        }

        return token;
    }

    /**
     * Skip whitespace characters by consuming them.  A comment is whitespace.
     * @throws Exception if an error occurred.
     */
    private void skipWhiteSpace()
        throws Exception
    {
    	char COMMENT_START = '/';
    	char COMMENT_ENDs = '*';
    	
        char currentChar = currentChar();

        while (Character.isWhitespace(currentChar) || (currentChar == COMMENT_START)) {
        	
            // Start of a comment?
            if (currentChar == COMMENT_START) {
            	currentChar = nextChar();
            	if (currentChar == '*') {
            		do {
                        currentChar = nextChar();  // consume comment characters
                    } while ((currentChar != COMMENT_ENDs) && (currentChar != EOF));
            		
                    // Found closing '}'?
                    if (currentChar == COMMENT_ENDs && nextChar() == '/') {
                        currentChar = nextChar();  // consume the '}'
                    }
            	}
            }

            // Not a comment.
            else {
                currentChar = nextChar();  // consume whitespace character
            }
        }
    }
}

