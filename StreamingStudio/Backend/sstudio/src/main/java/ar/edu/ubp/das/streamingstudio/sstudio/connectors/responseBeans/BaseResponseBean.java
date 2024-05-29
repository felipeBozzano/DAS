package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BaseResponseBean {
    private Integer status;
    private String message;
}
