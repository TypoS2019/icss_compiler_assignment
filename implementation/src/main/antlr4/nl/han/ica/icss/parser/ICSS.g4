grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
EQUALS: '==';
SMALLER: '<';
BIGGER: '>';
NOT: '!';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
stylesheet: variableAssignment* stylerule*;
stylerule: selector+ OPEN_BRACE statement* CLOSE_BRACE;


selector:   LOWER_IDENT #tagSelector
            | (ID_IDENT|COLOR) #idSelector
            | CLASS_IDENT #classSelector;

statement:  variableAssignment
            | declaration
            | ifClause ;

declaration:  propertyName COLON expression SEMICOLON;
propertyName: LOWER_IDENT;

//variables
variableAssignment: variableReference ASSIGNMENT_OPERATOR expression SEMICOLON;
variableReference: CAPITAL_IDENT;

//expressions
literal:    COLOR #colorLiteral
            | PERCENTAGE #percentageLiteral
            | SCALAR #scalarLiteral
            | PIXELSIZE #pixelLiteral
            | (TRUE | FALSE) #boolLiteral
            | variableReference #variable;
expression: literal #value
            | expression MUL expression #multiplyOperation
            | expression (PLUS | MIN) expression #addSubOperation
            | expression (SMALLER | BIGGER | EQUALS) expression #compareOperation
            | NOT expression #notOperation;

//conditionals
ifClause: IF BOX_BRACKET_OPEN conditionalExpression BOX_BRACKET_CLOSE OPEN_BRACE statement* CLOSE_BRACE elseClause?;
elseClause: ELSE OPEN_BRACE statement* CLOSE_BRACE;
conditionalExpression: expression;