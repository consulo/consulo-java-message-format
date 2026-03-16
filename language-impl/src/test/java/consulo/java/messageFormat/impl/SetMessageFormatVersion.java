package consulo.java.messageFormat.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SetMessageFormatVersion {
    MessageFormatVersionKind value();
}
