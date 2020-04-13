package com.lkz.blogs01.service;

import com.lkz.blogs01.po.User;

public interface UserService {
    User checkUser(String username, String password);

}
