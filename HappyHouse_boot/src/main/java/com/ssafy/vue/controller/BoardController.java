package com.ssafy.vue.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.BoardAllDto;
import com.ssafy.vue.dto.BoardFileDto;
import com.ssafy.vue.dto.TradeThreadDto;
import com.ssafy.vue.service.BoardService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;

	@GetMapping("root")
	public @ResponseBody int report(HttpServletRequest request) {
		System.out.println("boardService.LAST_INSERT_ID() : " + boardService.LAST_INSERT_ID());
		return boardService.LAST_INSERT_ID();
	}

	@ApiOperation(value = "?????? ???????????? ????????? ????????????.", response = List.class)
	@GetMapping
	public ResponseEntity<List<Board>> retrieveBoard() throws Exception {
//    	List<Board> returnBoardList = boardService.retrieveBoard();
//    	for(int i =0; i < returnBoardList.size();i++) {
//    		returnBoardList.get(i).toString()
//    	}
//		logger.debug("retrieveBoard - ?????? {}" , boardService.retrieveBoard().toArray().toString());
		return new ResponseEntity<List<Board>>(boardService.retrieveBoard(), HttpStatus.OK);
	}

	@ApiOperation(value = "???????????? ???????????? ???????????? ????????? ????????????.", response = Board.class)
	@GetMapping("{articleno}")
	public ResponseEntity<Board> detailBoard(@PathVariable int articleno) {
		logger.debug("detailBoard - ??????");
		Board board = boardService.detailBoard(articleno);
		board.setFileList(boardService.selectBoardFileList(articleno));
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

	@GetMapping("trade/{articleno}")
	public ResponseEntity<TradeThreadDto> detailTradeBoard(@PathVariable int articleno) {
		logger.debug("detailBoard - ??????");
		TradeThreadDto tradeThreadDto = boardService.selectTradeThread(articleno);
		tradeThreadDto.setCommonMaintainItem(boardService.selectCommonMaintainItem(articleno));
		tradeThreadDto.setEachFeeItem(boardService.selectEachFeeItem(articleno));

		return new ResponseEntity<TradeThreadDto>(tradeThreadDto, HttpStatus.OK);
	}

	@ApiOperation(value = "??????????????? ???????????? ??????. ????????? DB?????? ??????????????? ?????? 'success' ?????? 'fail' ???????????? ????????????.", response = ResponseEntity.class)
	@PostMapping("insertImage")
	public ResponseEntity<String> insertImage(MultipartHttpServletRequest multipartHttpServletRequest)
			throws Exception {
//		multipartHttpServletRequest = ???????????? ????????? ?????? ????????? ????????? ??????
		System.out.println("multipartHttpServletRequest : " + multipartHttpServletRequest.getFileNames());

//		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//		String name;
//		while (iterator.hasNext()) {
//			name = iterator.next();
//			logger.debug("file tag name : {}", name);
//			List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
//
//			for (MultipartFile multipartFile : list) {
//				logger.debug("start file information");
//				logger.debug("file name : " + multipartFile.getOriginalFilename());
//				logger.debug("file size : " + multipartFile.getSize());
//				logger.debug("file content type : " + multipartFile.getContentType());
//				logger.debug("end file information.\n");
//				System.out.println("file name : " + multipartFile.getOriginalFilename());
//				System.out.println("file size : " + multipartFile.getSize());
//				System.out.println("file content type : " + multipartFile.getContentType());
//			}
//		}
		// ?????? ??????
		// ???????????? ???????????? ??????????????? ?????? auto increment??????

		// ????????? ????????????
		if (boardService.writeBoard(multipartHttpServletRequest) > 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "??????????????? ???????????? ??????. ????????? DB?????? ??????????????? ?????? 'success' ?????? 'fail' ???????????? ????????????.", response = ResponseEntity.class)
	@PostMapping("insertThread")
	public ResponseEntity<String> insertThread(@RequestBody HashMap<String, Object> map) throws Exception {

		System.out.println("before===================================================");
		for (Entry<String, Object> set : map.entrySet()) {
			String key = set.getKey();
			Object obj = set.getValue();
			System.out.println("key :" + key + ", obj :" + obj);
		}
		System.out.println("before===================================================");
		// ?????? ???????????? ?????? ???
		// ?????? ????????? ?????? ???
		// ?????? ??????, ?????? ?????? ??????
		Board board = new Board();
		board.setId((String) map.get("id"));
		board.setTitle((String) map.get("title"));

		// 1.threadboard??? ?????? ????????? ??????
		int rslt = boardService.insertBoard(board);

		if (rslt == 1) {

			// 2.tradeboard??? ?????? ????????? ??????
			TradeThreadDto tradeThreadDto = new TradeThreadDto();
			tradeThreadDto.setCommonMaintainFee((Integer) map.get("commonMaintainFee"));
			tradeThreadDto.setContracOpt((Integer) map.get("contractOpt"));
			tradeThreadDto.setDeposit((Integer) map.get("deposit"));
			tradeThreadDto.setMonthlyFee((Integer) map.get("monthlyFee"));
			tradeThreadDto.setCommonMaintainFee((Integer) map.get("commonMaintainFee"));
			tradeThreadDto.setLoan((Integer) map.get("loan"));
			String detail = ((String) map.get("detail")).replace("\n", "<br>");
			tradeThreadDto.setDetail(detail);
			tradeThreadDto.setRoadnameAddress((String) map.get("roadnameAddress"));
			tradeThreadDto.setDetailAddress((String) map.get("detailAddress"));
			System.out.println("after===================================================");
			System.out.println(tradeThreadDto.toString());
			System.out.println("after===================================================");
			rslt = boardService.insertTradeThread(tradeThreadDto);

			if (rslt == 1) {
				// 3. commonMaintainItem, EachFeeItem ??????
				if (((List<String>) map.get("commonMaintainItem")).size() > 0)
					rslt = boardService.insertCommonMaintainItem((List<String>) map.get("commonMaintainItem"));
				if (rslt != 0 && ((List<String>) map.get("eachFeeItem")).size() > 0)
					boardService.insertEachFeeItem((List<String>) map.get("eachFeeItem"));
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);

	}

	@ApiOperation(value = "???????????? ???????????? ???????????? ????????? ????????????. ????????? DB?????? ??????????????? ?????? 'success' ?????? 'fail' ???????????? ????????????.", response = String.class)
	@PutMapping("{articleno}")
	public ResponseEntity<String> updateBoard(@RequestBody Board board) {
		logger.debug("updateBoard - ??????");
		logger.debug("" + board);

		if (boardService.updateBoard(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "???????????? ???????????? ???????????? ????????? ????????????. ????????? DB?????? ??????????????? ?????? 'success' ?????? 'fail' ???????????? ????????????.", response = String.class)
	@DeleteMapping("{articleno}")
	public ResponseEntity<String> deleteBoard(@PathVariable int articleno) {
		logger.debug("deleteBoard - ??????");
		if (boardService.deleteBoard(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@GetMapping("download/{no}")
	public void downloadBoardFile(@PathVariable int no, HttpServletResponse response) throws IOException {
		System.out.println("downloadBoardFile : no  = " + no);

		BoardFileDto boardFileDto = boardService.selectBoardFileInformation(no);

		if (ObjectUtils.isEmpty(boardFileDto) == false) {
			String fileName = boardFileDto.getOriginal_name();

			byte[] files = FileUtils.readFileToByteArray(new File(boardFileDto.getSave_path()));
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition",
					"attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		}
	}

	@GetMapping("image/{board_no}/{original_name}")
	public ResponseEntity<byte[]> imgLoad(@PathVariable int board_no, @PathVariable String original_name,
			HttpServletRequest request) throws Exception {
		BoardFileDto boardFileDto = new BoardFileDto();
		boardFileDto.setBoard_no(board_no);
		boardFileDto.setOriginal_name(original_name);

		String save_path = boardService.selectBoardFileRealPath(boardFileDto);
		InputStream imageStream = new FileInputStream(save_path);
		byte[] imageByteArray = IOUtils.toByteArray(imageStream);
		imageStream.close();
		return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
	}

	@GetMapping("/allselect")
	public ResponseEntity<List<BoardAllDto>> selectall() throws Exception {
		List<BoardAllDto> list = boardService.selectall();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNoo() == null) {
				list.remove(i);
				i--;
			}
		}
		return new ResponseEntity<List<BoardAllDto>>(list, HttpStatus.OK);
	}
}