package com.quyet.identity.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

  // Key-Value operations
  void setValue(String key, Object value);

  void setValueWithTTL(String key, Object value, long timeout, TimeUnit unit);

  Object getValue(String key);

  void deleteKey(String key);

  boolean hasKey(String key);

  // Hash operations
  void setHash(String key, String field, Object value);

  void setHashWithTTL(String key, String field, Object value, long timeout, TimeUnit unit);

  Object getHash(String key, String field);

  Map<Object, Object> getAllHash(String key);

  void deleteHashField(String key, String... fields);

  boolean hasHashField(String key, String field);

  // List operations
  void addToList(String key, Object... values);

  List<Object> getList(String key, long start, long end);

  long getListSize(String key);

  void removeFromList(String key, long count, Object value);

  void setListWithTTL(String key, List<Object> values, long timeout, TimeUnit unit);

  // Set operations
  void addToSet(String key, Object... values);

  Set<Object> getSet(String key);

  boolean isMemberOfSet(String key, Object value);

  void removeFromSet(String key, Object... values);

  long getSetSize(String key);

  // TTL operations
  boolean setTTL(String key, long timeout, TimeUnit unit);

  long getTTL(String key, TimeUnit unit);

  // Key management
  void deleteKeysByPattern(String pattern);

  Set<String> getKeysByPattern(String pattern);
}
