package com.skchoi.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skchoi.board.dao.BoardDao;
import com.skchoi.board.dto.BoardDto;
import com.skchoi.board.form.BoardForm;

@Service
public class BoardService {

	//protected final Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Autowired
	private BoardDao boardDao;

	/** 게시판 - 목록 조회 */
	public List<BoardDto> getBoardList(BoardForm boardForm) throws Exception {
		return boardDao.getBoardList(boardForm);
	}

	/** 게시판 - 상세 조회 */
	public BoardDto getBoardDetail(BoardForm boardForm) throws Exception {
		BoardDto boardDto = new BoardDto();
		String searchType = boardForm.getSearch_type();
		if("S".equals(searchType)) {
			int updateCnt = boardDao.updateBoardHits(boardForm);
			if(updateCnt > 0) {
				boardDto = boardDao.getBoardDetail(boardForm);
			}
		} else {
			boardDto = boardDao.getBoardDetail(boardForm);
		}
		return boardDto;
	}

	/** 게시판 - 등록 */
	public BoardDto insertBoard(BoardForm boardForm) throws Exception {
		BoardDto boardDto = new BoardDto();
		int insertCnt = boardDao.insertBoard(boardForm);
		if(insertCnt > 0) {
			boardDto.setResult("SUCCESS");
		} else {
			boardDto.setResult("FAIL");
		}
		return boardDto;
	}

	/** 게시판 - 첨부파일 정보 조회 */

	/** 게시판 - 삭제 */
	public BoardDto deleteBoard(BoardForm boardForm) throws Exception {
		BoardDto boardDto = new BoardDto();
		int deleteCnt = boardDao.deleteBoard(boardForm);
		if(deleteCnt > 0) {
			boardDto.setResult("SUCCESS");
		} else {
			boardDto.setResult("FAIL");
		}
		return boardDto;
	}

	/** 게시판 - 수정 */
	public BoardDto updateBoard(BoardForm boardForm) throws Exception {
		BoardDto boardDto = new BoardDto();
		int deleteCnt = boardDao.updateBoard(boardForm);
		if(deleteCnt > 0) {
			boardDto.setResult("SUCCESS");
		} else {
			boardDto.setResult("FAIL");
		}
		return boardDto;
	}

	/** 게시판 - 답글 등록 */

	/** UITL : 32글자 랜덤 문자열 생성 */
}
