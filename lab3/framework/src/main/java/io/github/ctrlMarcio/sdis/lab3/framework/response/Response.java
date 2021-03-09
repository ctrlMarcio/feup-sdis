package io.github.ctrlMarcio.sdis.lab3.framework.response;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final int ERROR_VALUE = -1;

    @Builder.Default
    protected int returnCode = 0;

    @Builder.Default
    protected String content = "";

    @Override
    public String toString() {
        return String.format("%d\n%s", returnCode, content);
    }
}
