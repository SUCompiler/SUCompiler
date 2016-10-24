package C.frontend.parsers;

import java.util.EnumSet;

import C.frontend.*;
import wci.frontend.*;
import wci.intermediate.*;

import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;

/**
 * <h1>AssignmentStatementParser</h1>
 *
 * <p>Parse a C assignment statement.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class AssignmentStatementParser extends StatementParser
{
    // Synchronization set for the = token.
    private static final EnumSet<CTokenType> ASSIGNMENT_SET =
        ExpressionParser.EXPR_START_SET.clone();
    static {
        ASSIGNMENT_SET.add(ASSIGNMENT);
        ASSIGNMENT_SET.addAll(StatementParser.STMT_FOLLOW_SET);
    }
    
    /**
     * Constructor.
     * @param parent the parent parser.
     */
    public AssignmentStatementParser(CParserTD parent)
    {
        super(parent);
    }

    /**
     * Parse an assignment statement.
     * @param token the initial token.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    public ICodeNode parse(Token token)
        throws Exception
    {
        // Create the ASSIGN node.
        ICodeNode assignNode = ICodeFactory.createICodeNode(ASSIGN);

        // Look up the target identifer in the symbol table stack.
        // Enter the identifier into the table if it's not found.
        String targetName = token.getText().toLowerCase();
        SymTabEntry targetId = symTabStack.lookup(targetName);
        if (targetId == null) {
            targetId = symTabStack.enterLocal(targetName);
        }
        targetId.appendLineNumber(token.getLineNumber());

        token = nextToken();  // consume the identifier token

        // Create the variable node and set its name attribute.
        ICodeNode variableNode = ICodeFactory.createICodeNode(VARIABLE);
        variableNode.setAttribute(ID, targetId);

        // The ASSIGN node adopts the variable node as its first child.
        assignNode.addChild(variableNode);

        // Synchronize on the := token.
        token = synchronize(ASSIGNMENT_SET);
        if (token.getType() == ASSIGNMENT) {
            token = nextToken();  // consume the :=
        }
        else {
            errorHandler.flag(token, MISSING_ASSIGNMENT, this);
        }

        // Parse the expression.  The ASSIGN node adopts the expression's
        // node as its second child.
        ExpressionParser expressionParser = new ExpressionParser(this);
        assignNode.addChild(expressionParser.parse(token));


        token = currentToken();

        if (token.getType() != SEMICOLON) {
            errorHandler.flag(token, MISSING_SEMICOLON, this);
        }

        token = nextToken();
  

        return assignNode;
    }
}
