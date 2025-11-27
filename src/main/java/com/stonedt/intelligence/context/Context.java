package com.stonedt.intelligence.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.stonedt.intelligence.dto.UserDTO;

import java.util.concurrent.ConcurrentHashMap;

public class Context {

    public static String currentUser = "currentUser";

    public static final ThreadLocal<ConcurrentHashMap<String,Object>> CONTEXT_HOLDER =  new TransmittableThreadLocal<>();




    public static void setCurrentUser(Object user){
        ConcurrentHashMap<String, Object> map = Context.CONTEXT_HOLDER.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            Context.CONTEXT_HOLDER.set(map);
        }
        map.put(Context.currentUser, user);
    }

    public static UserDTO getCurrentUser(){
        return (UserDTO) CONTEXT_HOLDER.get().get(currentUser);
    }

    public static Object getValue(String key) {
        return CONTEXT_HOLDER.get().get(key);
    }

    public static void setValue(String key, Object value) {
        ConcurrentHashMap<String, Object> map = Context.CONTEXT_HOLDER.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            Context.CONTEXT_HOLDER.set(map);
        }
        CONTEXT_HOLDER.get().put(key, value);
    }

    public static void removeValue(String key) {
        CONTEXT_HOLDER.get().remove(key);
    }

    public static void clear() {
        CONTEXT_HOLDER.get().clear();
    }
}

