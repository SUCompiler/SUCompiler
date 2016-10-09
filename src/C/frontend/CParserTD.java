package C.frontend;

import wci.frontend.*;
import wci.intermediate.*;
import wci.message.Message;

import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.message.MessageType.*;


public class CParserTD extends Parser
{
	protected static CErrorHandler errorHandler = new CErrorHandler();
	
    /**
     * Constructor.
     * @param scanner the scanner to be used with this parser.
     */
    public CParserTD(Scanner scanner)
    {
        super(scanner);
    }

    /**
     * Parse a Pascal source program and generate the symbol table
     * and the intermediate code.
     */
    public void parse()
        throws Exception
    {
        Token token;
        long startTime = System.currentTimeMillis();

        try {

            // Loop over each token until the end of file.
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();
                
//                if (tokenType != ERROR) {
//
//                    // Format each token.
//                    sendMessage(new Message(TOKEN,
//                                            new Object[] {token.getLineNumber(),
//                                                          token.getPosition(),
//                                                          tokenType,
//                                                          token.getText(),
//                                                          token.getValue()}));
//                }
                
                // Cross reference only the identifiers.
                if (tokenType == IDENTIFIER) {
                    String name = token.getText().toLowerCase();

                    // If it's not already in the symbol table,
                    // create and enter a new entry for the identifier.
                    SymTabEntry entry = symTabStack.lookup(name);
                    if (entry == null) {
                        entry = symTabStack.enterLocal(name);
                    }

                    // Append the current line number to the entry.
                    entry.appendLineNumber(token.getLineNumber());
                }
                else if (tokenType == ERROR) {
                    errorHandler.flag(token, (CErrorCode) token.getValue(),
                                      this);
                }

            }

            // Send the parser summary message.
            float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
            sendMessage(new Message(PARSER_SUMMARY,
                                    new Number[] {token.getLineNumber(),
                                                  getErrorCount(),
                                                  elapsedTime}));
        }
        catch (java.io.IOException ex) {
        	System.out.println("error");
            errorHandler.abortTranslation(IO_ERROR, this);
        }
    }

    /**
     * Return the number of syntax errors found by the parser.
     * @return the error count.
     */
    public int getErrorCount()
    {
        return 0;
    }
}
