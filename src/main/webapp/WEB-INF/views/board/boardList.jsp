<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 목록</title>

<!--  공통 CSS -->
<link rel="stylesheet" type="text/css" href="/css/common/common.css" />

<!--  공통 javascript -->
<script src="/js/common/jquery.js"></script>
<script>
	$(document).ready(function() {
		getBoardList();
	})
	
	/** 게시판 - 상세 페이지 이동 */
	function goBoardDetail(boardSeq) {
		location.href = "/board/boardDetail?boardSeq=" + boardSeq;
	}
	
	/** 게시판 - 작성 페이지이동 */
	function goBoardWrite(currentPageNo) {
		location.href = "/board/boardWrite";
	}
	
	/** 게시판 - 목록 조회 */
	function getBoardList() {
		$.ajax({
			url: "/board/getBoardList",
			data: $("#boardForm").serialize(),
			dataType: "JSON",
			cace : false,
			type: "POST",
			success: function(obj) {
				getBoardListCallback(obj);
			},
			error: function(xhr, status, error) {}
		});
	}
	
	/** 게시판 - 목록 조회 콜백 함수 */
	function getBoardListCallback(obj) {
		var list = obj;
		var listLen = obj.length;
		
		console.log(list);
		console.log(listLen);
		
		var str = "";
		
		if(listLen > 0) {
			for(var i=0; i<listLen; i++) {
				var boardSeq = list[i].board_seq;
				var boardRe_ref = list[i].board_re_ref;
				var boardRe_lev = list[i].board_re_lev;
				var boardRe_seq = list[i].board_re_seq;
				var boardWriter = list[i].board_writer;
				var boardSubject = list[i].board_subject;
				var boardContent = list[i].board_content;
				var boardHits = list[i].board_hits;
				var delYn = list[i].del_yn;
				var insUserId = list[i].ins_user_id;
				var insDate = list[i].ins_date;
				var updUserId = list[i].upd_user_id;
				var updDate = list[i].upd_date;

				str += "<tr>";
                str += "<td><a href='/board/boardDetail?boardSeq=" + boardSeq + "'>"+ boardSeq +"</a></td>";
                str += "<td>"+ boardSubject +"</td>";
                str += "<td>"+ boardHits +"</td>";
                str += "<td>"+ boardWriter +"</td>";                
                str += "<td>"+ insDate +"</td>";                
                str += "</tr>";
			}
		} else {
			str += "<tr colspan='4'>";
			str += "<td>등록된 글이 존재하지 않습니다.</td>";
			str += "</tr>";
		}
		
		$("#total_count").html(listLen);
		$("#tbody").html(str);
	}
</script>

</head>

<body>
<div id="wrap">
	<div id="container">
		<div class="inner">
			<h2>게시글 목록</h2>
			<form id="boardForm" name="boardForm">
				<input type="hidden" id="function_name" name="function_name" value="getBoardList" />
				<input type="hidden" id="current_page_no" name="current_page_no" value="1" />
				<div class="page_info">
					<span class="total_count"><strong>전체</strong> : <span id="total_count" class="t_red">0</span> 개</span>
				</div>
				
				<table width="100%" class="table01">
					<colgroup>
						<col width="10%" />
						<col width="25%" />
						<col width="10%" />
						<col width="15%" />
						<col width="20%" />
					</colgroup>
					<thead>
						<tr>
							<th>글번호</th>
							<th>제목</th>
							<th>조회수</th>
							<th>작성자</th>
							<th>작성일</th>
						</tr>
					</thead>
					
					<tbody id="tbody">
					</tbody>
				</table>
			</form>
			
			<div class="btn_right mt15">
				<button type="button" class="btn black mr5" onclick="javascript:goBoardWrite();">작성하기</button>
			</div>
		</div>
	</div>
	
	<div id="pagination"></div>
</div>

</body>
</html>
