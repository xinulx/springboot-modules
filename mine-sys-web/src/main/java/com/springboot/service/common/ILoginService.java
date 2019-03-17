package com.springboot.service.common;

import com.springboot.entity.vo.ResponseData;

public interface ILoginService {

    public ResponseData login(String userAccount, String userPassword);
}
