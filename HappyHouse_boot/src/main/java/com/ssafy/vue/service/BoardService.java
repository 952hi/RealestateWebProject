package com.ssafy.vue.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.BoardAllDto;
import com.ssafy.vue.dto.BoardFileDto;
import com.ssafy.vue.dto.TradeThreadDto;

public interface BoardService {
	public List<Board> retrieveBoard();

	public Board detailBoard(int articleno);

	public int insertBoard(Board board);

	public int writeBoard(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	public boolean updateBoard(Board board);

	public boolean deleteBoard(int articleno);

	public List<BoardFileDto> selectBoardFileList(int board_no);

	BoardFileDto selectBoardFileInformation(int no);

	public String selectBoardFileRealPath(BoardFileDto boardFileDto);

	public int insertTradeThread(TradeThreadDto tradeThreadDto);

	public int insertCommonMaintainItem(List<String> commonMaintainItem);

	public int insertEachFeeItem(List<String> EachFeeItem);

	public TradeThreadDto selectTradeThread(@Param("boardNo") int boardNo);

	public List<String> selectCommonMaintainItem(@Param("boardNo") int no);

	public List<String> selectEachFeeItem(@Param("boardNo") int no);

	public List<BoardAllDto> selectall() throws Exception;

	public int LAST_INSERT_ID();
}
