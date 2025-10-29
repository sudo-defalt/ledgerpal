package com.snappay.ledgerpal.service.specification;

import com.snappay.ledgerpal.entity.Transaction;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class TransactionSpecification implements Specification<Transaction> {
    private final UUID userUuid;
    private final Map<String, String> filterMap;

    public static TransactionSpecification build(UUID userUuid, @Nonnull Map<String, String> filterMap) {
        return new TransactionSpecification(userUuid, filterMap);
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        // default expression in each predicate is user identifier
        predicate.getExpressions().add(criteriaBuilder.equal(root.get("account").get("user").get("uuid"), this.userUuid));

        this.filterMap.forEach((field, value) -> {
            if (field.equals("createdAt"))
                predicate.getExpressions().add(criteriaBuilder.ge(root.get("createdAt"), Long.valueOf(value)));
            else if (field.equals("category"))
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("category").get("uuid"), UUID.fromString(value)));
        });
        return predicate;
    }
}
