package com.lewis.firstPhase.stream;

/**
 * Created by zhangminghua on 2017/1/3.
 */
public class StringCombiner  {

    private final String delim;
    private final String prefix;
    private final String suffix;
    private final StringBuilder builder;


    public StringCombiner add(String element) {
        if (areAtStart()) {
            builder.append(prefix);
        } else {
            builder.append(delim);
        }
        builder.append(element);
        return this;
    }

    public StringCombiner merge(StringCombiner other) {
        if (other.builder.length() > 0) {
            if (areAtStart()) {
                builder.append(prefix);
            } else {
                builder.append(delim);
            }
            builder.append(other.builder, prefix.length(), other.builder.length());
        }
        return this;
    }

    public StringCombiner(String delim, String prefix, String suffix) {
        this.delim = delim;
        this.prefix = prefix;
        this.suffix = suffix;
        this.builder = new StringBuilder();
    }

    private boolean areAtStart(){
        return builder.length() == 0;
    }

    public String toString() {
        if (areAtStart()) {
            builder.append(prefix);
        }
        builder.append(suffix);
        return builder.toString();
    }


}
