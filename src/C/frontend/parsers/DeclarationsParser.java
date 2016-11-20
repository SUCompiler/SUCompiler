package C.frontend.parsers;

import C.frontend.*;

import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;

import java.util.EnumSet;

import wci.frontend.*;
import wci.intermediate.*;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.VARIABLE;

/**
 * <h1>DeclarationsParser</h1>
 *
 * <p>Parse Pascal declarations.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class DeclarationsParser extends CParserTD
{
    private Token type;
    private Token identifier;

    // Synchronization set for a variable identifier.
    static protected final EnumSet<CTokenType> IDENTIFIER_SET =
        EnumSet.of(INT, BOOL, FLOAT, STRING);

    // Synchronization set for a variable identifier.
    static protected final EnumSet<CTokenType> FUNCTION_SET = IDENTIFIER_SET.clone();

    static {
        FUNCTION_SET.add(VOID);
    }

    // Synchronization set for a variable identifier.
    static protected final EnumSet<CTokenType> DECLARATION_IDENTIFIER_SET =
        EnumSet.of(IDENTIFIER);

    /**
     * Constructor.
     * @param parent the parent parser.
     */
    public DeclarationsParser(CParserTD parent)
    {
        super(parent);
    }

    /**
     * Parse declarations.
     * To be overridden by the specialized declarations parser subclasses.
     * @param token the initial token.
     * @throws Exception if an error occurred.
     */
    public SymTabEntry parse(Token token, SymTabEntry parentId)
        throws Exception
    {
        token = synchronize(IDENTIFIER_SET);

        while (IDENTIFIER_SET.contains(token.getType())) {
            type = token;
            token = nextToken();
            identifier = token;
            token = nextToken();
            TokenType tokenType = token.getType();

            switch((CTokenType) tokenType) {
                case LEFT_BRACE:
                    // Parse the program.
                    // DeclaredRoutineParser routineParser = new DeclaredRoutineParser(this);
                    // routineParser.parse(token, parentId);
                    break;
                default:
                    VariableDeclarationsParser variableDeclarationsParser =
                        new VariableDeclarationsParser(this);
                    variableDeclarationsParser.setDefinition(VARIABLE);
                    variableDeclarationsParser.parse(token, null, type, identifier);
            }
            
            token = currentToken();

            token = currentToken();
        }

        return null;
    }
}
