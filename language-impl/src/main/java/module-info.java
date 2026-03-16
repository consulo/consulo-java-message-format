module consulo.java.messageFormat.language.impl {
    requires consulo.language.api;
    requires consulo.language.impl;
    requires consulo.language.editor.api;

    requires consulo.java.messageFormat.language.api;

    exports consulo.java.messageFormat.impl;
    exports consulo.java.messageFormat.impl.lexer;
    exports consulo.java.messageFormat.impl.parser;
    exports consulo.java.messageFormat.impl.version;
    exports consulo.java.messageFormat.impl.highlighting;
    exports consulo.java.messageFormat.impl.annotator;
    exports consulo.java.messageFormat.impl.completion;
    exports consulo.java.messageFormat.impl.localize;
    exports consulo.java.messageFormat.impl.psi;
}
