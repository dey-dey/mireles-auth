package com.deydey.iam.domain.identity.tenant;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.domain.access.authorization.Role;
import com.deydey.iam.domain.access.authorization.RoleId;
import com.deydey.iam.domain.identity.user.MemberId;
import io.micrometer.core.lang.Nullable;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.Instant;

@Builder
public class TenantMemberRole {

    private BigInteger id;

    private TenantMemberId tenantMemberId;

    @Getter
    private RoleId roleId;

    private AuditInformation auditInformation;

    private Instant startingOn;

    @Nullable
    @Getter
    private Instant retiredOn;

    static public TenantMemberRole of(Role role) {
        return TenantMemberRole.builder()
                .roleId(role.getId())
                .startingOn(Instant.now())
                .retiredOn(null)
                .build();
    }

    public void retire() {
        retiredOn = Instant.now();
    }
}
