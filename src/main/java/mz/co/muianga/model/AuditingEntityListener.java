package mz.co.muianga.model;

import java.time.Instant;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditingEntityListener {

    @PrePersist
    void perpare(AbstractEntity auditable) {
        Instant now = Instant.now();
        auditable.setCreatedDate(now);
        auditable.setLastModifiedDate(now);
    }

    @PreUpdate
    void preUpdate(AbstractEntity auditable) {
        Instant now = Instant.now();
        auditable.setLastModifiedDate(now);
    }
}
