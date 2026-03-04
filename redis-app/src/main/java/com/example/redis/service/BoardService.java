package com.example.redis.service;

import com.example.redis.entity.Board;
import com.example.redis.repository.BoardRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * @Cacheable: Cache Aside 전략으로 캐싱이 적용됩니다.
     * 1. 레디스에 데이터가 있다면 메서드를 실행하지 않고 레디스의 데이터를 반환합니다.
     * 2. 데이터가 없다면 메서드 내부 로직을 실행한 뒤 리턴 값을 레디스에 저장하고 반환합니다.
     *
     * - cacheNames: 캐시 이름 (설정된 CacheManager에서 사용)
     * - key: Redis에 저장될 키의 이름 (SpEL 표현식 사용)
     * - cacheManager: 사용할 CacheManager 빈의 이름
     */
    @Cacheable(cacheNames = "getBoards", key = "'boards:page:' + #page + ':size:' + #size", cacheManager = "boardCacheManager")
    public List<Board> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> pageOfBoards = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return pageOfBoards.getContent();
    }
}
