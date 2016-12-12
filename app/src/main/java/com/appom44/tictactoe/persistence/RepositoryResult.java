package com.appom44.tictactoe.persistence;

import android.support.annotation.Nullable;

/**
 * Created by swar8080 on 12/4/2016.
 */

public class RepositoryResult<T> {

    private final T result;
    private final ResultCode code;
    private final Exception exception;

    public enum ResultCode{
        SuccessExists,
        SuccessNotExists,
        Failure,
    }

    private RepositoryResult(T result, ResultCode code, Exception exception){
        this.result = result;
        this.code = code;
        this.exception = exception;
    }

    public static <T> RepositoryResult<T> Result(T result, ResultCode code, Exception exception){
        return new RepositoryResult<T>(result,code,exception);
    }

    public static <T> RepositoryResult<T> SuccessExists(T result){
        return new RepositoryResult<T>(result,ResultCode.SuccessExists,null);
    }

    public static <T> RepositoryResult<T> SuccessNotExists(){
        return new RepositoryResult<T>(null,ResultCode.SuccessNotExists,null);
    }

    public static <T> RepositoryResult<T> Failure(Exception exception){
        return new RepositoryResult<T>(null,ResultCode.Failure,exception);
    }

    public T getResult() {
        return result;
    }

    public ResultCode getCode() {
        return code;
    }

    public Exception getException() {
        return exception;
    }

}
