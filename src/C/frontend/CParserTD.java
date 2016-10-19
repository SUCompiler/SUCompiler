package C.frontend;

import wci.frontend.*;
import wci.intermediate.*;
import wci.message.Message;

import C.frontend.parsers.*;
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
     * Constructor for subclasses.
     * @param parent the parent parser.
     */
    public CParserTD(CParserTD parent)
    {
        super(parent.getScanner());
    }

    /**
     * Parse a Pascal source program and generate the symbol table
     * and the intermediate code.
     */
    public void parse()
        throws Exception
    {
    	long startTime = System.currentTimeMillis();
        iCode = ICodeFactory.createICode();

        try {
            Token token = nextToken();
            ICodeNode rootNode = null;

            // Look for the LEFT_BRACE token to parse a compound statement.
            if (token.getType() == LEFT_BRACE) {
                StatementParser statementParser = new StatementParser(this);
                rootNode = statementParser.parse(token);
                token = currentToken();
            }
            else {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
            }

//            // Look for the final period.
//            if (token.getType() != RIGHT_BRACE) {
//                errorHandler.flag(token, MISSING_RIGHT_BRACE, this);
//            }
            token = currentToken();

            // Set the parse tree root node.
            if (rootNode != null) {
                iCode.setRoot(rootNode);
            }

            // Send the parser summary message.
            float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
            sendMessage(new Message(PARSER_SUMMARY,
                                    new Number[] {token.getLineNumber(),
                                                  getErrorCount(),
                                                  elapsedTime}));
        }
        catch (java.io.IOException ex) {
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
