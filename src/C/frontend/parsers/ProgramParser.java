package C.frontend.parsers;

import java.util.EnumSet;

import wci.frontend.*;
import C.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;

import static C.frontend.CTokenType.*;
import static C.frontend.CErrorCode.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;
import static wci.intermediate.symtabimpl.RoutineCodeImpl.*;
/**
 * <h1>ProgramParser</h1>
 *
 * <p>Parse a Pascal program.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class ProgramParser extends DeclarationsParser
{
    /**
     * Constructor.
     * @param parent the parent parser.
     */
    public ProgramParser(CParserTD parent)
    {
        super(parent);
    }

    /**
     * Parse a program.
     * @param token the initial token.
     * @param parentId the symbol table entry of the parent routine's name.
     * @return null
     * @throws Exception if an error occurred.
     */
    public SymTabEntry parse(Token token, SymTabEntry parentId)
        throws Exception
    {
        ICode iCode = ICodeFactory.createICode();
        SymTabEntry routineId = symTabStack.enterLocal("DummyProgramName".toLowerCase());       
        routineId.setDefinition(DefinitionImpl.PROGRAM);      
        symTabStack.setProgramId(routineId);      
        routineId.setAttribute(ROUTINE_SYMTAB, symTabStack.push());     
        routineId.setAttribute(ROUTINE_ICODE, iCode);

        DeclarationsParser declarationsParser = new DeclarationsParser(this);
        declarationsParser.parse(token, parentId);

        BlockParser blockParser = new BlockParser(this);
        blockParser.parse(token, parentId);

        return null;
    }
}
