package com.quyet.identity.controller;

import com.quyet.identity.common.UriPath;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UriPath.V1 + UriPath.AUTH)
public class AuthenticationController {}
