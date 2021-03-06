package com.shgx.cache.service.impl;

import com.shgx.cache.CacheApplication;
import com.shgx.cache.enums.ReviewTypeEnum;
import com.shgx.cache.model.Book;
import com.shgx.cache.repository.BookRepo;
import com.shgx.cache.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CacheApplication.class)
@Slf4j
public class BookServiceImplTest {

    private final static Long DEFAULT_BOOK_ID1 = 1L;

    @Mock
    private BookServiceImpl bookServiceMock;

    @Mock
    private BookRepo bookRepoMock;


    @Test
    public void fetchBookByIdTest(){
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Optional<Book> album = Optional.of(new Book());
                album.get().setId(DEFAULT_BOOK_ID1);
                return album;
            }
            return null;
        }).when(bookRepoMock).findAllByIdInAndDeletedEqualsAndReviewStatusEquals(ids, false, ReviewTypeEnum.PASS);

        doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Book book = new Book();
                book.setId(DEFAULT_BOOK_ID1);
                return book;
            }
            return null;
        }).when(bookServiceMock).fetchBookById(anyLong());

        bookServiceMock.fetchBookById(anyLong());
        //直接从DB中取值，没有查询数据库
        verify(bookRepoMock, times(0)).findAllByIdInAndDeletedEqualsAndReviewStatusEquals(ids, false, ReviewTypeEnum.PASS);
    }

}
