package io.github.ctrlMarcio.sdis.lab1.framework.request;

import lombok.Builder;
import lombok.Getter;

import static io.github.ctrlMarcio.sdis.lab1.framework.response.Response.ERROR_VALUE;

@Builder
public class RequestResponse {

    @Getter
    protected int returnCode;

    @Getter
    protected String content;

    public static RequestResponse fromString(String string) {
        if (string.trim().isEmpty())
            throw new IllegalArgumentException("Request cannot be empty");

        int separator = string.indexOf("\n");

        String returnCode = separator == -1 ? string : string.substring(0, separator);
        String content = separator == -1 ? "" : string.substring(separator + 1);

        int code;
        try {
            code = Integer.parseInt(returnCode);
        } catch (Exception exception) {
            throw new NumberFormatException("Could not parse return code " + returnCode);
        }

        return RequestResponse.builder().returnCode(code).content(content).build();
    }

    @Override
    public String toString() {
        return String.format("%d\n%s", returnCode, content);
    }
}
