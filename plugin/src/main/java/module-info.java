module consulo.java.messageFormat {
    requires consulo.ide.api;
    requires consulo.java.messageFormat.language.api;
    requires consulo.java.messageFormat.language.impl;
    requires static consulo.java.messageFormat.java.impl;
    requires static consulo.java.language.api;
}
