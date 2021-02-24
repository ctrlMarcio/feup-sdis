package io.github.ctrlMarcio.sdis.lab1.framework.request;

import lombok.Builder;
import lombok.Getter;

@Builder
public class RequestResponse {

    @Getter
    protected String content;
}
