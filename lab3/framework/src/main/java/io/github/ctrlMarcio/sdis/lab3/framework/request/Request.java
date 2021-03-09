package io.github.ctrlMarcio.sdis.lab3.framework.request;

import io.github.ctrlMarcio.sdis.lab3.framework.remote.CommandInterface;
import io.github.ctrlMarcio.sdis.lab3.framework.response.Response;
import lombok.Builder;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Builder
public class Request {

    @Builder.Default
    protected String method = "";

    @Builder.Default
    protected List<String> args = new ArrayList<>();

    @Builder.Default
    protected String content = "";

    public Response send(CommandInterface commandInterface) throws RemoteException {
        return switch (this.method) {
            case "REGISTER" -> commandInterface.register(this.args.get(0), this.args.get(1));
            case "LOOKUP" -> commandInterface.lookup(this.args.get(0));
            default -> throw new IllegalArgumentException();
        };
    }
}
