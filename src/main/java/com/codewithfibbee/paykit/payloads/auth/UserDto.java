package com.codewithfibbee.paykit.payloads.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String userId;
	private String firstName;
	private String lastName;
    private String email;
}