package C.frontend.parsers;

import java.util.EnumSet;

import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;
import wci.intermediate.typeimpl.*;

import C.frontend.*;
import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;
import static wci.intermediate.typeimpl.TypeFormImpl.*;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;

/**
 * <h1>SimpleTypeParser</h1>
 *
 * <p>Parse a simple Pascal type (identifier, subrange, enumeration)
 * specification.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
class SimpleTypeParser extends TypeSpecificationParser
{
    /**
     * Constructor.
     * @param parent the parent parser.
     */
    protected SimpleTypeParser(CParserTD parent)
    {
        super(parent);
    }

    // Synchronization set for starting a simple type specification.
    static final EnumSet<CTokenType> SIMPLE_TYPE_START_SET =
        VariableDeclarationsParser.IDENTIFIER_SET.clone();

    /**
     * Parse a simple Pascal type specification.
     * @param token the current token.
     * @return the simple type specification.
     * @throws Exception if an error occurred.
     */
    public TypeSpec parse(Token token)
        throws Exception
    {
        // Synchronize at the start of a simple type specification.
        // token = synchronize(SIMPLE_TYPE_START_SET);
        String name = "";
        
        switch ((CTokenType) token.getType()) {
            case INT: {
                name = "integer";
                break;
            }
            case BOOL: {
                name = "boolean";
                break;
            }
            case STRING: {
                name = "string";
                break;
            }
            case FLOAT: {
                name = "real";
                break;
            }

            default: {
                errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                token = nextToken();  // consume the identifier
                return null;
            }
        }

        SymTabEntry id = symTabStack.lookup(name);
     
        if (id != null) {
            Definition definition = id.getDefinition();

            // It's either a type identifier
            // or the start of a subrange type.
            if (definition == DefinitionImpl.TYPE) {
                id.appendLineNumber(token.getLineNumber());
                
                // Return the type of the referent type.
                return id.getTypeSpec();
            } else {
                errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                token = nextToken();  // consume the identifier
                return null;
            }
        }
        else {
            errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
            token = nextToken();  // consume the identifier
            return null;
        }
    }
}
