package C.frontend.parsers;

import java.util.EnumSet;

import C.frontend.*;
import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;

import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;
import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.VARIABLE;
/**
 * <h1>StatementParser</h1>
 *
 * <p>
 * Parse a C statement.
 * </p>
 *
 * <p>
 * Copyright (c) 2009 by Ronald Mak
 * </p>
 * <p>
 * For instructional purposes only. No warranties.
 * </p>
 */
public class StatementParser extends CParserTD {
	// Synchronization set for starting a statement.
	protected static final EnumSet<CTokenType> STMT_START_SET =
	    EnumSet.of(LEFT_BRACE, CTokenType.IF, WHILE,
		      IDENTIFIER, SEMICOLON);

	// Synchronization set for following a statement.
	protected static final EnumSet<CTokenType> STMT_FOLLOW_SET =
	    EnumSet.of(SEMICOLON, RIGHT_BRACE, ELSE);
        
	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            the parent parser.
	 */
	public StatementParser(CParserTD parent) {
		super(parent);
	}

	/**
	 * Parse a statement. To be overridden by the specialized statement parser
	 * subclasses.
	 * 
	 * @param token
	 *            the initial token.
	 * @return the root node of the generated parse tree.
	 * @throws Exception
	 *             if an error occurred.
	 */
	public ICodeNode parse(Token token) throws Exception {
		return parse(token, false);
	}

	public ICodeNode parse(Token token, boolean requiredReturn) throws Exception {
		ICodeNode statementNode = null;
		
		switch ((CTokenType) token.getType()) {
		    case LEFT_BRACE: {
					CompoundStatementParser compoundParser = new CompoundStatementParser(this);
					statementNode = compoundParser.parse(token, requiredReturn);
					break;
		    }
		
		    // An assignment statement begins with a variable's identifier.
		    case IDENTIFIER: {
					String name = token.getText().toLowerCase();
          SymTabEntry id = symTabStack.lookup(name);
          Definition idDefn = id != null ? id.getDefinition()
                                         : UNDEFINED;

          // Assignment statement or procedure call.
          switch ((DefinitionImpl) idDefn) {

              case VARIABLE:
              case VALUE_PARM:
              case VAR_PARM:
              case UNDEFINED: {
                  AssignmentStatementParser assignmentParser =
                      new AssignmentStatementParser(this);
                  statementNode = assignmentParser.parse(token);
                  break;
              }

              case FUNCTION:
              case PROCEDURE: {
                  CallParser callParser = new CallParser(this);
                  statementNode = callParser.parse(token);
                  break;
              }

              default: {
                  errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                  token = nextToken();  // consume identifier
              }
          }
          break;
		    }

		    case RETURN: {
		    	// token = nextToken();
		        AssignmentStatementParser assignmentParser =
		            new AssignmentStatementParser(this);
		        statementNode =
		            assignmentParser.parseFunctionNameAssignment(token);
		        break;
		    }

		    case WHILE: {
					WhileStatementParser whileParser =
					    new WhileStatementParser(this);
					statementNode = whileParser.parse(token);
				break;
		    }

		    case IF: {
				IfStatementParser ifParser = new IfStatementParser(this);
				statementNode = ifParser.parse(token);
				break;
		    }

		    case INT:
		    case FLOAT:
		    case BOOL: {
		    	VariableDeclarationsParser variableDeclarationsParser =
              new VariableDeclarationsParser(this);
          variableDeclarationsParser.setDefinition(VARIABLE);
          variableDeclarationsParser.parse(token, null);
					break;
		    }

		    default: {
		    	// if (VariableDeclarationsParser.IDENTIFIER_SET.contains(token.getType())) 
		    	// {
		    	// 	VariableDeclarationsParser declarePaser = new VariableDeclarationsParser(this);
		    	// 	declarePaser.parse(token);
		    	// }

				statementNode = ICodeFactory.createICodeNode(NO_OP);
				break;
		    }
		}

		// Set the current line number as an attribute.
		setLineNumber(statementNode, token);

		return statementNode;
	}

	/**
	 * Set the current line number as a statement node attribute.
	 * 
	 * @param node
	 *            ICodeNode
	 * @param token
	 *            Token
	 */
	protected void setLineNumber(ICodeNode node, Token token) {
		if (node != null) {
			node.setAttribute(LINE, token.getLineNumber());
		}
	}

	/**
	 * Parse a statement list.
	 * 
	 * @param token
	 *            the curent token.
	 * @param parentNode
	 *            the parent node of the statement list.
	 * @param terminator
	 *            the token type of the node that terminates the list.
	 * @param errorCode
	 *            the error code if the terminator token is missing.
	 * @throws Exception
	 *             if an error occurred.
	 */
	protected void parseList(Token token, ICodeNode parentNode, CTokenType terminator, CErrorCode errorCode)
			throws Exception {
		// Loop to parse each statement until the } token
		// or the end of the source file.
		parseList(token, parentNode, terminator, errorCode, false);
	}

	protected void parseList(Token token, ICodeNode parentNode, CTokenType terminator, CErrorCode errorCode, boolean requiredReturn)
			throws Exception {
		// Loop to parse each statement until the } token
		// or the end of the source file.
		boolean seenReturn = false;
		while (!(token instanceof EofToken) && (token.getType() != terminator)) {
			if (token.getType() == RETURN) {
				seenReturn = true;
			}
			// Parse a statement. The parent node adopts the statement node.
			ICodeNode statementNode = parse(token);
			parentNode.addChild(statementNode);

			token = currentToken();
			TokenType tokenType = token.getType();
			
			// Look for the semicolon between statements.
			if (tokenType == SEMICOLON) {
				token = nextToken(); // consume the ;
			}
		}

		// Look for the terminator token.
		if (token.getType() == terminator) {
			if (requiredReturn && !seenReturn) {
				errorHandler.flag(token, MISSING_RETURN, this);
			}
			token = nextToken(); // consume the terminator token
		} else {
			errorHandler.flag(token, errorCode, this);
		}
	}
}
