parser grammar StreamQuery;

options {
	tokenVocab = StreamLexer;
}

prog
:
	SELECT columList FROM tableRef
	(
		whereCluaster
	)? EOF
;

columList
:
	nameOprand
	(
		COMMA nameOprand
	)*
;

nameOprand
:
	(
		tableName = ID DOT
	)? columnName = name
	(
		AS alias = ID
	)?
;

name
:
	LPAREN name RPAREN # LRName
	| left = name op =
	(
		STAR
		| SLASH
		| MOD
	) right = name # MulName
	| left = name op =
	(
		PLUS
		| SUB
	) right = name # AddName
	| left = name op =
    (
    	AMP
        | BAR
        | CARET
        | LTLT
        | GTGT
    ) right = name # BitwiseName
	| ID LPAREN columnName = name RPAREN # AggregationName
	| ID LPAREN params = dynaBaseLineConfig RPAREN # DynaBLExpr
	| ID LPAREN columnName = name COMMA predicate = boolExpr RPAREN # AggregationName
	| ID LPAREN predicate = boolExpr RPAREN # AggregationName
	| identity # columnName
	| parenthesis # parenthesisName
;

parenthesis
:   PARENTHESIS
;

identity
:
	ID # idEle
	| INT # intEle
	| FLOAT # floatEle
	| NEG_INT # negativeIntEle
	| NEG_FLOAT # negativeFloatELe
	| STRING # stringEle
;

tableRef
:
	tableName = ID
	(
		AS alias = ID
	)?
;

whereCluaster
:
	WHERE
	boolExpr
	(filterByExpr)?
	(durationExpr)?
;

boolExpr
:
	LPAREN boolExpr RPAREN # lrExpr
	| left = boolExpr AND right = boolExpr # andOpr
	| left = boolExpr OR right = boolExpr # orOpr
	| basicBoolExpr # basicExpr
;

basicBoolExpr
:
left = name option =
	(
		EQ
		| GT
		| LT
		| GTEQ
		| LTEQ
		| NEQ
	) right = name # compareExpr
	| left = name option = IN right = collection # inExpr
;

collection
:
    LPAREN identity (COMMA identity)* RPAREN
;

durationExpr
:
	 FOR LAST number = name unit = (MINUTE | EVENTS)
;

duration
:
    number = INT unit = (MINUTE | HOUR | DAY)
;

filterByExpr
:
    FILTER BY ID (COMMA ID)*
;


dynaBaseLineConfig
:
    columnName = nameOprand COMMA windowTime = duration COMMA cycleUnit = duration # MoMParams
    | LBRACKET columList RBRACKET COMMA FLOAT # AnomListSizeParams
;