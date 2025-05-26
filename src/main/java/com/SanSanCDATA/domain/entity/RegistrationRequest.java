package com.SanSanCDATA.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

	@NotNull(message = "OwnerId {filed.empty}")
	private String ownerId;

	@NotNull(message = "Email {field.empty}")
	private String email;

	@NotNull(message = "CompanyName {field.empty}")
	private String companyName;

	@NotNull(message = "FirstName {field.empty}")
	private String firstName;

	@NotNull(message = "LastName {field.empty}")
	private String lastName;
}