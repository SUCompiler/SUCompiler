package C.frontend;

public enum CErrorCode
{
    IDENTIFIER_REDEFINED("Redefined identifier"),
    IDENTIFIER_UNDEFINED("Undefined identifier"),
    INCOMPATIBLE_ASSIGNMENT("Incompatible assignment"),
    INCOMPATIBLE_TYPES("Incompatible types"),
    INVALID_ASSIGNMENT("Invalid assignment statement"),
    INVALID_CHARACTER("Invalid character"),
    INVALID_EXPRESSION("Invalid expression"),
    INVALID_FIELD("Invalid field"),
    INVALID_FRACTION("Invalid fraction"),
    INVALID_IDENTIFIER_USAGE("Invalid identifier usage"),
    INVALID_INDEX_TYPE("Invalid index type"),
    INVALID_NUMBER("Invalid number"),
    INVALID_STATEMENT("Invalid statement"),
    INVALID_TARGET("Invalid assignment target"),
    INVALID_TYPE("Invalid type"),
    INVALID_PARM("Invalid parameter"),
    MISSING_ASSIGNMENT("Missing ="),
    MISSING_COMMA("Missing ,"),
    MISSING_RETURN("Missing return statement"),
    MISSING_EQUALS("Missing =="),
    MISSING_IDENTIFIER("Missing identifier"),
    MISSING_RIGHT_PAREN("Missing )"),
    MISSING_SEMICOLON("Missing ;"),
    MISSING_VARIABLE("Missing variable"),
    MISSING_RIGHT_BRACE("Missing right brace }"),
    MISSING_LEFT_BRACE("Missing left brace {"),
    NOT_TYPE_IDENTIFIER("Not a type identifier"),
    MIN_GT_MAX("Min limit greater than max limit"),
    RANGE_INTEGER("Integer literal out of range"),
    STACK_OVERFLOW("Stack overflow"),
    INVALID_VAR_PARM("Invalid VAR parameter"), //TODO check this one
    // TOO_MANY_LEVELS("Nesting level too deep"),
    // TOO_MANY_SUBSCRIPTS("Too many subscripts"),
    RANGE_REAL("Real literal out of range"),
    UNEXPECTED_EOF("Unexpected end of file"),
    UNEXPECTED_TOKEN("Unexpected token"),
    UNIMPLEMENTED("Unimplemented feature"),
    UNRECOGNIZABLE("Unrecognizable input"),
    WRONG_NUMBER_OF_PARMS("Wrong number of actual parameters"),

    // Fatal errors.
    IO_ERROR(-101, "Object I/O error"),
    TOO_MANY_ERRORS(-102, "Too many syntax errors");

    private int status;      // exit status
    private String message;  // error message

    /**
     * Constructor.
     * @param message the error message.
     */
    CErrorCode(String message)
    {
        this.status = 0;
        this.message = message;
    }

    /**
     * Constructor.
     * @param status the exit status.
     * @param message the error message.
     */
    CErrorCode(int status, String message)
    {
        this.status = status;
        this.message = message;
    }

    /**
     * Getter.
     * @return the exit status.
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @return the message.
     */
    public String toString()
    {
        return message;
    }
}