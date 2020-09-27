<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 작성</title>
<!--  공통 CSS -->
<link rel="stylesheet" type="text/css" href="/css/common/common.css" />

<!--  공통 javascript -->
<script src="/js/common/jquery.js"></script>
<script>
	$(document).ready(function () {
		
	});
	
	/** 게시판 - 목록 페이지 이동 */
	function goBoardList() {
		location.href = "/board/boardList";
	}
	
	/** 게시판 - 작성 */
	function insertBoard() {
		var boardSubject = $("#board_subject").val();
		var boardContent = $("#board_content").val();
		
		if(boardSubject == "") {
			alert("제목을 입력해 주세요");
			$("#board_subject").focus();
			return;
		}
		
		if(boardContent == "") {
			alert("내용을 입력해 주세요");
			$("#board_ㅊontent").focus();
			return;
		}
		
		if(confirm("게시글을 등록하시겠습니까?")) {
			$.ajax({
				url : "/board/insertBoard",
				data : $("#boardForm").serialize(),
				dataType : "JSON",
				type : "POST",
				cache : false,
				asysnc : true,
				success : function(obj) {
					insertBoardCallback(obj);
				},
				error : function(xhr, status, error) {}
			});
		}
	}
	
	/** 게시판 - 작성 콜백 함수 */
	function insertBoardCallback(obj) {
		if(obj != null) {
			var result = obj.result;
			if(result == "SUCCESS") {
				alert("게시글 등록을 성공하였습니다.");
				goBoardList();
			} else {
				alert("게시글 등록을 실패하였습니다.");
				return;
			}
		}
	}
</script>

</head>

<body>
	<div id="wrap">
		<div id="container">
			<div class="inner">
				<h2>게시글 작성</h2>
				<form id="boardForm" name="boardForm">
					<table width="100%" class="table02">
						<caption><strong><span class="t_red">*</span> 표시는 필ㅇ수입력 항목입니다.</strong></caption>
						<colgroup>
							<col width="20%">
							<col width="*">
						</colgroup>
						<tbody id="tbody">
							<tr>
								<th>제목<span class="t_red">*</span></th>
								<td><input id="board_subject" name="board_subject" value="" class="tbox01" /></td>
							</tr>
							<tr>
								<th>작성자<span class="t_red">*</span></th>
								<td><input id="board_writer" name="board_writer" value="" class="tbox01" /></td>
							</tr>
							<tr>
								<th>내용<span class="t_red">*</span></th>
								<td><textarea id="board_content" name="board_content" cols="10" rows="5" class="textarea01"></textarea></td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="btn_right_mt15">
					<button type="button" class="btn black mr5" onclick="javascript:goBoardList();">목록으로</button>
					<button type="button" class="btn black" onclick="javascript:insertBoard();">등록하기</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>