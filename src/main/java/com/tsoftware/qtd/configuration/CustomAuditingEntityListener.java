package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.entity.AbstractAuditEntity;
import jakarta.persistence.PrePersist;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

@Configuration
public class CustomAuditingEntityListener extends AuditingEntityListener {

	@Override
	public void touchForCreate (@NonNull Object target){
		AbstractAuditEntity entity = (AbstractAuditEntity) target;
		if(entity.getCreatedBy() == null){
			super.touchForCreate(target);
		}else {
			if(entity.getLastModifiedBy() == null){
				entity.setLastModifiedBy(entity.getCreatedBy());
			}else {
				entity.setLastModifiedBy(entity.getCreatedBy());
			}
		}
	}
	@Override
	@PrePersist
	public void touchForUpdate (@NonNull Object target){
		AbstractAuditEntity entity = (AbstractAuditEntity) 	target;
		if(entity.getLastModifiedBy() == null){
			super.touchForUpdate(target);
		}
	}
}
