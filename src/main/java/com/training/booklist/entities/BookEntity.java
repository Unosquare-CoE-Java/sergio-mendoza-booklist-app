package com.training.booklist.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
        public String publishedDate;
}
