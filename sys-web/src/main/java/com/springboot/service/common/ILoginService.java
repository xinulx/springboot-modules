package com.springboot.service.common;

import com.springboot.vo.ResponseData;

public interface ILoginService {

    public ResponseData login(String userAccount, String userPassword);
}
