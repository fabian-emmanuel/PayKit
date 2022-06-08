package com.codewithfibbee.paykit.services.auth.admin;


import com.codewithfibbee.paykit.models.users.LoginHistory;
import com.codewithfibbee.paykit.models.users.admin.AdminLoginHistory;
import com.codewithfibbee.paykit.repositories.user.AdminLoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminLoginHistoryServiceImpl implements AdminLoginHistoryService {

    private final AdminLoginHistoryRepository repository;

    @Override
    public LoginHistory saveLoginHistory(AdminLoginHistory loginHistory) {
        return repository.save(loginHistory);
    }

}
