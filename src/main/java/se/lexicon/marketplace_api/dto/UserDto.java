package se.lexicon.marketplace_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import se.lexicon.marketplace_api.entity.User;

@Builder

public record UserDto(

        Integer id,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email address")
        @Size(max = 100, message = "Email must be less than 100 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        String password
) {

    // --- Conversion method: Entity → DTO ---
    // adding this here to get the creation of user to work if they are making an advert and not being reg.

    public static UserDto fromEntity(User user) {

        return new UserDto(
                user.getId(),
                user.getEmail(),
                null
        );
    }

    public User toEntity() {
        return User.builder()
                .id(this.id())
                .email(this.email)
                .password(this.password)
                .build();
    }
}
