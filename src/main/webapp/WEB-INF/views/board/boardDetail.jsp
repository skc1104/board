<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 상세</title>
<!--  공통 CSS -->
<link rel="stylesheet" type="text/css" href="/css/common/common.css" />

<c:set var="boardSeq" value="${param.boardSeq}"/>    <!-- 게시글 번호 -->

<!--  공통 javascript -->
<script src="/js/common/jquery.js"></script>
<script>
	$(document).ready(function () {
		getBoardDetail();
	});
	
	/** 게시판 - 목록 페이지 이동 */
	function goBoardList() {
		location.href = "/board/boardList";
	}

	/** 게시판 - 수정 페이지 이동 */
	function goBoardUpdate() {
		var boardSeq = $("#board_seq").val();
		location.href = "/board/boardUpdate?boardSeq=" + boardSeq;
	}
	
	/** 게시판 - 상세 조회 */
	function getBoardDetail(boardSeq) {
		var boardSeq = $("#board_seq").val();
		if(boardSeq != "") {
			$.ajax({
				url : "/board/getBoardDetail",
				data : $("#boardForm").serialize(),
				dataType : "JSON",
				cache : false,
				async : true,
				type : "POST",
				success : function(obj) {
					getBoardDetailCallback(obj);
				},
				error : function(xhr, status, error) {}
			});
		} else {
			alert("오류가 발생했습니다.\n관리자에게 문의하세요.");
		}
	}
	
	/** 게시판 - 상세 조회 콜백 함수 */
	function getBoardDetailCallback(obj) {
		var str = "";
		if(obj != null) {
			var boardSeq = obj.board_seq;
			var boardRe_ref = obj.board_re_ref;
			var boardRe_lev = obj.board_re_lev;
			var boardRe_seq = obj.board_re_seq;
			var boardWriter = obj.board_writer;
			var boardSubject = obj.board_subject;
			var boardContent = obj.board_content;
			var boardHits = obj.board_hits;
			var delYn = obj.del_yn;
			var insUserId = obj.ins_user_id;
			var insDate = obj.ins_date;
			var updUserId = obj.upd_user_id;
			var updDate = obj.upd_date;

			str += "<tr>";
			str += "<th>제목</th>";
			str += "<td>" + boardSubject + "</td>";
			str += "<th>조회수</th>";
			str += "<td>" + boardHits + "</td>";
			str += "</tr>";
			str += "<tr>";
			str += "<th>작성자</th>";
			str += "<td>" + boardWriter + "</td>";
			str += "<th>작성일시</th>";
			str += "<td>" + insDate + "</td>";
			str += "</tr>";
			str += "<tr>";
			str += "<th>내용</th>";
			str += "<td colspan='3'>" + boardContent + "</td>";
			str += "</tr>";

		} else {
			alert("등록된 글이 존재하지 않습니다.");
			return;
		}
		$("#tbody").html(str);
	}

</script>
</head>
<body>
	<div id="wrap">
		<div id="container">
			<div class="inner">
				<h2>게시글 상세</h2>
				<form id="boardForm" name="boardForm">
					<table width="100%" class="table02">
						<caption><strong><span class="t_red">*</span> 표시는 필수입력 항목입니다.</strong></caption>
						<colgroup>
							<col width="15%">
							<col width="35%">
							<col width="15%">
							<col width="*">
						</colgroup>
						<tbody id="tbody">

						</tbody>
					</table>
					<input type="hidden" id="board_seq" name="board_seq" value="${boardSeq}" />  <!-- 게시글 번호 -->
					<input type="hidden" id="search_type" name="search_type" value="S" />  <!--  조회 타입 : 조회용 조회(S), 수정용 조회(U) -->
				</form>
				<div class="btn_right_mt15">
					<button type="button" class="btn black mr5" onclick="javascript:goBoardList();">목록으로</button>
					<button type="button" class="btn black mr5" onclick="javascript:goBoardUpdate();">수정하기</button>
					<button type="button" class="btn black" onclick="javascript:deleteBoard();">삭제하기</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>




