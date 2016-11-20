package C.frontend.parsers;

import C.frontend.*;
import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;

import java.util.ArrayList;
import java.util.EnumSet;

import wci.frontend.*;
import C.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;
import static wci.intermediate.typeimpl.TypeFormImpl.*;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;

/**
 * <h1>VariableDeclarationsParser</h1>
 *
 * <p>Parse Pascal variable declarations.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class VariableDeclarationsParser extends DeclarationsParser
{
    private Definition definition;  // how to define the identifier

    /**
     * Constructor.
     * @param parent the parent parser.
     */
    public VariableDeclarationsParser(CParserTD parent)
    {
        super(parent);
    }

    /**
     * Setter.
     * @param definition the definition to set.
     */
    protected void setDefinition(Definition definition)
    {
        this.definition = definition;
    }

    // Synchronization set for the start of the next definition or declaration.
    static final EnumSet<CTokenType> NEXT_START_SET =
        EnumSet.of(IDENTIFIER, SEMICOLON);

    public SymTabEntry parse(Token token, SymTabEntry parentId, Token typeToken, Token identifierToken)
        throws Exception
    {

        // Parse the type specification.
        TypeSpec type = parseTypeSpec(typeToken);

        // Parse the identifier sublist and its type specification.
        ArrayList<SymTabEntry> sublist = parseIdentifierSublist(token, identifierToken, IDENTIFIER_FOLLOW_SET, COMMA_SET);

        token = currentToken();
        TokenType tokenType = token.getType();
       
        // Look for one or more semicolons after a definition.
        if (tokenType == SEMICOLON) {
            while (token.getType() == SEMICOLON) {
                token = nextToken();  // consume the ;
            }

        }

        // If at the start of the next definition or declaration,
        // then missing a semicolon.
        else if (NEXT_START_SET.contains(tokenType)) {
            errorHandler.flag(token, MISSING_SEMICOLON, this);
        }
        
        // Assign the type specification to each identifier in the list.
        for (SymTabEntry variableId : sublist) {
            variableId.setTypeSpec(type);
        }


        return null;
    }

    /**
     * Parse variable declarations.
     * @param token the initial token.
     * @param parentId the symbol table entry of the parent routine's name.
     * @return null
     * @throws Exception if an error occurred.
     */
    public SymTabEntry parse(Token token, SymTabEntry parentId)
        throws Exception
    {
        token = synchronize(IDENTIFIER_SET);

        // Loop to parse a sequence of variable declarations
        // separated by semicolons.
        while (IDENTIFIER_SET.contains(token.getType())) {
            // Parse the type specification.
            TypeSpec type = parseTypeSpec(token);

            // Parse the identifier sublist and its type specification.
            ArrayList<SymTabEntry> sublist = parseIdentifierSublist(token, IDENTIFIER_FOLLOW_SET, COMMA_SET);

            token = currentToken();
            TokenType tokenType = token.getType();

            // Look for one or more semicolons after a definition.
            if (tokenType == SEMICOLON) {
                while (token.getType() == SEMICOLON) {
                    token = nextToken();  // consume the ;
                }

            }

            // If at the start of the next definition or declaration,
            // then missing a semicolon.
            else if (NEXT_START_SET.contains(tokenType)) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            }
            
            // Assign the type specification to each identifier in the list.
            for (SymTabEntry variableId : sublist) {
                variableId.setTypeSpec(type);
            }
        }

        return null;
    }

    // Synchronization set to start a sublist identifier.
    static final EnumSet<CTokenType> IDENTIFIER_START_SET =
        EnumSet.of(IDENTIFIER, COMMA);
    static {
        IDENTIFIER_START_SET.addAll(IDENTIFIER_SET);
    }

    // Synchronization set to follow a sublist identifier.
    private static final EnumSet<CTokenType> IDENTIFIER_FOLLOW_SET =
        EnumSet.of(SEMICOLON);

    // Synchronization set for the , token.
    private static final EnumSet<CTokenType> COMMA_SET =
        EnumSet.of(COMMA, IDENTIFIER, SEMICOLON);

    /**
     * Parse a sublist of identifiers and their type specification.
     * @param token the current token.
     * @param followSet the synchronization set to follow an identifier.
     * @return the sublist of identifiers in a declaration.
     * @throws Exception if an error occurred.
     */
    protected ArrayList<SymTabEntry> parseIdentifierSublist(
                                         Token token,
                                         EnumSet<CTokenType> followSet,
                                         EnumSet<CTokenType> commaSet)
        throws Exception
    {
        ArrayList<SymTabEntry> sublist = new ArrayList<SymTabEntry>();

        do {
            token = synchronize(IDENTIFIER_START_SET);
            SymTabEntry id = parseIdentifier(token);

            if (id != null) {
                sublist.add(id);
            }
            
            token = synchronize(commaSet);
            TokenType tokenType = token.getType();

            // Look for the comma.
            if (tokenType == COMMA) {
                token = nextToken();  // consume the comma

                if (followSet.contains(token.getType())) {
                    errorHandler.flag(token, MISSING_IDENTIFIER, this);
                }
            }


        } while (!followSet.contains(token.getType()));
        return sublist;
    }

    /**
     * Parse a sublist of identifiers and their type specification.
     * @param token the current token.
     * @param followSet the synchronization set to follow an identifier.
     * @return the sublist of identifiers in a declaration.
     * @throws Exception if an error occurred.
     */
    protected ArrayList<SymTabEntry> parseIdentifierSublist(
                                         Token token,
                                         Token firstIdentifier,
                                         EnumSet<CTokenType> followSet,
                                         EnumSet<CTokenType> commaSet)
        throws Exception
    {
        ArrayList<SymTabEntry> sublist = new ArrayList<SymTabEntry>();
       
        SymTabEntry firstId = parseIdentifier1(firstIdentifier);
        if (firstId != null) {
            sublist.add(firstId);
        }
        TokenType tokenType = token.getType();

        // Look for the comma.
        if (tokenType == COMMA) {
            token = nextToken();  // consume the comma
        }

        do {
            token = synchronize(IDENTIFIER_START_SET);
            SymTabEntry id = parseIdentifier(token);

            if (id != null) {
                sublist.add(id);
            }
            
            token = synchronize(commaSet);
            tokenType = token.getType();

            // Look for the comma.
            if (tokenType == COMMA) {
                token = nextToken();  // consume the comma
                if (followSet.contains(token.getType())) {
                    errorHandler.flag(token, MISSING_IDENTIFIER, this);
                }
            }


        } while (!followSet.contains(token.getType()));
        return sublist;
    }

    /**
     * Parse an identifier.
     * @param token the current token.
     * @return the symbol table entry of the identifier.
     * @throws Exception if an error occurred.
     */
    private SymTabEntry parseIdentifier(Token token)
        throws Exception
    {
        SymTabEntry id = null;

        if (token.getType() == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            id = symTabStack.lookupLocal(name);

            // Enter a new identifier into the symbol table.
            if (id == null) {
                id = symTabStack.enterLocal(name);
                id.setDefinition(definition);
                id.appendLineNumber(token.getLineNumber());
            }
            else {
                errorHandler.flag(token, IDENTIFIER_REDEFINED, this);
            }

            token = nextToken();   // consume the identifier token
        }
        else {
            errorHandler.flag(token, MISSING_IDENTIFIER, this);
        }

        return id;
    }

    private SymTabEntry parseIdentifier1(Token token)
        throws Exception
    {
        SymTabEntry id = null;
        
        if (token.getType() == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            id = symTabStack.lookupLocal(name);

            // Enter a new identifier into the symbol table.
            if (id == null) {
                id = symTabStack.enterLocal(name);
                id.setDefinition(definition);
                id.appendLineNumber(token.getLineNumber());
            }
            else {
                errorHandler.flag(token, IDENTIFIER_REDEFINED, this);
            }
        }
        else {
            errorHandler.flag(token, MISSING_IDENTIFIER, this);
        }

        return id;
    }

    // Synchronization set for the : token.
    private static final EnumSet<CTokenType> COLON_SET =
        EnumSet.of(COLON, SEMICOLON);

    /**
     * Parse the type specification.
     * @param token the current token.
     * @return the type specification.
     * @throws Exception if an error occurs.
     */
    protected TypeSpec parseTypeSpec(Token token)
        throws Exception
    {
        // Parse the type specification.
        TypeSpecificationParser typeSpecificationParser =
            new TypeSpecificationParser(this);
        TypeSpec type = typeSpecificationParser.parse(token);

        // Formal parameters and functions must have named types.
        if ((definition != VARIABLE) && (definition != FIELD) &&
            (type != null) && (type.getIdentifier() == null))
        {
            errorHandler.flag(token, INVALID_TYPE, this);
        }
        
        return type;
    }
}
