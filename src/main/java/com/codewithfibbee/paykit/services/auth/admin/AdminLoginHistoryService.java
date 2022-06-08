package com.codewithfibbee.paykit.services.auth.admin;


import com.codewithfibbee.paykit.models.users.LoginHistory;
import com.codewithfibbee.paykit.models.users.admin.AdminLoginHistory;

public interface AdminLoginHistoryService {
    LoginHistory saveLoginHistory(AdminLoginHistory loginHistory);

}
