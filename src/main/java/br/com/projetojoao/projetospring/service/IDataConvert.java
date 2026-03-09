package br.com.projetojoao.projetospring.service;

public interface IDataConvert {
    <T> T getData(String json, Class<T> classs);
}
