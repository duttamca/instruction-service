package com.sanjiv.awsmessaging.instructionservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Instruction {
    private Long instructionId;
    private String poolNumber;
    private String poolIndicator;

    public Instruction() {
        this.instructionId =1001L;
        this.poolIndicator="TRADE";
        this.poolNumber="12CCC5MC";
    }

}
