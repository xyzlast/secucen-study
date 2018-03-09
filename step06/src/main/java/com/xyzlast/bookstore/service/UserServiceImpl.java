package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.History;
import com.xyzlast.bookstore.entity.HistoryActionType;
import com.xyzlast.bookstore.entity.User;
import com.xyzlast.bookstore.repository.BookRepository;
import com.xyzlast.bookstore.repository.HistoryRepository;
import com.xyzlast.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserLevelDeterminant userLevelDeterminant;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final HistoryRepository historyRepository;

    public UserServiceImpl(UserLevelDeterminant userLevelDeterminant, UserRepository userRepository,
                           BookRepository bookRepository, HistoryRepository historyRepository) {

        this.userLevelDeterminant = userLevelDeterminant;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    @Transactional
    public boolean rent(int userId, int bookId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!userOptional.isPresent() || !bookOptional.isPresent()) {
            throw new RuntimeException("사용자 또는 책을 찾을 수 없습니다.");
        }
        User user = userOptional.get();
        Book book = bookOptional.get();
        user.rentBook(book, 100, userLevelDeterminant, userRepository, bookRepository);

        History history = new History(user, book, HistoryActionType.RENT_BOOK);
        history.save(historyRepository);

        return true;
    }
}
