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

//All whitespaces and line comments are skipped
WS: [ \t\r\n]+ -> skip;
LINE_COMMENT : '//' .*? '\r'? '\n' -> skip;
BLOCK_COMMENT : '/*' .*? '*/' -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
OPEN_PARANTHESIS: '(';
CLOSE_PARANTHESIS: ')';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';
NOT: '!';
EQUALS: '==';
AND: '&&';
LESS_THAN: '<';


//--- PARSER: ---
stylesheet: stylesheetLines* EOF;
stylesheetLines: variableAssignment | styleRule;
styleRule: selector OPEN_BRACE body+ CLOSE_BRACE;
body: (declaration | ifStatement | variableAssignment)+;

// Variable
variableAssignment: variableReference ASSIGNMENT_OPERATOR expression SEMICOLON;
variableReference: CAPITAL_IDENT;


// If-else
ifStatement: IF BOX_BRACKET_OPEN booleanExpression BOX_BRACKET_CLOSE OPEN_BRACE body CLOSE_BRACE (elseStatement)?;
elseStatement: ELSE OPEN_BRACE body CLOSE_BRACE;


// Selector
selector: LOWER_IDENT | CLASS_IDENT | (ID_IDENT | COLOR);

// Literal
literal: boolLiteral | pixelLiteral | percentageLiteral | colorLiteral | scalarLiteral;
scalarLiteral: SCALAR;
pixelLiteral: PIXELSIZE;
boolLiteral: TRUE | FALSE;
percentageLiteral: PERCENTAGE;
colorLiteral: COLOR;

// Other
expression: operation | primaryExpression | booleanExpression;
primaryExpression: literal | variableReference;

operation:
    | OPEN_PARANTHESIS operation CLOSE_PARANTHESIS
    | operation MUL operation
    | operation (PLUS | MIN) operation
    | primaryExpression;

booleanExpression: primaryExpression
    | booleanExpression (LESS_THAN | EQUALS) booleanExpression
    | NOT booleanExpression
    | OPEN_PARANTHESIS booleanExpression CLOSE_PARANTHESIS
    | booleanExpression AND booleanExpression;

declaration: propertyName COLON expression SEMICOLON;

propertyName: 'background-color' | 'color' | 'width' | 'height';