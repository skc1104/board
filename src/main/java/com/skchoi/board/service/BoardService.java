package com.skchoi.board.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skchoi.board.common.PagingUtil;
import com.skchoi.board.common.ResultUtil;
import com.skchoi.board.dao.BoardDao;
import com.skchoi.board.dto.BoardDto;
import com.skchoi.board.dto.CommonDto;
import com.skchoi.board.form.BoardFileForm;
import com.skchoi.board.form.BoardForm;
import com.skchoi.board.form.CommonForm;

@Service
public class BoardService {

	protected final Logger logger = LoggerFactory.getLogger(BoardService.class);

	@Autowired
	private BoardDao boardDao;

	/** �Խ��� - ��� ��ȸ */
	public ResultUtil getBoardList(BoardForm boardForm) throws Exception {
		ResultUtil resultUtil = new ResultUtil();

		CommonDto commonDto = new CommonDto();

		int totalCount = boardDao.getBoardCnt(boardForm);
		if(totalCount != 0) {
			CommonForm commonForm = new CommonForm();
			commonForm.setFunction_name(boardForm.getFunction_name());
			commonForm.setCurrent_page_no(boardForm.getCurrent_page_no());
			commonForm.setCount_per_page(10);
			commonForm.setCount_per_list(10);
			commonForm.setTotal_list_count(totalCount);
			commonDto = PagingUtil.setPageUtil(commonForm);
		}

		boardForm.setLimit(commonDto.getLimit());
		boardForm.setOffset(commonDto.getOffset());

		List<BoardDto> list = boardDao.getBoardList(boardForm);

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", list);
		resultMap.put("totalCount",  totalCount);
		resultMap.put("pagination", commonDto.getPagination());

		resultUtil.setData(resultMap);
		resultUtil.setState("SUCCESS");

		return resultUtil;
	}

	/** �Խ��� - �� ��ȸ */
	public BoardDto getBoardDetail(BoardForm boardForm) throws Exception {

		logger.debug("==================== getBoardDetail START ====================");

		BoardDto boardDto = new BoardDto();
		String searchType = boardForm.getSearch_type();
		if("S".equals(searchType)) {
			boardDao.updateBoardHits(boardForm);
		}
		boardDto = boardDao.getBoardDetail(boardForm);

		BoardFileForm boardFileForm = new BoardFileForm();
		boardFileForm.setBoard_seq(boardForm.getBoard_seq());

		boardDto.setFiles(boardDao.getBoardFileList(boardFileForm));

		logger.debug("==================== getBoardDetail END ====================");

		return boardDto;
	}

	/** �Խ��� - ��� */
	public BoardDto insertBoard(BoardForm boardForm) throws Exception {
		BoardDto boardDto = new BoardDto();
		int insertCnt = 0;

		int boardReRef = boardDao.getBoardReRef(boardForm);
		boardForm.setBoard_re_ref(boardReRef);

		insertCnt = boardDao.insertBoard(boardForm);

		List<BoardFileForm> boardFileList = getBoardFileInfo(boardForm);
		for(BoardFileForm boardFileForm : boardFileList) {
			boardDao.insertBoardFile(boardFileForm);
		}

		if(insertCnt > 0) {
			boardDto.setResult("SUCCESS");
		} else {
			boardDto.setResult("FAIL");
		}
		return boardDto;
	}

	/** �Խ��� - ÷������ ���� ��ȸ */
	public List<BoardFileForm> getBoardFileInfo(BoardForm boardForm) throws Exception {
		List<MultipartFile> files = boardForm.getFiles();
		List<BoardFileForm> boardFileList = new ArrayList<BoardFileForm>();
		BoardFileForm boardFileForm = null; // = new BoardFileForm();

		int boardSeq = boardForm.getBoard_seq();
		String fileName = null;
		String fileExt = null;
		String fileNameKey = null;
		String fileSize = null;
		// ������ ����� Path ����
		String filePath = "C:\\dev\\ecWorkspace\\ws-mvnEx\\board_Temp\\file";

		if(files != null && files.size() > 0) {
			File file = new File(filePath);

			// ���丮�� ������ ����
			if(file.exists() == false) {
				file.mkdirs();
			}

			for(MultipartFile multipartFile : files) {
				fileName = multipartFile.getOriginalFilename();
				fileExt = fileName.substring(fileName.lastIndexOf("."));
				// ���ϸ� ���� (uuid�� ��ȣȭ) + Ȯ����
				fileNameKey = getRandomString() + fileExt;
				fileSize = String.valueOf(multipartFile.getSize());

				// ������ Path�� ���� ����
				file = new File(filePath + "/" + fileNameKey);

				multipartFile.transferTo(file);

				boardFileForm = new BoardFileForm();
				boardFileForm.setBoard_seq(boardSeq);
				boardFileForm.setFile_name(fileName);
				boardFileForm.setFile_name_key(fileNameKey);
				boardFileForm.setFile_path(filePath);
				boardFileForm.setFile_size(fileSize);
				boardFileList.add(boardFileForm);
			}
		}

		return boardFileList;
	}

	/** �Խ��� - ���� */
	public BoardDto deleteBoard(BoardForm boardForm) throws Exception {
		logger.debug("==================== deleteBoard START ====================");
		BoardDto boardDto = new BoardDto();
		int deleteCnt = boardDao.deleteBoard(boardForm);
		if(deleteCnt > 0) {
			boardDto.setResult("SUCCESS");
		} else {
			boardDto.setResult("FAIL");
		}

		logger.debug("==================== deleteBoard START ====================");
		return boardDto;
	}

	/** �Խ��� - ���� */
	public BoardDto updateBoard(BoardForm boardForm) throws Exception {
		logger.debug("==================== updateBoard START ====================");
		BoardDto boardDto = new BoardDto();
		int updateCnt = boardDao.updateBoard(boardForm);
		String deleteFile = boardForm.getDelete_file();
		if(!"".equals(deleteFile)) {
			String[] deleteFileInfo = deleteFile.split("!");

			int boardSeq = Integer.parseInt(deleteFileInfo[0]);
			int fileNo = Integer.parseInt(deleteFileInfo[1]);

			BoardFileForm deleteBoardFileForm = new BoardFileForm();
			deleteBoardFileForm.setBoard_seq(boardSeq);
			deleteBoardFileForm.setFile_no(fileNo);

			boardDao.deleteBoardFile(deleteBoardFileForm);
		}

		List<BoardFileForm> boardFileList = getBoardFileInfo(boardForm);
		for(BoardFileForm boardFileForm : boardFileList) {
			boardDao.insertBoardFile(boardFileForm);
		}

		if(updateCnt > 0) {
			boardDto.setResult("SUCCESS");
		} else {
			boardDto.setResult("FAIL");
		}

		logger.debug("==================== updateBoard START ====================");
		return boardDto;
	}

	/** �Խ��� - ��� ��� */
	public BoardDto insertBoardReply(BoardForm boardForm) throws Exception {

		BoardDto boardDto = new BoardDto();

		BoardDto boardReplyInfo = boardDao.getBoardReplyInfo(boardForm);

		boardForm.setBoard_seq(boardReplyInfo.getBoard_seq());
		boardForm.setBoard_re_lev(boardReplyInfo.getBoard_re_lev());
		boardForm.setBoard_re_ref(boardReplyInfo.getBoard_re_ref());
		boardForm.setBoard_re_seq(boardReplyInfo.getBoard_re_seq());

		int insertCnt = 0;

		insertCnt += boardDao.updateBoardReSeq(boardForm);
		insertCnt += boardDao.insertBoardReply(boardForm);

		if(insertCnt > 0) {
			boardDto.setResult("SUCCESS");
		} else {
			boardDto.setResult("FAIL");
		}
		return boardDto;
	}

	/** UITL : 32���� ���� ���ڿ� ���� */
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
