package consulo.java.messageFormat.impl.lexer;

import consulo.language.ast.IElementType;
import consulo.language.lexer.LexerBase;
import consulo.java.messageFormat.MessageFormatTokenTypes;

%%

%public
%class _JdkMessageFormatLexer
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
%state CHOICE_STYLE

%%

<YYINITIAL> {
    "{"                     { pushBrace(); yybegin(FORMAT_ELEMENT); return MessageFormatTokenTypes.LBRACE; }
    "''"                    { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'" [^']* "'"           { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'"                     { return MessageFormatTokenTypes.SINGLE_QUOTE; }
    [^'{]+                  { return MessageFormatTokenTypes.TEXT; }
}

<FORMAT_ELEMENT> {
    {WHITE_SPACE}+          { return MessageFormatTokenTypes.WHITE_SPACE; }
    {DIGIT}+                { return MessageFormatTokenTypes.NUMBER; }
    ","                     { yybegin(AFTER_FIRST_COMMA); return MessageFormatTokenTypes.COMMA; }
    "}"                     { popBrace(); yybegin(YYINITIAL); return MessageFormatTokenTypes.RBRACE; }
    [^]                     { return MessageFormatTokenTypes.BAD_CHARACTER; }
}

<AFTER_FIRST_COMMA> {
    {WHITE_SPACE}+          { return MessageFormatTokenTypes.WHITE_SPACE; }
    {LETTER} {IDENTIFIER_CHAR}* { return MessageFormatTokenTypes.IDENTIFIER; }
    ","                     { yybegin(AFTER_SECOND_COMMA); return MessageFormatTokenTypes.COMMA; }
    "}"                     { popBrace(); yybegin(YYINITIAL); return MessageFormatTokenTypes.RBRACE; }
    [^]                     { return MessageFormatTokenTypes.BAD_CHARACTER; }
}

<AFTER_SECOND_COMMA> {
    {WHITE_SPACE}+          { return MessageFormatTokenTypes.WHITE_SPACE; }
    {LETTER} {IDENTIFIER_CHAR}* { return MessageFormatTokenTypes.IDENTIFIER; }
    {DIGIT}+ ("." {DIGIT}+)? { return MessageFormatTokenTypes.NUMBER; }
    "{"                     { pushBrace(); yybegin(CHOICE_STYLE); return MessageFormatTokenTypes.LBRACE; }
    "|"                     { return MessageFormatTokenTypes.PIPE; }
    "<"                     { return MessageFormatTokenTypes.LESS; }
    "\u2264"                { return MessageFormatTokenTypes.LESS_OR_EQUAL; }
    "\u2265"                { return MessageFormatTokenTypes.GREATER_OR_EQUAL; }
    "#"                     { return MessageFormatTokenTypes.HASH; }
    "''"                    { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'" [^']* "'"           { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'"                     { return MessageFormatTokenTypes.SINGLE_QUOTE; }
    "}"                     { popBrace(); yybegin(YYINITIAL); return MessageFormatTokenTypes.RBRACE; }
    [^{}|<#',\u2264\u2265\t\r\n ]+ { return MessageFormatTokenTypes.TEXT; }
    ","                     { return MessageFormatTokenTypes.COMMA; }
}

<CHOICE_STYLE> {
    {WHITE_SPACE}+          { return MessageFormatTokenTypes.WHITE_SPACE; }
    {DIGIT}+ ("." {DIGIT}+)? { return MessageFormatTokenTypes.NUMBER; }
    {LETTER} {IDENTIFIER_CHAR}* { return MessageFormatTokenTypes.IDENTIFIER; }
    ","                     { return MessageFormatTokenTypes.COMMA; }
    "{"                     { pushBrace(); return MessageFormatTokenTypes.LBRACE; }
    "}"                     { if (popBrace()) {
                                if (braceDepth == 0) {
                                    yybegin(YYINITIAL);
                                } else if (braceDepth == 1) {
                                    yybegin(AFTER_SECOND_COMMA);
                                }
                                return MessageFormatTokenTypes.RBRACE;
                              }
                              yybegin(YYINITIAL);
                              return MessageFormatTokenTypes.RBRACE;
                            }
    "|"                     { return MessageFormatTokenTypes.PIPE; }
    "<"                     { return MessageFormatTokenTypes.LESS; }
    "\u2264"                { return MessageFormatTokenTypes.LESS_OR_EQUAL; }
    "\u2265"                { return MessageFormatTokenTypes.GREATER_OR_EQUAL; }
    "#"                     { return MessageFormatTokenTypes.HASH; }
    "''"                    { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'" [^']* "'"           { return MessageFormatTokenTypes.QUOTED_STRING; }
    "'"                     { return MessageFormatTokenTypes.SINGLE_QUOTE; }
    [^{}|<#',\u2264\u2265\t\r\n ]+ { return MessageFormatTokenTypes.TEXT; }
}

[^]                         { return MessageFormatTokenTypes.BAD_CHARACTER; }
