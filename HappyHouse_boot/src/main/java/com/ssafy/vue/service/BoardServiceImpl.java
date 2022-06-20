package com.ssafy.vue.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.BoardAllDto;
import com.ssafy.vue.dto.BoardFileDto;
import com.ssafy.vue.dto.TradeThreadDto;
import com.ssafy.vue.mapper.BoardMapper;
import com.ssafy.vue.util.FileUtils;

@Service
public class BoardServiceImpl implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Autowired
	private FileUtils fileUtils;

	@Autowired
	private BoardMapper boardMapper;

	@Override
	public List<Board> retrieveBoard() {
		System.out.println(boardMapper.selectBoard());
		return boardMapper.selectBoard();
	}

	@Override
	public int insertBoard(Board board) {
		return boardMapper.insertBoard(board);
	}

	@Override
	public int writeBoard(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {

//		logger.debug("multipartHttpServletRequest : {}",multipartHttpServletRequest);
//		if (ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
//			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//			String name;
//			while (iterator.hasNext()) {
//				name = iterator.next();
//				logger.debug("file tag name : {}", name);
//				System.out.println("file tag name : "+ name);
//				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
//
//				for (MultipartFile multipartFile : list) {
//					logger.debug("start file information");
//					logger.debug("file name : " + multipartFile.getOriginalFilename());
//					logger.debug("file size : " + multipartFile.getSize());
//					logger.debug("file content type : " + multipartFile.getContentType());
//					logger.debug("end file information.\n");
//					System.out.println("start file information");
//					System.out.println("file name : " + multipartFile.getOriginalFilename());
//					System.out.println("file size : " + multipartFile.getSize());
//					System.out.println("file content type : " + multipartFile.getContentType());
//					System.out.println("end file information.\n");
//				}
//			}
//		}
		List<BoardFileDto> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		if (CollectionUtils.isEmpty(list) == false) {
			return boardMapper.insertBoardFileList(list);
		}	
		return 0;
	}

	@Override
	public Board detailBoard(int articleno) {
		return boardMapper.selectBoardByNo(articleno);
	}

	@Override
	@Transactional
	public boolean updateBoard(Board board) {
		return boardMapper.updateBoard(board) == 1;
	}

	@Override
	@Transactional
	public boolean deleteBoard(int articleno) {
		return boardMapper.deleteBoard(articleno) == 1;
	}

	@Override
	public List<BoardFileDto> selectBoardFileList(int board_no) {
		return boardMapper.selectBoardFileList(board_no);
	}

	@Override
	public BoardFileDto selectBoardFileInformation(int no) {
		return boardMapper.selectBoardFileInformation(no);
	}

	@Override
	public String selectBoardFileRealPath(BoardFileDto boardFileDto) {
		return boardMapper.selectBoardFileRealPath(boardFileDto);
	}

	@Override
	public int insertCommonMaintainItem(List<String> commonMaintainItem) {
		return boardMapper.insertCommonMaintainItem(commonMaintainItem);
	}

	@Override
	public int insertEachFeeItem(List<String> EachFeeItem) {
		return boardMapper.insertEachFeeItem(EachFeeItem);
	}

	@Override
	public int insertTradeThread(TradeThreadDto tradeThreadDto) {
		return boardMapper.insertTradeThread(tradeThreadDto);
	}

	@Override
	public List<String> selectCommonMaintainItem(int boardNo) {
		return boardMapper.selectCommonMaintainItem(boardNo);
	}
	
	@Override
	public List<String> selectEachFeeItem(int boardNo) {
		return boardMapper.selectEachFeeItem(boardNo);
	}
	
	@Override
	public TradeThreadDto selectTradeThread(int boardNo) {
		return boardMapper.selectTradeThread(boardNo);
	}

	@Override
	public List<BoardAllDto> selectall() throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.selectall();
	}
	
	public int LAST_INSERT_ID() {
		return boardMapper.LAST_INSERT_ID();
	}
	
}