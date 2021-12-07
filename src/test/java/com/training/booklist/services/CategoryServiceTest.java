package com.training.booklist.services;

import com.training.booklist.dao.CategoryDao;
import com.training.booklist.dto.CategoryDto;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryService categoryService;

    @Test
    void saveCategory() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Terror");
        doNothing().when(categoryService).saveCategory(category);
        categoryService.saveCategory(category);

        verify(categoryService, times(1)).saveCategory(category);
    }

    @Test
    void deleteNonexistentCategory() {
        Long mockId = 5000L;

        doThrow(BadRequestException.class).when(categoryService).deleteCategory(anyLong());
        Assertions.assertThrows(BadRequestException.class, () -> categoryService.deleteCategory(mockId));
    }
}
