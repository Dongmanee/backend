package com.dongmanee.domain.member.service;

import org.springframework.stereotype.Service;

import com.dongmanee.domain.email.service.EmailService;
import com.dongmanee.domain.member.dao.MemberRepository;
import com.dongmanee.domain.member.domain.Member;
import com.dongmanee.domain.member.enums.Role;
import com.dongmanee.domain.member.exception.DuplicateEmailException;
import com.dongmanee.domain.member.exception.DuplicatePhoneException;
import com.dongmanee.domain.member.exception.DuplicateStudentIdException;
import com.dongmanee.domain.member.exception.OAuthProviderNotFoundException;
import com.dongmanee.domain.security.dao.AuthProviderRepository;
import com.dongmanee.domain.security.domain.AuthProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
	private final MemberRepository memberRepository;
	private final AuthProviderRepository authProviderRepository;
	private final EmailService emailService;

	@Override
	@Transactional
	public void signup(String provider, Long externalProviderId, Member member) {
		emailService.checkEmailAuthentication(member.getEmail());
		checkStudentIdAvailability(member.getStudentId());
		checkEmailAvailability(member.getEmail());
		checkPhoneAvailability(member.getPhone());

		member.updateRole(Role.ROLE_USER);

		if (provider != null) {
			AuthProvider authProvider = authProviderRepository
				.findByAuthProviderAndExternalProviderId(provider, externalProviderId)
				.orElseThrow(() -> new OAuthProviderNotFoundException("OAuth 제공자를 찾을 수 없습니다."));

			authProvider.initializeMember(member);
		}

		memberRepository.save(member);
	}

	private void checkStudentIdAvailability(String studentId) {
		if (memberRepository.existsByStudentId(studentId)) {
			throw new DuplicateStudentIdException("이미 가입한 학번입니다.");
		}
	}

	private void checkEmailAvailability(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new DuplicateEmailException("이미 사용 중인 이메일 입니다.");
		}
	}

	private void checkPhoneAvailability(String phone) {
		if (memberRepository.existsByPhone(phone)) {
			throw new DuplicatePhoneException("이미 사용 중인 전화번호 입니다.");
		}
	}
}
