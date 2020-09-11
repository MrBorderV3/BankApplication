package me.border.bankapplication;

import me.border.bankapplication.account.Account;

public class LoginResponse {

    private boolean answer;
    private Account vault;

    public LoginResponse(boolean answer){
        this.answer = answer;
    }

    public LoginResponse(boolean answer, Account vault){
        this(answer);
        this.vault = vault;
    }

    public boolean getAnswer() {
        return answer;
    }

    public Account getVault() {
        return vault;
    }
}
