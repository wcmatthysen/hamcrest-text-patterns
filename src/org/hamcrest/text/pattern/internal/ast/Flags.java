package org.hamcrest.text.pattern.internal.ast;

import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.internal.naming.GroupNamespace;

public class Flags implements PatternComponent {
    private final String flags;
    private final PatternComponent pattern;

    public Flags(String flags, PatternComponent pattern) {
        this.flags = flags;
        this.pattern = pattern;
    }

    public void buildRegex(StringBuilder builder, GroupNamespace groups) {
        builder.append("(?").append(flags).append(":");
        pattern.buildRegex(builder, groups);
        builder.append(")");
    }
}
