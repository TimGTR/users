package org.example.users.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("users")
public class UserProfile {
    @Id
    private String id;
    private Long userId;
    private String address;
    private String phoneNumber;
}
