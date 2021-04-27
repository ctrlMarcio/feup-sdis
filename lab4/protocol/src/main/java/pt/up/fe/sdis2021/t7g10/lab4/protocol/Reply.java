package pt.up.fe.sdis2021.t7g10.lab4.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;

@AllArgsConstructor
@Getter
public class Reply implements TCPMessage {

    @Serial
    private static final long serialVersionUID = -8510734964622287529L;

    public final String content;
}
