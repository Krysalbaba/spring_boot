package com.java.nie.service;

import java.util.concurrent.Future;

public interface AsyncService {

    Future<String> asyncOne() throws InterruptedException;

    Future<String> asyncTwo() throws InterruptedException;
}
