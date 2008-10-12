package org.hamcrest.text.pattern.examples;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.pattern.Patterns.anyCharacterInCategory;
import static org.hamcrest.text.pattern.Patterns.capture;
import static org.hamcrest.text.pattern.Patterns.caseInsensitive;
import static org.hamcrest.text.pattern.Patterns.either;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.separatedBy;
import static org.hamcrest.text.pattern.Patterns.whitespace;
import static org.junit.Assert.assertThat;

import org.hamcrest.text.pattern.Parse;
import org.hamcrest.text.pattern.PatternComponent;
import org.hamcrest.text.pattern.PatternMatchException;
import org.hamcrest.text.pattern.PatternMatcher;
import org.junit.Test;

public class Example001OverviewOfFeatures {
    PatternComponent month = either(
        "jan", "feb", "mar", "apr", "may", "jun", 
        "jul", "aug", "sep", "oct", "nov", "dec");
        
    PatternComponent digit = anyCharacterInCategory("Digit");
    
    PatternComponent date = separatedBy(whitespace(),
        capture("day", exactly(2, digit)), 
        capture("month", caseInsensitive(month)), 
        capture("year", exactly(4, digit)));
    
    PatternMatcher dateRange = new PatternMatcher(separatedBy(whitespace(),
        capture("from", date),
        "-",
        capture("to", date)));
    
    @Test
    public void parsingExample() throws PatternMatchException {
        String input = "31 Dec 2003 - 16 aug 2008";
        
        Parse parse = dateRange.parse(input);
        
        assertThat(parse.get("from"), equalTo("31 Dec 2003"));
        assertThat(parse.get("to"), equalTo("16 aug 2008"));
        
        assertThat(parse.get("from.day"), equalTo("31"));
        assertThat(parse.get("from.month"), equalTo("Dec"));
        assertThat(parse.get("from.year"), equalTo("2003"));
        
        assertThat(parse.get("to.day"), equalTo("16"));
        assertThat(parse.get("to.month"), equalTo("aug"));
        assertThat(parse.get("to.year"), equalTo("2008"));
    }
    
    @Test
    public void matchingExample() {
        String input = "31 Dec 2003 - 16 aug 2008";
        assertThat(input, dateRange);
        
        String badInput = "31 12 2003 - 16 08 2008";
        assertThat(badInput, not(dateRange));
    }
}
