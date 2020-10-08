package com.skchoi.board.common;

import com.skchoi.board.dto.CommonDto;
import com.skchoi.board.form.CommonForm;

public class PagingUtil {

	public static CommonDto setPageUtil(CommonForm commonForm) {
		CommonDto commonDto = new CommonDto();

		String pagination = ""; // ����¡ Navi �� html ��ũ��Ʈ
		String functionName = commonForm.getFunction_name(); // ����¡ ����� ��û�ϴ� js �Լ���
		int currentPage = commonForm.getCurrent_page_no();
		int countPerList = commonForm.getCount_per_list();
		int countPerPage = commonForm.getCount_per_page();
		int totalListCount = commonForm.getTotal_list_count();
		int totalPageCount = totalListCount / countPerList;
		if (totalListCount % countPerList > 0) {
			// �� ���������� ���� �� int������ ����ϸ� �������� �ִ� ��� �Խù��� �����ϱ� ������ �� �������� ���� ����
			totalPageCount++;
		}

		int viewFirstPage = (((currentPage - 1) / countPerPage) * countPerPage) + 1;
		int viewLastPage = viewFirstPage + countPerPage - 1;
		if (viewLastPage > totalPageCount) {
			viewLastPage = totalPageCount;
		}

		int totalFirstPage = 1; // ��ü ������ �� ù ������
		int totalLastPage = totalPageCount;
		int prePerPage = 0; // ���� ȭ�鿡 ù��° ��ȣ
		if (viewFirstPage - countPerPage > 0) {
			prePerPage = viewFirstPage - countPerPage;
		} else {
			prePerPage = totalFirstPage;
		}
		int nextPerPage = 0; // ���� ȭ�鿡 ù��° ��ȣ
		if (viewFirstPage + countPerPage < totalPageCount) {
			nextPerPage = viewFirstPage + countPerPage;
		} else {
			nextPerPage = totalPageCount;
		}

		// ������ �׺���̼� ����
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

		int offset = ((currentPage - 1) * countPerList); // �� ȭ���� ǥ��Ǵ� �Խù��� ���� ��ȣ (���� ������)

		// LIMIT�� ���� �� row�� ��, OFFSET�� �� ��° row���� ���������� ����
		commonDto.setLimit(countPerList);
		commonDto.setOffset(offset);
		commonDto.setPagination(pagination);

		return commonDto;
	}
}
