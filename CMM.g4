grammar CMM;

program: stmt+;

stmt: declStmt|ifStmt|whileStmt|breakStmt|(assignStmt ';')
      |readStmt|writeStmt|stmtBlock|forStmt;

stmtBlock:'{'stmt*'}';

declStmt: type deAs(',' deAs)*';';
deAs: variable ('=' (expr|'{' (expr (',' expr)*)? '}'))? ;

type: 'int' | 'double' |'bool'|'char';

ifStmt: 'if' '(' compare ')' stmtBlock ('elif' '(' compare ')' stmtBlock)
		* ('else' stmtBlock)*;

whileStmt:'while' '(' compare ')' stmtBlock;

breakStmt:'break'';';

readStmt:'read''(' variable')'';';

writeStmt:'write' '(' (expr|string) ')'';';

assignStmt: variable '=' expr;

forStmt : 'for' '(' ((assignStmt ';')|declStmt| expr ';') 
		  compare ';' assignStmt ')' stmtBlock;

variable:Identi  #varID
|array         #varArray;

constant:Int #int
|Double   #double
|bool   #constBool
|Char   #char
;


compare: 'not' compare  # CompNot
|compare '||' compare # CompOr
| compare '&&' compare # CompAnd
| expr (Relation expr)? # CompRl
| '(' compare ')'    # ParensComp
;

expr: expr ('*'|'/') expr  #MulDiv
    |expr ('+'|'-') expr   #AddSub
    |expr('%') expr         #Mod
    |'-' expr               #Oppo
    |variable              #ExpVariable
    |constant             #ExpConstant
    |'('expr')'          #Parens
;

Comment:Slash ~[\r\n]*  ->channel(HIDDEN);
CommentLines:'/*' .*?  '*/'  ->channel(HIDDEN);
Slash:'//';
Relation : '>'|'<'|'>='|'<='|'=='|'!='|'<>';

WS:[ \t\n\r]+ -> skip ;
Identi:[a-zA-Z][a-z|A-Z|_|0-9]*;
bool: 'true' | 'false';
Char: '\''('a'..'z'|'A'..'Z'|'0'..'9')'\'';
Int:[0-9]+;
Double:[0-9]+'.'[0-9]*;
array:Identi'['expr']';
string:String;
String:'"' (ESC|.)*? '"';
ESC:('\"'|'\\' [btnr"\\]) ;













