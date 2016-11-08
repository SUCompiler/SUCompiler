package C.frontend;

import java.util.EnumSet;

import wci.frontend.*;
import wci.intermediate.*;
import wci.message.Message;
import wci.intermediate.symtabimpl.*;
import wci.intermediate.typeimpl.*;

import C.frontend.parsers.*;

import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.typeimpl.TypeFormImpl.*;
import static wci.message.MessageType.*;


public class CParserTD extends Parser
{
	protected static CErrorHandler errorHandler = new CErrorHandler();
	
	private SymTabEntry routineId; // name of the routine being parsed
	 
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
    public SymTabEntry getRoutineId()
  {
     return routineId;
   }
    /**
     * Parse a Pascal source program and generate the symbol table
     * and the intermediate code.
     */
    public void parse()
        throws Exception
    {
    	long startTime = System.currentTimeMillis();
        ICode iCode = ICodeFactory.createICode();
        Predefined.initialize(symTabStack);
        routineId = symTabStack.enterLocal("DummyProgramName".toLowerCase());
	routineId.setDefinition(DefinitionImpl.PROGRAM);
	symTabStack.setProgramId(routineId);

 // Push a new symbol table onto the symbol table stack and set
 // the routine's symbol table and intermediate code.
 	routineId.setAttribute(ROUTINE_SYMTAB, symTabStack.push());
 	routineId.setAttribute(ROUTINE_ICODE, iCode);

 BlockParser blockParser = new BlockParser(this);

        try {
            Token token = nextToken();
         

           ICodeNode rootNode = blockParser.parse(token, routineId);
 	   iCode.setRoot(rootNode);
 	   symTabStack.pop();
// Look for the final period.
 	   token = currentToken();

//            // Look for the final period.
//            if (token.getType() != RIGHT_BRACE) {
//                errorHandler.flag(token, MISSING_RIGHT_BRACE, this);
//            }
            token = currentToken();

            // Set the parse tree root node.
            

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
    
    /**
     * Synchronize the parser.
     * @param syncSet the set of token types for synchronizing the parser.
     * @return the token where the parser has synchronized.
     * @throws Exception if an error occurred.
     */
    public Token synchronize(EnumSet syncSet)
        throws Exception
    {
        Token token = currentToken();

        // If the current token is not in the synchronization set,
        // then it is unexpected and the parser must recover.
        if (!syncSet.contains(token.getType())) {

            // Flag the unexpected token.
            errorHandler.flag(token, UNEXPECTED_TOKEN, this);

            // Recover by skipping tokens that are not
            // in the synchronization set.
            do {
                token = nextToken();
            } while (!(token instanceof EofToken) &&
                     !syncSet.contains(token.getType()));
       }

       return token;
    }
}
