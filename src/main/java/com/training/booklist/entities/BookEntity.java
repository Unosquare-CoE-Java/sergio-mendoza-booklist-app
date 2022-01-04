package com.training.booklist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Books")
public class BookEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "Bookid", nullable = false)
        private Long id;

        @Column(name = "Name")
        public String name;

        @Column(name = "Description")
        public String description;

        @Column(name = "Publisher")
        public String publisher;

        @Column(name = "Author")
        public String author;

        @Column(name = "ISBN")
        public String isbn;

        @Column(name = "PublishedDate")
        public LocalDate publishedDate;

        @Column(name = "CoverURL")
        private String coverUrl;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "Books_Categories",
                joinColumns = { @JoinColumn(name = "Bookid")},
                inverseJoinColumns = { @JoinColumn(name = "id")})
        public Set<CategoryEntity> categories = new HashSet<>();

        @ManyToMany(mappedBy = "books")
        @JsonIgnore
        public Set<UserEntity> users = new HashSet<>();

        public void addCategory(CategoryEntity category) {
                this.categories.add(category);
                category.getBooks().add(this);
        }
}

