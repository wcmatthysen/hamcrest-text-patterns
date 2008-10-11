package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Sequence implements PatternComponent {
    private final PatternComponent[] elements;

    public Sequence(PatternComponent[] alternatives) {
        this.elements = alternatives.clone();
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
        for (PatternComponent element : elements) {
            element.buildRegex(builder, groups);
        }
    }
}
