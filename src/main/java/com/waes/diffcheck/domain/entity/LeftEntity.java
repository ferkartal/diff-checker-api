package com.waes.diffcheck.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "left_data")
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LeftEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "value")
    @Getter
    private String value;
}