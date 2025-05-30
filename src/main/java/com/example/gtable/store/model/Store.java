package com.example.gtable.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private Long departmentId;
    private String name;
    private String location;
    private String description;
    private String storeImageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public static Store create(Long departmentId, String name, String location,
                               String description, String storeImageUrl) {
        return Store.builder()
                .departmentId(departmentId)
                .name(name)
                .location(location)
                .description(description)
                .storeImageUrl(storeImageUrl)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
