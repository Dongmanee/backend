package com.dongmanee.domain.member.service;

import com.dongmanee.domain.member.domain.Member;
import com.dongmanee.domain.member.dto.request.RequestUpdateEmail;
import com.dongmanee.domain.member.dto.request.RequestUpdateMemberDetails;
import com.dongmanee.domain.member.dto.request.RequestUpdatePassword;

public interface MemberService {
	Member findById(Long id);

	Member updateMemberDetails(long id, RequestUpdateMemberDetails requestUpdateMemberDetails);

	void updateMemberPassword(long id, RequestUpdatePassword request);

	void updateMemberEmail(long id, RequestUpdateEmail request);
}
