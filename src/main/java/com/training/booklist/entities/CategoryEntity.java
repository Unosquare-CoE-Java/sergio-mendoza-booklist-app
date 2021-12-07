package com.training.booklist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "Categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "Category")
    public String name;

    @ManyToMany(mappedBy="categories", fetch = FetchType.LAZY)
    @JsonIgnore
    public Set<BookEntity> books = new HashSet<>();

    public CategoryEntity() {

    }
}
