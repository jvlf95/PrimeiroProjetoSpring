package br.com.projetojoao.projetospring.Service;

public interface IDataConvert {
    <T> T getData(String json, Class<T> classs);
}
