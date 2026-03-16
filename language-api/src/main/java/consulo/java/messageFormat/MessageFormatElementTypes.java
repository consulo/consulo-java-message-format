package consulo.java.messageFormat;

import consulo.language.ast.IElementType;

public interface MessageFormatElementTypes {
    IElementType FORMAT_ELEMENT = new IElementType("FORMAT_ELEMENT", MessageFormatLanguage.INSTANCE);
    IElementType ARGUMENT_INDEX = new IElementType("ARGUMENT_INDEX", MessageFormatLanguage.INSTANCE);
    IElementType FORMAT_TYPE = new IElementType("FORMAT_TYPE", MessageFormatLanguage.INSTANCE);
    IElementType FORMAT_STYLE = new IElementType("FORMAT_STYLE", MessageFormatLanguage.INSTANCE);
    IElementType QUOTED_TEXT = new IElementType("QUOTED_TEXT", MessageFormatLanguage.INSTANCE);
}
