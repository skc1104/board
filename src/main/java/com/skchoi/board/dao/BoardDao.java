package com.skchoi.board.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.skchoi.board.dto.BoardDto;
import com.skchoi.board.dto.BoardFileDto;
import com.skchoi.board.form.BoardFileForm;
import com.skchoi.board.form.BoardForm;

@Repository
public class BoardDao {
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	private static final String NAMESPACE = "com.skchoi.board.boardMapper";

	/** �Խ��� - ��� �� */
	public int getBoardCnt(BoardForm boardForm) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getBoardCnt", boardForm);
	}

	/** �Խ��� - �����ȸ */
	public List<BoardDto> getBoardList(BoardForm boardForm) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getBoardList", boardForm);
	}

	/** �Խ��� - ��ȸ ��(hits) ���� */
	public int updateBoardHits(BoardForm boardForm) throws Exception {
		return sqlSession.update(NAMESPACE + ".updateBoardHits", boardForm);
	}

	/** �Խ��� - �� ��ȸ */
	public BoardDto getBoardDetail(BoardForm boardForm) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getBoardDetail", boardForm);
	}

	/** �Խ��� - ÷������ ��ȸ */
	public List<BoardFileDto> getBoardFileList(BoardFileForm boardFileForm) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getBoardFileList", boardFileForm);
	}

	/** �Խ��� - �׷� ��ȣ ��ȸ */
	public int getBoardReRef(BoardForm boardForm) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getBoardReRef", boardForm);
	}

	/** �Խ��� - ��� */
	public int insertBoard(BoardForm boardForm) throws Exception {
		return sqlSession.insert(NAMESPACE + ".insertBoard", boardForm);
	}

	/** �Խ��� - ÷������ ��� */
	public int insertBoardFile(BoardFileForm boardFileForm) throws Exception {
		return sqlSession.insert(NAMESPACE + ".insertBoardFile", boardFileForm);
	}

	/** �Խ��� - ��� ���� (Ʈ����� �׽�Ʈ) */
	public int insertBoardFail(BoardForm boardForm) throws Exception {
		return sqlSession.insert(NAMESPACE + ".insertBoardFail", boardForm);
	}

	/** �Խ��� - ���� */
	public int deleteBoard(BoardForm boardForm) throws Exception {
		return sqlSession.delete(NAMESPACE + ".deleteBoard", boardForm);
	}

	/** �Խ��� - ���� */
	public int updateBoard(BoardForm boardForm) throws Exception {
		return sqlSession.update(NAMESPACE + ".updateBoard", boardForm);
	}

	/** �Խ��� - ��� ���� ��ȸ */
	public BoardDto getBoardReplyInfo(BoardForm boardForm) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getBoardReplyInfo", boardForm);
	}

	/** �Խ��� - ����� ���� ���� */
	public int updateBoardReSeq(BoardForm boardForm) throws Exception {
		return sqlSession.update(NAMESPACE + ".updateBoardReSeq", boardForm);
	}

	/** �Խ��� - ��� ��� */
	public int insertBoardReply(BoardForm boardForm) throws Exception {
		return sqlSession.insert(NAMESPACE + ".insertBoardReply", boardForm);
	}

	/** �Խ��� - ÷������ ���� */
	public int deleteBoardFile(BoardFileForm boardFileForm) throws Exception {
		return sqlSession.update(NAMESPACE + ".deleteBoardFile", boardFileForm);
	}

}
