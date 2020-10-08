package com.skchoi.board.common;

import com.skchoi.board.dto.CommonDto;
import com.skchoi.board.form.CommonForm;

public class PagingUtil {

	public static CommonDto setPageUtil(CommonForm commonForm) {
		CommonDto commonDto = new CommonDto();

		String pagination = ""; // 페이징 Navi 분 html 스크립트
		String functionName = commonForm.getFunction_name(); // 페이징 목록을 요청하는 js 함수명
		int currentPage = commonForm.getCurrent_page_no();
		int countPerList = commonForm.getCount_per_list();
		int countPerPage = commonForm.getCount_per_page();
		int totalListCount = commonForm.getTotal_list_count();
		int totalPageCount = totalListCount / countPerList;
		if (totalListCount % countPerList > 0) {
			// 총 페이지수를 구할 때 int형으로 계산하면 나머지가 있는 경우 게시물이 존재하기 때문에 총 페이지의 수를 수정
			totalPageCount++;
		}

		int viewFirstPage = (((currentPage - 1) / countPerPage) * countPerPage) + 1;
		int viewLastPage = viewFirstPage + countPerPage - 1;
		if (viewLastPage > totalPageCount) {
			viewLastPage = totalPageCount;
		}

		int totalFirstPage = 1; // 전체 페이지 중 첫 페이지
		int totalLastPage = totalPageCount;
		int prePerPage = 0; // 이전 화면에 첫번째 번호
		if (viewFirstPage - countPerPage > 0) {
			prePerPage = viewFirstPage - countPerPage;
		} else {
			prePerPage = totalFirstPage;
		}
		int nextPerPage = 0; // 이후 화면에 첫번째 번호
		if (viewFirstPage + countPerPage < totalPageCount) {
			nextPerPage = viewFirstPage + countPerPage;
		} else {
			nextPerPage = totalPageCount;
		}

		// 페이지 네비게이션 설정
		pagination += "<div class='pagination'>";
		pagination += "<a href='javascript:" + functionName + "(\"" + totalFirstPage + "\");' class=\"direction_left01\">[<<]</a>";
		pagination += "<a href='javascript:" + functionName + "(" + prePerPage + ");' class=\"direction_left01\">[<]</a>";
		for (int a = viewFirstPage; a <= viewLastPage; a++) {
			if (a == currentPage) {
				pagination += "<a href='javascript:" + functionName + "(\"" + a + "\");' class='onpage'>[" + a + "]</a>";
			} else {
				pagination += "<a href='javascript:" + functionName + "(\"" + a + "\");'>[" + a + "]</a>";
			}
		}
		pagination += "<a href='javascript:" + functionName + "(" + nextPerPage + ");' class=\"direction_right01\">[>]</a>";
		pagination += "<a href='javascript:" + functionName + "(" + totalLastPage + ");' class=\"direction_right01\">[>>]</a>";
		pagination += "</div>";

		int offset = ((currentPage - 1) * countPerList); // 한 화면의 표출되는 게시물의 시작 번호 (쿼리 조건절)

		// LIMIT는 가져 올 row의 수, OFFSET은 몇 번째 row부터 가져올지를 결정
		commonDto.setLimit(countPerList);
		commonDto.setOffset(offset);
		commonDto.setPagination(pagination);

		return commonDto;
	}
}
