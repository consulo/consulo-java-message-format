/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.java.messageFormat;

import consulo.language.ast.IElementType;

public interface MessageFormatElementTypes {
    IElementType FORMAT_ELEMENT = new IElementType("FORMAT_ELEMENT", MessageFormatLanguage.INSTANCE);
    IElementType ARGUMENT_INDEX = new IElementType("ARGUMENT_INDEX", MessageFormatLanguage.INSTANCE);
    IElementType FORMAT_TYPE = new IElementType("FORMAT_TYPE", MessageFormatLanguage.INSTANCE);
    IElementType FORMAT_STYLE = new IElementType("FORMAT_STYLE", MessageFormatLanguage.INSTANCE);
    IElementType QUOTED_TEXT = new IElementType("QUOTED_TEXT", MessageFormatLanguage.INSTANCE);
}
