package com.asastech.ofin.account.model;

import lombok.Data;

@Data
public class CustomerEntity {

    private Long id;
    private String entityId;
    private String bankIdentifier;

}
