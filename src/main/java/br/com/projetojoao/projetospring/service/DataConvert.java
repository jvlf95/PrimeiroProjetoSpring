package br.com.projetojoao.projetospring.service;

import tools.jackson.databind.ObjectMapper;


public class DataConvert implements IDataConvert{
    // object to convert JSON into java object
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T getData(String json, Class<T> classes) {
        return mapper.readValue(json, classes);
    }
}
