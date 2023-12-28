package com.spring.finaldemo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;


    @Column(name = "role_name")
    private String roleName;

    // getters and setters

    public UserRole(String roleName) {
        this.roleName = roleName;
    }

}
