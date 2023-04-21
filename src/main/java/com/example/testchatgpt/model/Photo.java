package com.example.testchatgpt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "housing_id", referencedColumnName = "id")
    private Housing housing;

    @Column(name = "data", nullable = false, columnDefinition = "mediumblob")
    private byte[] data;

    public Photo(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }
}
