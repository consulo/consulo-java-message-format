module consulo.java.messageFormat.java.impl {
    requires consulo.java.language.api;
    requires consulo.language.api;
    requires consulo.java.messageFormat.language.api;
    requires consulo.java.messageFormat.language.impl;

    exports consulo.java.messageFormat.java.inject;
}
