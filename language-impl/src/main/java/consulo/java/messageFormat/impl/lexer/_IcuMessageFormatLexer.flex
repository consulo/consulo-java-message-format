package consulo.java.messageFormat.impl.lexer;

import consulo.language.ast.IElementType;
import consulo.language.lexer.LexerBase;
import consulo.java.messageFormat.MessageFormatTokenTypes;

%%

%public
%class _IcuMessageFormatLexer
%extends LexerBase
%function advanceImpl
%unicode
%type IElementType

%{
    private int braceDepth = 0;

    private void pushBrace() {
        braceDepth++;
    }

    private boolean popBrace() {
        if (braceDepth > 0) {
            braceDepth--;
            return true;
        }
        return false;
    }
%}

DIGIT = [0-9]
LETTER = [a-zA-Z_]
IDENTIFIER_CHAR = {LETTER} | {DIGIT}
WHITE_SPACE = [ \t\r\n]

%state FORMAT_ELEMENT
%state AFTER_FIRST_COMMA
%state AFTER_SECOND_COMMA
%state SUB_MESSAGE

%%

<YYINITIAL> {
    "{"                     { pushBrace(); yybegin(FORMAT_ELEMENT); return MessageFormatTokenTypes.LBRACE; }
    "''"                    { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'" [^']* "'"           { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'"                     { return MessageFormatTokenTypes.SINGLE_QUOTE; }
    "#"                     { return MessageFormatTokenTypes.HASH; }
    [^'{#]+                 { return MessageFormatTokenTypes.TEXT; }
}

<FORMAT_ELEMENT> {
    {WHITE_SPACE}+          { return MessageFormatTokenTypes.WHITE_SPACE; }
    {DIGIT}+                { return MessageFormatTokenTypes.NUMBER; }
    {LETTER} {IDENTIFIER_CHAR}* { return MessageFormatTokenTypes.IDENTIFIER; }
    ","                     { yybegin(AFTER_FIRST_COMMA); return MessageFormatTokenTypes.COMMA; }
    "}"                     { popBrace(); yybegin(braceDepth > 0 ? AFTER_SECOND_COMMA : YYINITIAL); return MessageFormatTokenTypes.RBRACE; }
    [^]                     { return MessageFormatTokenTypes.BAD_CHARACTER; }
}

<AFTER_FIRST_COMMA> {
    {WHITE_SPACE}+          { return MessageFormatTokenTypes.WHITE_SPACE; }
    {LETTER} {IDENTIFIER_CHAR}* { return MessageFormatTokenTypes.IDENTIFIER; }
    ","                     { yybegin(AFTER_SECOND_COMMA); return MessageFormatTokenTypes.COMMA; }
    "}"                     { popBrace(); yybegin(braceDepth > 0 ? AFTER_SECOND_COMMA : YYINITIAL); return MessageFormatTokenTypes.RBRACE; }
    [^]                     { return MessageFormatTokenTypes.BAD_CHARACTER; }
}

<AFTER_SECOND_COMMA> {
    {WHITE_SPACE}+          { return MessageFormatTokenTypes.WHITE_SPACE; }
    {LETTER} {IDENTIFIER_CHAR}* { return MessageFormatTokenTypes.IDENTIFIER; }
    {DIGIT}+ ("." {DIGIT}+)? { return MessageFormatTokenTypes.NUMBER; }
    "=" {DIGIT}+            { return MessageFormatTokenTypes.IDENTIFIER; }
    "{"                     { pushBrace(); yybegin(SUB_MESSAGE); return MessageFormatTokenTypes.LBRACE; }
    "}"                     { popBrace(); yybegin(braceDepth > 0 ? AFTER_SECOND_COMMA : YYINITIAL); return MessageFormatTokenTypes.RBRACE; }
    "''"                    { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'" [^']* "'"           { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'"                     { return MessageFormatTokenTypes.SINGLE_QUOTE; }
    ","                     { return MessageFormatTokenTypes.COMMA; }
    [^{}',=\t\r\n a-zA-Z_0-9]+ { return MessageFormatTokenTypes.TEXT; }
}

<SUB_MESSAGE> {
    "{"                     { pushBrace(); yybegin(FORMAT_ELEMENT); return MessageFormatTokenTypes.LBRACE; }
    "}"                     { popBrace(); yybegin(braceDepth > 0 ? AFTER_SECOND_COMMA : YYINITIAL); return MessageFormatTokenTypes.RBRACE; }
    "''"                    { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'" [^']* "'"           { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'"                     { return MessageFormatTokenTypes.SINGLE_QUOTE; }
    "#"                     { return MessageFormatTokenTypes.HASH; }
    [^'{#}]+                { return MessageFormatTokenTypes.TEXT; }
}

[^]                         { return MessageFormatTokenTypes.BAD_CHARACTER; }
