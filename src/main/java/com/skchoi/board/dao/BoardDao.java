package com.skchoi.board.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.skchoi.board.dto.BoardDto;
import com.skchoi.board.form.BoardForm;

@Repository
public class BoardDao {
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	private static final String NAMESPACE = "com.skchoi.board.boardMapper";

	/** �Խ��� - �����ȸ */
	public List<BoardDto> getBoardList(BoardForm boardForm) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getBoardList");
	}

	/** �Խ��� - ��ȸ ��(hits) ���� */
	public int updateBoardHits(BoardForm boardForm) throws Exception {
		return sqlSession.update(NAMESPACE + ".updateBoardHits", boardForm);
	}

	/** �Խä� - �� ��ȸ */
	public BoardDto getBoardDetail(BoardForm boardForm) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getBoardDetail", boardForm);
	}

	/** �Խ��� - ��� */
	public int insertBoard(BoardForm boardForm) throws Exception {
		return sqlSession.insert(NAMESPACE + ".insertBoard", boardForm);
	}

	/** �Խ��� - ���� */
	public int deleteBoard(BoardForm boardForm) throws Exception {
		return sqlSession.delete(NAMESPACE + ".deleteBoard", boardForm);
	}

	/** �Խ��� - ���� */
	public int updateBoard(BoardForm boardForm) throws Exception {
		return sqlSession.update(NAMESPACE + ".updateBoard", boardForm);
	}
}
