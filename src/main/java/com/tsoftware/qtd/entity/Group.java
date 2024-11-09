package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Group extends AbstractAuditEntity {
	
	private String name;
	
	@ManyToMany
	private List<Profile> profiles;
	
	@ManyToMany
	private List<Role> roles;
}
