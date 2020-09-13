package me.border.bankapplication.utils;

import me.border.bankapplication.account.Account;
import me.border.utilities.utils.ImmuteableResponse;

public class LoginResponse extends ImmuteableResponse<Account> {
    public LoginResponse(boolean answer) {
        super(answer);
    }

    public LoginResponse(boolean answer, Account vault){
        super(answer, vault);
    }
}
