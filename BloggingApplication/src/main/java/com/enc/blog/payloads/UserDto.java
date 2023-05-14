package com.enc.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private Long id;
	@NotBlank(message = "Name can't be blank")
	@Size(min = 4, message = "Name must be of minimum 4 characters !!")
	private String name;
	@Email(message = "Email address is not valid !!")
	private String email;
	@NotBlank(message = "Password can't be blank !!")
	@Size(min = 8, max = 20, message = "Password must be of min 8 characters and maximum 20 characters !!")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "Password must contains a small letter, upper letter, special symbol (@#$%^&-+=()), a digit and no white spaces ")
	private String password;
	@NotBlank(message = "About can't be blank !!")
	private String about;
}
