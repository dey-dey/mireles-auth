package com.deydey.iam.domain.identity.tenant;

import com.deydey.iam.domain.EntityId;

import java.math.BigInteger;

public class TenantMemberId implements EntityId<BigInteger> {

    private BigInteger id;

    public TenantMemberId(BigInteger id) {
        this.id = id;
    }

    @Override
    public BigInteger getValue() {
        return id;
    }
}
