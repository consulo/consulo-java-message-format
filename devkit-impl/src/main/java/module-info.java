module consulo.java.messageFormat.devkit.impl {
    requires consulo.language.api;
    requires consulo.java.messageFormat.language.api;
    requires consulo.java.messageFormat.language.impl;
    requires org.jetbrains.plugins.yaml;

    exports consulo.java.messageFormat.devkit.inject;
}
